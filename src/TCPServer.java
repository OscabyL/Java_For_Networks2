
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    // 服务器监听的端口
    private int port;

    // 构造函数：指定端口
    public TCPServer(int port) {
        this.port = port;
    }

    // 默认构造函数：使用8080端口
    public TCPServer() {
        this(8080);
    }

    // 描述服务器信息
    @Override
    public String toString() {
        return "TCP Server listening on port " + port;
    }

    // 启动服务器
    public void launch() {

        // 创建ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println(this);
            System.out.println("Server is waiting for clients...");

            // 服务器一直运行
            while (true) {

                // 等待客户端建立连接
                Socket socket = serverSocket.accept();

                // 获取客户端IP地址
                String clientIP = socket.getInetAddress().getHostAddress();

                System.out.println("Client connected: " + clientIP);

                // 处理当前客户端
                try (
                        Socket clientSocket = socket;

                        // 创建输入流，用于接收消息
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        );

                        // 创建输出流，用于发送消息
                        PrintWriter writer = new PrintWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream(),
                                        StandardCharsets.UTF_8
                                ),
                                true
                        )
                ) {

                    String message;

                    // 不断读取客户端发送的消息
                    while ((message = reader.readLine()) != null) {

                        // 显示接收到的消息
                        System.out.println("Received: " + message);

                        // 回复客户端：IP地址 + 原始消息
                        writer.println(clientIP + ": " + message);
                    }

                } catch (IOException e) {

                    // 一个客户端出现错误，不影响整个服务器
                    System.out.println("Client error: " + e.getMessage());

                } finally {

                    System.out.println("Client disconnected: " + clientIP);
                    System.out.println("Server is waiting for clients...");
                }
            }

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Java程序入口
    public static void main(String[] args) {

        int port = 8080;

        // 如果用户输入了端口号，则使用指定端口
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        TCPServer server = new TCPServer(port);

        server.launch();
    }
}
