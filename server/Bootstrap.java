package server;

import java.io.IOException;

/**
 * @author chunibyo
 * @createTime 2019-06-11 13:41
 * @description Tomcat启动
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        new HttpServer().run();
    }
}
