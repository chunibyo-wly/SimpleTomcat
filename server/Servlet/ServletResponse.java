package server.Servlet;

import server.Http.HttpResponse;

import java.io.OutputStream;

/**
 * @author chunibyo
 * @createTime 2019-06-20 00:03
 */
public class ServletResponse extends HttpResponse {
    public ServletResponse(OutputStream output) {
        super(output);
    }
}
