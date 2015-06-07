/*
 * MyApplication.java which is part of the " wishbox ( frontend )" project
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

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

/**
 * Created by johnnym on 07/06/15.
 */
@ApplicationPath("/")
public class MyApplication extends ResourceConfig {
    @Inject
    public MyApplication(ServiceLocator serviceLocator) {
        // Register resources and providers using package-scanning.
        packages("hu.fnf.devel.wishbox.frontend;hu.fnf.devel.wishbox.filter");

        // Register my custom provider - not needed if it's in my.package.
//        register(SecuredWithGoogleToken.class);
        // Register an instance of LoggingFilter.
//        register(new LoggingFilter(LoggerFactory, true));

        // Enable Tracing support.
//        property(ServerProperties.TRACING, "ALL");
    }
}
