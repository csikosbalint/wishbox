/*
 * Application.java which is part of the " wishbox ( persistence )" project
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

package hu.fnf.devel.wishbox.persistence;


import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@SpringBootApplication
@Configuration
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@RestController
@EnableAutoConfiguration
public class Application {
    @Autowired
    private UserRepository userRepository;

    public static void main( String[] args ) throws Exception {
        ConfigurableApplicationContext context = new SpringApplicationBuilder( Application.class ).run( args );

        UserRepository userRepository = context.getBean( UserRepository.class );
        ItemRepository itemRepository = context.getBean( ItemRepository.class );

//
//// save a couple of customers
        User user = new User( 1L );
        user.setFirstName( "fname" );
        user.setLastName( "lname" );

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setName( "item1" );
        item.setPattern( "pattern1" );
        Item item2 = new Item();
        item2.setName( "item2" );
        item2.setPattern( "pattern2" );
        itemRepository.save( item );
        itemRepository.save( item2 );
        items.add( item );
        items.add( item2 );

        user.setItems( items );
        userRepository.save( user );
    }

    @RequestMapping("/hello/{id}")
    @ResponseBody
    User home(@PathVariable("id") Long id) throws Exception {
        User retUser = userRepository.findOne(id);
        if (retUser == null) {
            throw new Exception("No such user!");
        }
        return retUser;
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/jax/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
//        registration.setName("Jax");
        return registration;
    }

    @Bean
    public ServletRegistrationBean restServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServletContainer(), "/*");
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, DataConfig.class.getName());
//        registrationBean.setName("Rest");
        return registrationBean;
    }
}
