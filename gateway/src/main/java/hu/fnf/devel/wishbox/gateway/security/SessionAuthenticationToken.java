package hu.fnf.devel.wishbox.gateway.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by johnnym on 15/07/15.
 */
public class SessionAuthenticationToken extends AbstractAuthenticationToken {
    private String id;

    public SessionAuthenticationToken( Collection<? extends GrantedAuthority> authorities, String id ) {
        super( authorities );
        this.id = id;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }
}
