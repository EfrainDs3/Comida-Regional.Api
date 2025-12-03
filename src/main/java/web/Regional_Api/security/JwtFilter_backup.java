package web.Regional_Api.security;

import jakarta.servlet.GenericFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

// Backup file - Disabled by removing @Component
// @Component 
public class JwtFilter_backup extends GenericFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, res);
    }
}