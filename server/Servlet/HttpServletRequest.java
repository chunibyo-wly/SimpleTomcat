package server.Servlet;

import server.Http.HttpRequest;

import java.io.InputStream;
import java.util.*;

/**
 * @author chunibyo
 * @createTime 2019-06-18 00:57
 */
public class HttpServletRequest extends ServletRequest {

    private RequestDispatcher requestDispatcher;

    public HttpServletRequest(InputStream inputStream) {
        super(inputStream);
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        requestDispatcher = new RequestDispatcher(s);
        return requestDispatcher;
    }

}
