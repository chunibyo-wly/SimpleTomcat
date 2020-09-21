package server;

import server.GUI.Home;
import server.Http.HttpRequest;
import server.Http.HttpResponse;
import server.Servlet.HttpServlet;
import server.Servlet.HttpServletRequest;
import server.Servlet.HttpServletResponse;
import server.Thread.ServerThread;
import server.filter.FilterChain;
import server.process.HttpProcess;
import server.process.ServletProcess;
import server.process.filterProcess;

import java.io.*;
import java.lang.module.Configuration;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chunibyo
 * @createTime 2019-06-17 19:04
 */
public class HttpServer {

    private Thread t;

    public static final String webroot = "src/resources/webroot";
    public static int port = 8080;
    public static Map<String, String> configuration = new HashMap<>();
    public static Map<String, String> filterConfiguration = new HashMap<>();

    public static int chain = 0;

    public static HttpRequest request;
    public static HttpResponse response;

    public static boolean state;

    static {
        System.out.println("静态初始化");
        String content = readFile("src/resources/webroot/WEB-INF/web.xml");
        chain = 0;
        state = true;
        port = Home.port;
        parseXML(content);
//        System.out.println(configuration);
//        System.out.println(filterConfiguration);
//        configuration.put("/servlet/PetServlet", "server.course.PetServlet");
//        configuration.put("/servlet/PetServlet2", "server.course.PetServlet2");
//        configuration.put("/servlet/Login", "server.course.Login");
    }

    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();

        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    result.append(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return result.toString();
    }

    public static void parseXML(String content) {
        configuration.clear();
        filterConfiguration.clear();
        int fromIndex = 0;
        int index;
        while ((index = content.indexOf("<servlet-name>", fromIndex)) != -1) {
            fromIndex = index + 14;
            configuration.put(
                    content.substring(content.indexOf("<url-pattern>", fromIndex) + 13, content.indexOf("</url-pattern>", fromIndex))
                    ,
                    content.substring(content.indexOf("<servlet-class>", fromIndex) + 15, content.indexOf("</servlet-class>", fromIndex))
                            + "." +
                            content.substring(fromIndex, content.indexOf("</servlet-name>", fromIndex))
            );
            fromIndex = content.indexOf("<servlet-name>", fromIndex) + 14;
        }

        fromIndex = 0;
        while ((index = content.indexOf("<filter-name>", fromIndex)) != -1) {
            fromIndex = index + 13;
            filterConfiguration.put(
                    content.substring(content.indexOf("<url-pattern>", fromIndex) + 13, content.indexOf("</url-pattern>", fromIndex))
                    ,
                    content.substring(content.indexOf("<filter-class>", fromIndex) + 14, content.indexOf("</filter-class>", fromIndex))
                            + "." +
                            content.substring(fromIndex, content.indexOf("</filter-name>", fromIndex))
            );
            fromIndex = content.indexOf("<filter-name>", fromIndex) + 13;
        }
    }

    private void await() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (HttpServer.state) {
            try {
                if (serverSocket != null) {
                    Socket socket = serverSocket.accept();
                    System.out.println("连接建立");
                    ServerThread serverThread = new ServerThread(socket);
                    serverThread.start();
                    serverThread.join();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("HttpServer close");
    }

    public void run() {
        await();
        System.out.println("服务器关闭");
    }

    public void close() {
        state = false;
    }

    public static void main(String[] args) {
        new HttpServer().await();
    }
}
