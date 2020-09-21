package server.process;

import server.Http.HttpRequest;
import server.Http.HttpResponse;

/**
 * @author chunibyo
 * @createTime 2019-06-12 00:17
 * @description Http静态请求处理
 */
public class HttpProcess {

    public HttpProcess() {

    }

    public void process(HttpRequest request, HttpResponse responce) {
        responce.sendMessage();
    }
}
