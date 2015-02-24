package hu.fnf.devel.wishbox.persistence;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@Profile( "web" )
@Component
@ApplicationPath( "/" )
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register( RequestContextFilter.class );
        packages( "hu.fnf.devel.wishbox.persistence" );
        register( LoggingFilter.class );
    }
}
