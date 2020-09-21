package server.Servlet;

import server.Http.HttpResponse;
import server.HttpServer;
import server.container.jspContainer;
import server.jsp.HttpJspBase;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author chunibyo
 * @createTime 2019-06-18 00:57
 */
public class HttpServletResponse extends ServletResponse {

    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public HttpServletResponse(OutputStream output) {
        super(output);
    }

    public void setContentType(String s) {
        contentType = s;
    }

    public PrintWriter getWriter() {
        return new PrintWriter(getOutputStream());
    }

    public void sendRedirect(String name) {
        jspContainer jsp = new jspContainer(name);
        try {
//            String headers = "HTTP/1.1 200\r\n" + "\r\n";
//            response.getOutputStream().write(headers.getBytes());
            Class clazz = Class.forName("server.jsp." + jsp.getFileName());
            HttpJspBase servlet = (HttpJspBase) clazz.newInstance();
            servlet._jspService((HttpServletRequest) HttpServer.request, (HttpServletResponse) HttpServer.response);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
