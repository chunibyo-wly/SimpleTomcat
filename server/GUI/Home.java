package server.GUI;

import server.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.OutputStream;

/**
 * @author chunibyo
 * @createTime 2019-06-20 16:54
 */
public class Home {


    private controlThread t;

    private JPanel panel;

    private Socket s;

    public static int port = 8080;

    private static JTextAreaOutputStream out;

    public Home() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (t == null || !t.isAlive()) {
                    t = new controlThread();
                    HttpServer.state = true;
                    t.start();
                }
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (t != null && t.isAlive()) {
                    try {
                        s = new Socket("127.0.0.1", port);
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        out.println("GET /SHUTDOWN HTTP/1.1\r\n\r\n");
                        s.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String URI = editorPane1.getText();
                editorPane1.setText("");

                if (t == null || !t.isAlive()) {
                    try {
                        port = Integer.parseInt(URI);
                    } catch (Exception ex) {
                        port = 8080;
                    }
                    return;
                }

                if (URI.equals("")) URI = "/";
                if (URI.startsWith("/")) {
                    try {
                        Runtime.getRuntime().exec("firefox localhost:" + port + URI);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        JTextArea jta = new JTextArea();
        jta.setFont(new Font(null, Font.BOLD, 20));
        jta.setEditable(false);//设置不可编辑
        JTextAreaOutputStream out = new JTextAreaOutputStream(jta);
        System.setOut(new PrintStream(out));//设置输出重定向
        System.setErr(new PrintStream(out));//将错误输出也重定向,用于e.pritnStackTrace
        this.panel.add(jta);
        JScrollPane jsp = new JScrollPane(jta);//设置滚动条
        this.panel.add(jsp);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }

    private JButton closeButton;
    private JButton startButton;
    private JEditorPane editorPane1;
    private JButton commitButton;
}

class JTextAreaOutputStream extends OutputStream {
    private final JTextArea destination;


    public JTextAreaOutputStream(JTextArea destination) {
        if (destination == null)
            throw new IllegalArgumentException("Destination is null");

        this.destination = destination;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        final String text = new String(buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                destination.append(text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }
}

