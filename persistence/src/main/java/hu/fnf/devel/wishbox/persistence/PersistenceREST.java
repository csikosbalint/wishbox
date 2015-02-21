/*
 * PersistenceREST.java which is part of the " wishbox ( persistence )" project
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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 21/02/15.
 */
@Configuration
@EnableAutoConfiguration
public class PersistenceREST {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PersistenceREST.class);
        UserRepository userRepository = context.getBean(UserRepository.class);

//        save a couple of customers
        userRepository.save(new User("Jack", "Bauer"));
        userRepository.save(new User("Chloe", "O'Brian"));
        userRepository.save(new User("Kim", "Bauer"));
        userRepository.save(new User("David", "Palmer"));
        userRepository.save(new User("Michelle", "Dessler"));

//        fetch all customers
        Iterable<User> customers = userRepository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User customer : customers) {
            System.out.println(customer);
        }
        System.out.println();

//        fetch an individual customer by ID
        User customer = userRepository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        List<User> bauers = userRepository.findByLastName("Bauer");
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (User bauer : bauers) {
            System.out.println(bauer);
        }

        context.close();
    }
}
