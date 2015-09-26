/*
 *   ChannelInterceptor.java is part of the "wishbox ( gateway )" project
 *   Copyright (C)  2015  author:  johnnym
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License
 *   as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class ChannelInterceptor extends ChannelInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelInterceptor.class);

    @Override
    public boolean preReceive( MessageChannel channel ) {
        return super.preReceive( channel );
    }

    @Override
    public void afterReceiveCompletion( Message<?> message, MessageChannel channel, Exception ex ) {
        logger.info("message: " + message.toString());
    }
}
