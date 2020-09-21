package server.process;

import server.Http.HttpResponse;
import server.HttpServer;
import server.Servlet.HttpServlet;
import server.Servlet.ServletException;
import server.Servlet.ServletRequest;
import server.Servlet.ServletResponse;
import server.filter.Filter;
import server.filter.FilterChain;
import server.jsp.HttpJspBase;

import java.io.IOException;

/**
 * @author chunibyo
 * @createTime 2019-06-20 01:46
 */
public class filterProcess {
    public void process(ServletRequest request, ServletResponse response) {
        String value;
        if (HttpServer.filterConfiguration.containsKey("/*")) {
            value = HttpServer.filterConfiguration.get("/*");
        } else {
            value = HttpServer.filterConfiguration.get(request.getURI());
        }
        try {
            Class clazz = Class.forName(value);
            Filter filter = (Filter) clazz.newInstance();
            filter.doFilter(request, response, new FilterChain());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
