package server.Thread;

import server.HttpServer;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.process.HttpProcess;
import server.process.ServletProcess;
import server.process.filterProcess;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chunibyo
 * @createTime 2019-06-20 13:41
 */
public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("serverThread run");
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            //request 处理
            HttpServer.request = new HttpServletRequest(inputStream);
            HttpServer.request.parseHttpRequest();

            //response 处理
            HttpServer.response = new HttpServletResponse(outputStream);
            HttpServer.response.setRequest(HttpServer.request);

            if (HttpServer.request.getURI().equals("/SHUTDOWN")) {
                HttpServer.state = false;
                socket.close();
                return;
            }

            if (HttpServer.request.getURI() != null) {
                if (HttpServer.filterConfiguration.containsKey("/*") || HttpServer.filterConfiguration.containsKey(HttpServer.request.getURI())) {
                    HttpServer.chain = 0;
                    new filterProcess().process((HttpServletRequest) HttpServer.request, (HttpServletResponse) HttpServer.response);
                }
                if (HttpServer.chain == 1)
                    if (HttpServer.configuration.containsKey(HttpServer.request.getURI()) || HttpServer.request.getURI().endsWith(".jsp")) {
                        new ServletProcess().process((HttpServletRequest) HttpServer.request, (HttpServletResponse) HttpServer.response);
                    } else {
                        new HttpProcess().process(HttpServer.request, HttpServer.response);
                    }
            }

            if (socket != null) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
