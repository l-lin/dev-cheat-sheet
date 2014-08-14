package foo.bar;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The type X 509 custom user.
 */
public class X509CustomUser extends User {
    private static final long serialVersionUID = 846234442228459567L;

    /**
     * Instantiates a new X 509 custom user.
     *
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     */
    public X509CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * Instantiates a new X 509 custom user.
     *
     * @param username              the username
     * @param password              the password
     * @param enabled               the enabled
     * @param accountNonExpired     the account non expired
     * @param credentialsNonExpired the credentials non expired
     * @param accountNonLocked      the account non locked
     * @param authorities           the authorities
     */
    public X509CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
