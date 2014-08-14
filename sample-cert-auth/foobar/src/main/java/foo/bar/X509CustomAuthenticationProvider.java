package foo.bar;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type X 509 custom authentication provider.
 */
@Component
public class X509CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected X509CustomUser retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        X509Certificate certificate = (X509Certificate) authentication.getPrincipal();

        // You can use a service to fetch the user from DB, LDAP or somewhere
        return new X509CustomUser(certificate.getSubjectDN().getName(), "", grantedAuths);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (X509AuthenticationToken.class.isAssignableFrom(authentication));
    }
}
