package server.GUI;

import server.HttpServer;

/**
 * @author chunibyo
 * @createTime 2019-06-20 17:43
 */
public class controlThread extends Thread {
    public void run() {
        System.out.println("controlThread run");
        new HttpServer().run();
    }
}
