package server.Servlet;

import server.container.jspContainer;
import server.jsp.HttpJspBase;

import java.io.IOException;

/**
 * @author chunibyo
 * @createTime 2019-06-18 01:02
 */
public class RequestDispatcher {

    String name;

    public RequestDispatcher(String s) {
        name = s;
    }

    public void forward(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println(request.getRequestString());
        jspContainer jsp = new jspContainer(name);
        try {
//            String headers = "HTTP/1.1 200\r\n" + "\r\n";
//            response.getOutputStream().write(headers.getBytes());
            Class clazz = Class.forName("server.jsp." + jsp.getFileName());
            HttpJspBase servlet = (HttpJspBase) clazz.newInstance();
            servlet._jspService(request, response);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
