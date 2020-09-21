package server.Http;

import server.GUI.Home;
import server.Servlet.HttpServletRequest;
import server.session.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;

/*
    start-line
    *( header-field CRLF )
    CRLF
    [ message-body ]
 */

/*
  POST /?id=1 HTTP/1.1

  Host: echo.paw.cloud
  Content-Type: application/json; charset=utf-8
  User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0
  Connection: close
  Content-Length: 136

  CRLF

  {
    "status": "ok",
    "extended": true,
    "results": [
      {"value": 0, "type": "int64"},
      {"value": 1.0e+3, "type": "decimal"}
    ]
  }
 */

/**
 * @author chunibyo
 * @createTime 2019-06-11 11:30
 * @description request解析
 */
public class HttpRequest {

    private InputStream input;
    protected String requestString;
    private String URI;
    private String method;
    private String header = "";

    private Map<String, String> m = new HashMap<>();

    public HttpRequest() {

    }

    public HttpRequest(InputStream input) {
        this.input = input;
    }

    public String getURI() {
        return URI;
    }

    public String getMethod() {
        return method;
    }

    public InputStream getInput() {
        return input;
    }

    public String getRequestString() {
        return requestString;
    }

    public String getHeader() {
        return header;
    }


    public String getHeader(String h) {
        return m.get(h);
    }

    public Enumeration getHeaderNames() {
        Enumeration<String> HeaderNames;
        Vector<String> header = new Vector<>();
        String key;
        String value;
        for (String s : getHeader().split("\r\n")) {
            if (s.contains(": ")) {
                key = s.substring(0, s.indexOf(": ") - 1);
                value = s.substring(s.indexOf(": ") + 1);
                m.put(key, value);
                header.add(key);
            }
        }
        HeaderNames = header.elements();
        return HeaderNames;
    }

    public void setParameter(String parameter, Object o) {
        requestString += (parameter + "=" + o.toString() + "&");
    }

    public String getParameter(String parameter) {
        if (requestString.contains(parameter + "=")) {
            int begin = requestString.indexOf(parameter + "=");
            int end = requestString.indexOf("&", begin);
            if (end == -1) {
                return requestString.substring(begin + parameter.length() + 1);
            } else {
                return requestString.substring(begin + parameter.length() + 1, end);
            }
        } else {
            return null;
        }
    }

    /**
     * @description request解析
     */
    public void parseHttpRequest() {
        try {
            StringBuilder sb = new StringBuilder(1024);
            byte[] buffer = new byte[1024];

            int len = input.read(buffer);

            for (int i = 0; i < len; ++i) {
                sb.append((char) buffer[i]);
            }

            requestString = sb.toString();
            System.out.println(requestString);

            String[] buff = requestString.split(" |\r\n");
            if (buff.length < 2) return;

            header = requestString.substring(requestString.indexOf("\r\n"));

            method = buff[0];
            URI = buff[1];

            if (URI.endsWith("/")) URI = URI.substring(0, URI.lastIndexOf("/"));

//            System.out.println(method + " " + URI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public session getSession() {
        return new session();
    }

    public void setAttribute(String name, Object o) {
        attribute.put(name, o);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    private Map<String, Object> attribute = new HashMap<>();

}
