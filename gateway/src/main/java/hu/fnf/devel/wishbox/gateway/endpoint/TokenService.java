/*
 * TokenService.java which is part of the " wishbox ( gateway )" project
 * Copyright (C)  2015  author:  johnnym
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

package hu.fnf.devel.wishbox.gateway.endpoint;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import hu.fnf.devel.wishbox.gateway.entity.Enums;
import hu.fnf.devel.wishbox.gateway.entity.Notification;
import hu.fnf.devel.wishbox.gateway.entity.User;
import hu.fnf.devel.wishbox.gateway.entity.repository.NotificationRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.UserRepository;
import hu.fnf.devel.wishbox.gateway.security.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
@RequestMapping(WishboxGateway.ROOT)
public class TokenService {
    private static final Gson GSON = new Gson();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    private String CLIENT_ID = "574876928534-8547nbjas9bscjd627lpv6oi0mvtdlnm.apps.googleusercontent.com";
    private String CLIENT_SECRET = "6e4aUOTiB7AGIgQFKtcxkvZM";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
            throws IOException {
        // Read the response into a buffered stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int readChar;
        while ((readChar = reader.read()) != -1) {
            outputStream.write(readChar);
        }
        reader.close();
    }

    @RequestMapping(value = "token", method = RequestMethod.POST)
    @ResponseBody
    public String validateToken(@RequestBody String code, HttpSession session) throws ServletException, IOException {
        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
//        if (!request.getParameter("state").equals(request.getSession().getAttribute("state"))) {
//            return Response.status(HttpServletResponse.SC_UNAUTHORIZED).entity("Invalid state parameter.").build();
//        }
        // Normally the state would be a one-time use token, however in our
        // simple case, we want a user to be able to connect and disconnect
        // without reloading the page.  Thus, for demonstration, we don't
        // implement this best practice.
        //request.getSession().removeAttribute("state");

        try {
            // Upgrade the authorization code into an access and refresh token.
            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                            CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();

            // You can read the Google user SUBJECT_ID in the SUBJECT_ID token.
            // This sample does not use the user SUBJECT_ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();

            // Store the token in the session for later use.
            session.setAttribute(InterceptorConfig.TOKEN, tokenResponse.toString());
            GoogleIdToken.Payload token = idToken.getPayload();

            if (!userRepository.exists(idToken.getPayload().getSubject()) &&
                    userRepository.findAll().size() < 5) {
                User newUser = new User(token.getSubject(), token.getEmail(), "none");
                Notification welcome = new Notification();
                welcome.setText("Welcome to wishbox!");
                welcome.setPriority(Enums.Priority.info);
                notificationRepository.save(welcome);
                newUser.addNotification(welcome);
                userRepository.save(newUser);
                session.setAttribute(InterceptorConfig.SUBJECT_ID, token.getSubject());
                return token.getSubject();
            }

            throw new ServletException("Cannot create user in database!Database is full!");

        } catch (TokenResponseException e) {
            throw new ServletException("Failed to upgrade the authorization code.");
        } catch (IOException e) {
            throw new ServletException("Failed to read token data from Google.");
        }
    }
}
