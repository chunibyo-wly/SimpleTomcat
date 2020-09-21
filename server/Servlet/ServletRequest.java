package server.Servlet;

import server.Http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author chunibyo
 * @createTime 2019-06-20 00:04
 */
public class ServletRequest extends HttpRequest {

    public ServletRequest(InputStream inputStream) {
        super(inputStream);
    }
}
