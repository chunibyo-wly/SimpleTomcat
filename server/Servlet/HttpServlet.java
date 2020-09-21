package server.Servlet;

import server.Http.HttpRequest;
import server.HttpServer;

import java.io.IOException;

/**
 * @author chunibyo
 * @createTime 2019-06-18 00:56
 */
public class HttpServlet {

    public HttpServlet() {
    }

    public void init() {
    }

    public void destroy() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) {
//        if (request.getMethod().equals("GET")) {
//            doGet(request, response);
//        } else if (request.getMethod().equals("POST")) {
//            doPost(request, response);
//        }
        try {

            if (HttpServer.readFile("src/server/course" + request.getURI() + ".java").contains("doPost")) {
                doPost(request, response);
            } else {
                doGet(request, response);
                System.out.println("src/server/course" + request.getURI() + ".java");
            }

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
