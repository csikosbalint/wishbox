package hu.fnf.devel.wishbox.persistence;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@Profile( "web" )
@Component
@RestController
@Path( "/" )
public interface RestAPI {
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "/test" )
    public User test();
}
