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

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@SpringBootApplication
@Configuration
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class Application {
//    @Autowired
//    private RepositoryUser repositoryUser;


    public static void main( String[] args ) throws Exception {
        ConfigurableApplicationContext context = new SpringApplicationBuilder( Application.class ).run( args );

        //TODO: @Autowire?
        RepositoryUser repositoryUser = context.getBean(RepositoryUser.class);
        RepositoryItem repositoryItem = context.getBean(RepositoryItem.class);
        RepositoryUrl repositoryUrl = context.getBean(RepositoryUrl.class);
// save a couple of customers
        User user = new User(13L);
        user.setFirstName( "fname" );
        user.setLastName( "lname" );
        Url url = new Url();
        url.setUrl("http://");
        repositoryUrl.save(url);

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setName( "item1" );
        item.setPattern("pattern1");
        item.setFound(url);
        Item item2 = new Item();
        item2.setName( "item2" );
        item2.setPattern( "pattern2" );
        item2.setFound(url);

        repositoryItem.save(item);
        repositoryItem.save(item2);

        items.add( item );
        items.add( item2 );

        user.setItems( items );
        repositoryUser.save(user);

    }
}
