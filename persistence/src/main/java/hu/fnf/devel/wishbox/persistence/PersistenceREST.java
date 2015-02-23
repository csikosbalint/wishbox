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

//import com.ning.http.client.AsyncHttpClient;
//import com.ning.http.client.Response;
//import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
//import com.theoryinpractise.halbuilder.api.RepresentationFactory;
//import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 21/02/15.
 */
@Configuration
@EnableJpaRepositories
//@EnableAutoConfiguration
@EnableWebMvc
public class PersistenceREST {

    @RequestMapping( "/u" )
    @ResponseBody()
    public User getUser() {
        return new User();
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(PersistenceREST.class);
//
//        RepositoryRestConfiguration restConfiguration = context.getBean( RepositoryRestConfiguration.class );
//        restConfiguration.setDefaultMediaType( MediaType.APPLICATION_XML );
//
//
//        UserRepository userRepository = context.getBean(UserRepository.class);
//        ItemRepository itemRepository = context.getBean(ItemRepository.class);
//
////
//////        save a couple of customers
//        User user = new User();
//        user.setFirstName("fname");
//        user.setLastName("lname");
//        user.setId(1);
//        List<Item> items = new ArrayList<Item>();
//        Item item = new Item();
//        item.setName("item1");
//        item.setPattern("pattern1");
//        Item item2 = new Item();
//        item2.setName("item2");
//        item2.setPattern("pattern2");
//        itemRepository.save(item);
//        itemRepository.save(item2);
//        items.add(item);
//        items.add(item2);
//        user.setItems(items);
//        userRepository.save(user);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new Jackson2HalModule());
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(org.springframework.http.MediaType.parseMediaTypes("application/hal+json"));
//        converter.setObjectMapper(mapper);
//
//        RestTemplate rt = new RestTemplate();
//        rt.getMessageConverters().add(converter);
//
//
//        String uri = new String("http://localhost:8080/user/{id}");
//        User u = rt.getForObject(uri, User.class, "1");
//
//        try {
//            for ( Item i : u.getItems() ) {
//                System.out.println( i.getName() );
//            }
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("user", 1);
//
//        Traverson traverson = null;
//        try {
//            traverson = new Traverson(new URI("http://localhost:8080/user"), MediaTypes.HAL_JSON);
//        } catch ( URISyntaxException e ) {
//            e.printStackTrace();
//        }
//        String name = traverson.follow("items", "item").
//                withTemplateParameters(parameters).
//                toObject( "$.name" );
//        System.out.println(name );
//        RepresentationFactory representationFactory = new JsonRepresentationFactory();
//        representationFactory.withFlag(RepresentationFactory.PRETTY_PRINT);
//
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        Response response = null;
//        try {
//            response = asyncHttpClient.prepareGet("http://gotohal.net/restbucks/api").execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        InputStreamReader inputStreamReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(response.getResponseBodyAsStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ReadableRepresentation representation = representationFactory.readRepresentation(inputStreamReader);
//        String ordersLinkUrl = representation.getLinkByRel("orders").getHref();
//        System.out.println(ordersLinkUrl);
//        asyncHttpClient.close();


//        userRepository.save(new User("Chloe", "O'Brian"));
//        userRepository.save(new User("Kim", "Bauer"));
//        userRepository.save(new User("David", "Palmer"));
//        userRepository.save(new User("Michelle", "Dessler"));
//
////        fetch all customers
//        Iterable<User> customers = userRepository.findAll();
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (User customer : customers) {
//            System.out.println(customer);
//        }
//        System.out.println();
//
////        fetch an individual customer by ID
//        User customer = userRepository.findOne(1L);
//        System.out.println("Customer found with findOne(1L):");
//        System.out.println("--------------------------------");
//        System.out.println(customer);
//        System.out.println();
//
//        // fetch customers by last name
//        List<User> bauers = userRepository.findByLastName("Bauer");
//        System.out.println("Customer found with findByLastName('Bauer'):");
//        System.out.println("--------------------------------------------");
//        for (User bauer : bauers) {
//            System.out.println(bauer);
//        }
//
//        context.close();
//    }
}
