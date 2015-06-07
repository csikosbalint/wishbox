/*
 * Gateway.java which is part of the " wishbox ( frontend )" project
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

package hu.fnf.devel.wishbox.frontend;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import hu.fnf.devel.wishbox.filter.OnlyWithSessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by johnnym on 31/05/15.
 */
@Path("/gateway")
public class Gateway {

    private static final Gson GSON = new Gson();
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    private String CLIENT_ID = "574876928534-8547nbjas9bscjd627lpv6oi0mvtdlnm.apps.googleusercontent.com";
    private String CLIENT_SECRET = "6e4aUOTiB7AGIgQFKtcxkvZM";

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

    @GET
    @OnlyWithSessionAttribute
    public String doGet(@Context HttpServletRequest request) {
        if (request.getSession().getAttribute("token") != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "token: " + request.getSession().getAttribute("token") + "\nID: " + request.getSession().getAttribute("id");
        }
        return "none";
    }


    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@Context HttpServletRequest request) throws ServletException, IOException {
        // Only connect a user that is not already connected.
        String tokenData = (String) request.getSession().getAttribute("token");
        if (tokenData != null) {
            return Response.ok(GSON.toJson("Current user is already connected.")).build();
        }
        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
//        if (!request.getParameter("state").equals(request.getSession().getAttribute("state"))) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().print(GSON.toJson("Invalid state parameter."));
//            return;
//        }
        // Normally the state would be a one-time use token, however in our
        // simple case, we want a user to be able to connect and disconnect
        // without reloading the page.  Thus, for demonstration, we don't
        // implement this best practice.
        //request.getSession().removeAttribute("state");

        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        getContent(request.getInputStream(), resultStream);
        String code = new String(resultStream.toByteArray(), "UTF-8");

        try {
            // Upgrade the authorization code into an access and refresh token.
            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                            CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();

            // You can read the Google user ID in the ID token.
            // This sample does not use the user ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            String gplusId = idToken.getPayload().getSubject();
            System.out.println(gplusId);
            request.getSession().setAttribute("id", gplusId);

            // Store the token in the session for later use.
            request.getSession().setAttribute("token", tokenResponse.toString());

        } catch (TokenResponseException e) {
            return Response.serverError().entity("Failed to upgrade the authorization code.").build();
        } catch (IOException e) {
            return Response.serverError().entity("Failed to read token data from Google.").build();
        }
        return Response.ok(GSON.toJson("Successfully connected user.")).build();
    }
}
