package server.jsp;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.jsp.HttpJspBase;
import java.io.*;

public class test2_jsp extends HttpJspBase {
public void _jspService(HttpServletRequest request, HttpServletResponse response) {
PrintWriter out = response.getWriter();
out.println("<html>");
out.println("<body>");
out.write("<b>Testing for first JSP</b><br><b> current time is:    ");
out.println(  new java.util.Date() );
out.write("</b>");
out.write("</body>");
out.write("</html>");
out.flush();
out.close();
}
}
