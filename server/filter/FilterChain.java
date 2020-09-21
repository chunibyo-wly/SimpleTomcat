package server.filter;

import server.HttpServer;
import server.Servlet.ServletRequest;
import server.Servlet.ServletResponse;

/**
 * @author chunibyo
 * @createTime 2019-06-20 00:04
 * @description 若开发人员调用filterChain的doFilter方法，则web服务器就会调用web资源的service方法，
 * 即web资源就会被访问，否则web资源不会被访问
 */
public class FilterChain {
    public void doFilter(ServletRequest request, ServletResponse response) {
        HttpServer.chain = 1;
    }
}
