package hu.fnf.devel.wishbox.persistence;


import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@SpringBootApplication
public class Application {

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

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean( new ServletContainer(), "/*" );
        registration.addInitParameter( ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName() );
        return registration;
    }
}
