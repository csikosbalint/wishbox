/*
 *   WishboxGateway.java is part of the "wishbox ( gateway )" project
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

package hu.fnf.devel.wishbox.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class WishboxGateway extends SpringBootServletInitializer {
    public static final String TOKEN = "token";
    public static final String SUBJECT_ID = "google_id";

    public static final String ROOT = "/gateway";
    public static final String WEBSOCKET = "/websocket";
    public static final String GRANTED_ROLE = "SESSION_OWNER";

    private static final Logger logger = LoggerFactory.getLogger(WishboxGateway.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting...");
        SpringApplication.run(WishboxGateway.class, args);
    }

}
