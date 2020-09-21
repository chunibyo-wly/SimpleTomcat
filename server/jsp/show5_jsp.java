package server.jsp;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.jsp.HttpJspBase;
import java.io.*;

public class show5_jsp extends HttpJspBase {
public void _jspService(HttpServletRequest request, HttpServletResponse response) {
PrintWriter out = response.getWriter();
out.write("<!DOCTYPE html><html><head>    <title>Testing for Filter</title><body><h1>Testing for Filter</h1><p>    The site have been visited ");
out.println(server.course.AccessFilter.nNum);
out.write(" times.<p></body></html>");
out.flush();
out.close();
}
}
