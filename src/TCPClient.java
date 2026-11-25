
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    private String host;
    private int port;

    // 构造函数：设置服务器IP地址和端口
    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {

        // 建立TCP连接
        try (
                Socket socket = new Socket(host, port);

                // 读取用户通过键盘输入的内容
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8)
                );

                // 接收服务器发送的消息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );

                // 向服务器发送消息
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),
                                StandardCharsets.UTF_8
                        ),
                        true
                )
        ) {






            System.out.println("Connected to server: " + host + ":" + port);
            System.out.println("Type /quit to disconnect.");

            String message;

            while (true) {

                System.out.print("Enter message: ");

                // 读取键盘输入
                message = keyboard.readLine();

                // 用户输入 /quit 或关闭标准输入
                if (message == null || message.equals("/quit")) {
                    break;
                }

                // 向服务器发送消息
                writer.println(message);

                if (writer.checkError()) {
                    System.out.println("Failed to send message.");
                    break;
                }

                // 接收服务器的回复
                String response = reader.readLine();

                // 检查服务器是否关闭连接
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                // 显示服务器的回复
                System.out.println("Server response: " + response);
            }

        } catch (UnknownHostException e) {

            System.out.println("Error: Server host not found.");

        } catch (ConnectException e) {

            System.out.println("Error: Cannot connect to the server.");

        } catch (SocketException e) {

            System.out.println("Connection error: " + e.getMessage());

        } catch (IOException e) {

            System.out.println("Communication error: " + e.getMessage());

        } finally {

            System.out.println("Client disconnected.");
        }
    }

    public static void main(String[] args) {

        String host = "localhost";
        int port = 8080;

        if (args.length > 0) {
            host = args[0];
        }

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        TCPClient client = new TCPClient(host, port);

        client.start();
    }
}
