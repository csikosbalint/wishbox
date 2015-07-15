package hu.fnf.devel.wishbox.gateway.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class SessionSecurityFilter extends BasicAuthenticationFilter {

    @Override
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException, ServletException {
        if ( HttpServletRequest.class.isAssignableFrom( req.getClass() ) ) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            String id = (String) httpServletRequest.getSession().getAttribute( WishboxGateway.SUBJECT_ID );
            if ( !StringUtils.isBlank( id ) ) {
                Authentication authentication = new SessionAuthenticationToken( SecurityContextHolder.getContext().getAuthentication().getAuthorities(), id );
                authentication.setAuthenticated( true );
                SecurityContextHolder.getContext().setAuthentication( authentication );
            } else {
                throw new UsernameNotFoundException( "No id stored! for " + ((HttpServletRequest) req).getMethod() + " " + ((HttpServletRequest) req).getRequestURI() );
            }
        }
    }
}
