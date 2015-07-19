package hu.fnf.devel.wishbox.gateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WebSessionAuthNToken extends AbstractAuthenticationToken {
    private String id;

    public WebSessionAuthNToken(Collection<? extends GrantedAuthority> authorities, String id) {
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
