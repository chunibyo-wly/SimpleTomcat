package server.process;

import server.Http.HttpRequest;
import server.Http.HttpResponse;
import server.HttpServer;
import server.Servlet.HttpServlet;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.Servlet.ServletException;
import server.container.jspContainer;
import server.jsp.HttpJspBase;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * @author chunibyo
 * @createTime 2019-06-12 17:05
 */
public class ServletProcess {

    public void process(HttpServletRequest request, HttpServletResponse response) {
        if (request.getURI().endsWith(".jsp")) {
            jspContainer jsp = new jspContainer(request.getURI().substring(1));

//            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//            int result = compiler.run(null, null, null, "src/server/jsp/" + jsp.getFileName() + ".java");
//            System.out.println("src/server/jsp/" + jsp.getFileName() + ".java" + result);

            try {
//                System.out.println("server.jsp." + jsp.getFileName());
                String headers = "HTTP/1.1 200\r\n" + "\r\n";
                response.getOutputStream().write(headers.getBytes());

//                Class c = new URLClassLoader(new URL[]{new URL("file:/" + "~/")}).loadClass(jsp.getFileName());

                Class clazz = Class.forName("server.jsp." + jsp.getFileName());
                HttpJspBase servlet = (HttpJspBase) clazz.newInstance();
                servlet._jspService(request, response);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        } else if (HttpServer.configuration.containsKey(request.getURI())) {
            try {
                String headers = "HTTP/1.1 200\r\n" + "\r\n";
                response.getOutputStream().write(headers.getBytes());
                String value = HttpServer.configuration.get(request.getURI());
                Class clazz = Class.forName(value);
                HttpServlet servlet = (HttpServlet) clazz.newInstance();
                servlet.service(request, response);
            } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
