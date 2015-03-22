/*
 * GatewayREST.java which is part of the " wishbox ( gateway )" project
 * Copyright (C)  2015  author:  Balint Csikos (csikos.balint@fnf.hu)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.gateway;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 21/02/15.
 */
@Controller
@RequestMapping("/gateway")
public class GatewayREST {

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public
    @ResponseBody
    String test() {
        return "test";
    }

    @RequestMapping(value = "/persistence/**", method = RequestMethod.GET)
    public
    @ResponseBody
    String proxy(HttpServletRequest request) {
        String req = "";
        StringBuilder content = new StringBuilder();
        try {
            while (request.getReader().ready()) {
                content.append(request.getReader().readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        req = "user";
        try {
            req = request.getRequestURL().toString().split("/gateway/persistence")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            req = "/";
        }
        System.out.println(request.getRequestURL());

        System.out.println("Proxy call: " + req);
        System.out.println("Conent: " + content);

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject("http://localhost:9090/" + req, String.class);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/persistence/user/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    String createUser(@PathVariable("userId") String userId, HttpServletRequest request) {
        StringBuilder content = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        for (X509Certificate cert : certs) {
            stringBuilder.append(cert.getSubjectX500Principal().getName());
        }
        try {
            while (request.getReader().ready()) {
                content.append(request.getReader().readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userId + ": " + content + ": " + stringBuilder;
    }
}
