/*
 * SessionSecurityInterceptor.java which is part of the " wishbox ( gateway )" project
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

package hu.fnf.devel.wishbox.gateway.security;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class SessionSecurityInterceptor extends HandlerInterceptorAdapter implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SessionSecurityInterceptor.class);
    private final String key;

    public SessionSecurityInterceptor(String key) {
        this.key = key;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest && serverHttpResponse instanceof ServletServerHttpResponse) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) serverHttpResponse;
            return isAuthenticatedSession( servletRequest.getServletRequest().getSession( false ), servletResponse.getServletResponse(), map );
        }
        throw new Exception("Unable to convert request to ServletServerHttpRequest");
    }

    private boolean isAuthenticatedSession( HttpSession session, HttpServletResponse response, Map<String, Object> map ) throws IOException {
        if (session != null && !StringUtils.isBlank(session.getAttribute(key).toString())) {
            map.put( WishboxGateway.SUBJECT_ID, session.getAttribute( WishboxGateway.SUBJECT_ID ) );
            return true;
        }
        response.sendError(403, "You must login and post your authorization code first!");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
