package foo.bar;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * The type X 509 authentication token.
 */
public class X509AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 7855950531717712571L;

    /**
     * Instantiates a new X 509 authentication token.
     *
     * @param principal   the principal
     * @param credentials the credentials
     */
    public X509AuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * Instantiates a new X 509 authentication token.
     *
     * @param principal   the principal
     * @param credentials the credentials
     * @param authorities the authorities
     */
    public X509AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
