package foo.bar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.X509Certificate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.GenericFilterBean;

/**
 * The type X 509 custom filter.
 */
public class X509CustomFilter extends GenericFilterBean {
    public static final String X509 = "javax.servlet.request.X509Certificate";

    private AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getAttribute(X509) == null) {
            chain.doFilter(request, response);
            return;
        }

        X509Certificate[] certificates = (X509Certificate[]) request.getAttribute(X509);
        if (certificates.length > 0) {
            //Using the first certificate, we don't know how to identify several at once
            doAuthenticate((HttpServletRequest) request, (HttpServletResponse) response, certificates[0]);
        }

        chain.doFilter(request, response);
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response, X509Certificate certificate) {
        Authentication authResult;

        if (certificate == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No certificate found in request");
            }

            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("preAuthenticatedPrincipal = " + certificate + ", trying to authenticate");
        }

        try {
            X509AuthenticationToken authRequest = new X509AuthenticationToken(certificate, getPreAuthenticatedCredentials(request));
            authResult = authenticationManager.authenticate(authRequest);
            successfulAuthentication(request, response, authResult);
        } catch (AuthenticationException failed) {
            unsuccessfulAuthentication(request, response, failed);
            throw failed;
        }
    }

    /**
     * Sets authentication manager.
     *
     * @param authenticationManager the authentication manager
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Gets pre authenticated credentials.
     *
     * @param request the request
     * @return the pre authenticated credentials
     * @see org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedPrincipal(javax.servlet.http.HttpServletRequest)
     */

    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

    /**
     * Unsuccessful authentication.
     *
     * @param request the request
     * @param response the response
     * @param failed the failed
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("Cleared security context due to exception", failed);
        }

        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);
    }

    /**
     * Puts the <code>Authentication</code> instance returned by the authentication manager into the secure context.
     * @param request the request
     * @param response the response
     * @param authResult the auth result
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success: " + authResult);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }
}
