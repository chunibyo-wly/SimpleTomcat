package server.Http;

/*
    start-line
    *( header-field CRLF )
    CRLF
    [ message-body ]
 */

/*
    HTTP/1.1 200 OK

    Content-Type: text/html; charset=utf-8
    Date: Sat, 18 Feb 2017 00:01:57 GMT
    Server: nginx/1.11.8
    transfer-encoding: chunked
    Connection: Close



    <!doctype html>
    <html lang="en">
    <head>
    <meta charset="utf-8">
    <title>echo</title>
    ....略
 */


import server.HttpServer;

import java.io.*;

/**
 * @author chunibyo
 * @createTime 2019-06-11 11:31
 */
public class HttpResponse {

    private HttpRequest request;
    private OutputStream outputStream;
    private String method;
    private String URI;
//    protected String webroot = "src/resources/webroot";

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public HttpResponse() {
    }

    public HttpResponse(OutputStream output) {
        this.outputStream = output;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    /**
     * @description send response
     */
    public void sendMessage() {
        File file = new File(HttpServer.webroot + request.getURI());
        byte[] bytes = new byte[1024];
        int ch;
        FileInputStream fileInputStream = null;

        try {
            if (file.exists()) {
                outputStream.write("HTTP/1.1 200\r\n\r\n".getBytes());
                if (file.isFile()) {
                    fileInputStream = new FileInputStream(file);
                    while ((ch = fileInputStream.read(bytes, 0, 1024)) != -1) {
                        outputStream.write(bytes, 0, ch);
                    }
                } else {
                    File[] fileList = file.listFiles();
                    outputStream.write("<html><body><ul>".getBytes());
                    assert fileList != null;
                    for (File value : fileList) {
                        if (!value.isFile() && value.getName().endsWith("-INF")) continue;
                        outputStream.write(("<li" + "><a href = \"" + request.getURI() + "/" + value.getName() + "\">" + value.getName() + "</a></li>").getBytes());
                    }
                    outputStream.write("</ul></body></html>".getBytes());
                }
            } else {
                outputStream.write("HTTP/1.1 400\r\n\r\n".getBytes());
                fileInputStream = new FileInputStream(new File(HttpServer.webroot + "/404.html"));
                while ((ch = fileInputStream.read(bytes, 0, 1024)) != -1) {
                    outputStream.write(bytes, 0, ch);
                }
            }
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
