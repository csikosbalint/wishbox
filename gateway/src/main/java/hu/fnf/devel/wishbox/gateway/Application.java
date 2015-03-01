/*
 * Application.java which is part of the " wishbox ( gateway )" project
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

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 01/03/15.
 */
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
        // our rest resources will be available in the path /rest/*
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, ConfigWeb.class.getName());
        return registration;
    }


}
