package server.filter;

import server.Servlet.ServletException;
import server.Servlet.ServletRequest;
import server.Servlet.ServletResponse;

import java.io.IOException;

public interface Filter {
    public void destroy();
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;
    public void init(FilterConfig fConfig) throws ServletException;
}
