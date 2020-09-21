package server.jsp;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.jsp.HttpJspBase;
import java.io.*;

public class test4_jsp extends HttpJspBase {
public void _jspService(HttpServletRequest request, HttpServletResponse response) {
PrintWriter out = response.getWriter();
out.println("<body>");
out.write("<html><head>    <meta http-equiv=Content-Type content=\"text/html;charset=utf-8\">    </head><body bgcolor=\"white\"><h1>The Echo JSP - Testing for Jsp tasks</h1>");
 java.util.Enumeration eh = request.getHeaderNames();    while (eh.hasMoreElements()) {        String h = (String) eh.nextElement();        out.print("<br> header: " + h);        out.println(" value: " + request.getHeader(h));    }
out.write("</body></html> ");
out.flush();
out.close();
}
}
