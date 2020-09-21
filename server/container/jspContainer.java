package server.container;

import server.Http.HttpRequest;
import server.HttpServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chunibyo
 * @createTime 2019-06-18 15:17
 */
public class jspContainer {
    String fileName;

    HttpRequest request;

    StringBuilder _jsp;

    int flag;

    public jspContainer(String fileName) {
        this.fileName = fileName;
        flag = 0;
        translate();
    }

    public String getFileName() {
        return fileName;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public void doHTML(String s) {

        if (flag == 0) {
            _jsp.append("out.write(\"")
                    .append(s);
            flag = 1;
        } else if (flag == 1) {
            _jsp.append(s);
        }

    }

    public void doscript(String s) {

        if (flag == 1) _jsp.append("\");\n");

        _jsp.append("out.println(")
                .append(s)
                .append(");\n");
    }

    public void doJava(String s) {
        if (flag == 1) _jsp.append("\");\n");

        _jsp.append(s).append("\n");
    }

    public void translate() {
        String content = HttpServer.readFile(HttpServer.webroot + "/" + fileName);

        fileName = fileName.substring(0, fileName.indexOf(".")) + "_jsp";

        File file = new File("src/jsp/" + fileName + ".java");
//        System.out.println(file.getName());
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        _jsp = new StringBuilder();

//        import
        _jsp.append("package server.jsp;\n" +
                "import server.Servlet.HttpServletRequest;\n" +
                "import server.Servlet.HttpServletResponse;\n" +
                "import server.jsp.HttpJspBase;\n" +
                "import java.io.*;\n\n");

        _jsp.append("public class ")
                .append(fileName)
                .append(" extends HttpJspBase {\n");
        _jsp.append("public void _jspService(HttpServletRequest request, HttpServletResponse response) {\n");
        _jsp.append("PrintWriter out = response.getWriter();\n");

//        _jsp.append("System.out.println(\"dddd\");\n");

//        jsp translate

        if (content.startsWith("<%@"))
            content = content.substring(content.indexOf("%>") + 2);

        if (!content.contains("<html>")) {
            _jsp.append("out.println(\"")
                    .append("<html>")
                    .append("\");\n");
        }
        if (!content.contains("<body>")) {
            _jsp.append("out.println(\"")
                    .append("<body>")
                    .append("\");\n");
        }

        for (int i = 0; i < content.length(); ++i) {
            if (i + 4 < content.length() && content.substring(i, i + 4).equals("<%--")) {
                i = content.indexOf("%>", i) + 1;
            } else if (i + 3 < content.length() && content.substring(i, i + 3).equals("<%=")) {
                flag = 1;
                doscript(content.substring(i + 3, content.indexOf("%>", i)));
                i = content.indexOf("%>", i) + 1;
                flag = 0;
            } else if (i + 2 < content.length() && content.substring(i, i + 2).equals("<%")) {
                flag = 1;
                doJava(content.substring(i + 2, content.indexOf("%>", i)));
                i = content.indexOf("%>", i) + 1;
                flag = 0;
            } else {
                if (content.substring(i, i + 1).equals("\"")) doHTML("\\\"");
                else doHTML(content.substring(i, i + 1));
            }
        }

        if (_jsp.toString().endsWith(">") || _jsp.toString().endsWith("> ") || _jsp.toString().endsWith(">\n"))
            _jsp.append("\");\n");

        if (!content.contains("</body>")) {
            _jsp.append("out.write(\"")
                    .append("</body>")
                    .append("\");\n");
        }

        if (!content.contains("</html>")) {
            _jsp.append("out.write(\"")
                    .append("</html>")
                    .append("\");\n");
        }

//        System.out.println(_jsp.toString());

        if (_jsp.toString().endsWith(">") || _jsp.toString().endsWith("> ") || _jsp.toString().endsWith(">\n"))
            _jsp.append("\");\n");

        _jsp
                .append("out.flush();\n")
                .append("out.close();\n")
                .append("}\n");

        _jsp.append("}\n");


//        generate jsp.java
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src/server/jsp/" + fileName + ".java");
            fileWriter.write(_jsp.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
