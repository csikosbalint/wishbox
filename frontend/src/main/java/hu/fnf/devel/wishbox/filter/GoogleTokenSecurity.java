/*
 * GoogleTokenSecurity.java which is part of the " wishbox ( frontend )" project
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

package hu.fnf.devel.wishbox.filter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by johnnym on 07/06/15.
 */
@Provider
@OnlyWithSessionAttribute
public class GoogleTokenSecurity implements ContainerRequestFilter {
    @Context
    HttpServletRequest webRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (webRequest.getSession().getAttribute("token") == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Not valid user request!").build());
        }
    }
}
