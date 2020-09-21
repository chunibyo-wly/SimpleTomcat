package server.jsp;

import server.Http.HttpRequest;
import server.HttpServer;
import server.Servlet.HttpServlet;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;

/**
 * @author chunibyo
 * @createTime 2019-06-18 14:58
 */
public abstract class HttpJspBase extends HttpServlet {

    HttpRequest request;

    public void _jspInit() {
    }

    public void _jspDestroy() {
    }

    public abstract void _jspService(HttpServletRequest request, HttpServletResponse response);

    public final void init() {
        super.init();
        request = HttpServer.request;
        _jspInit();
    }

    public final void destroy() {
        super.destroy();
        _jspDestroy();
    }

    public final void service(HttpServletRequest request, HttpServletResponse response) {
        _jspService(request, response);
    }
}
