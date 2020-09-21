package server.course;

import server.Servlet.ServletException;
import server.Servlet.ServletRequest;
import server.Servlet.ServletResponse;
import server.filter.Filter;
import server.filter.FilterChain;
import server.filter.FilterConfig;

import java.io.IOException;


/**
 * Servlet Filter implementation class AccessFilter
 */
public class AccessFilter implements Filter {
    public static int nNum = 0;

    /**
     * Default constructor.
     */
    public AccessFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        // place your code here

        AccessFilter.nNum++;
        System.out.println(AccessFilter.nNum);
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
