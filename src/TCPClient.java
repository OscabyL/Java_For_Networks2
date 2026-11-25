import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    private String host;
    private int port;

    // Constructor: Set the server IP address and port
    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {

        // Establish a TCP connection
        try (
                Socket socket = new Socket(host, port);

                // Read user input from the keyboard
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8)
                );

                // Receive messages from the server
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );

                // Send messages to the server
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

                // Read keyboard input
                message = keyboard.readLine();

                // The user enters /quit or closes the standard input
                if (message == null || message.equals("/quit")) {
                    break;
                }

                // Send the message to the server
                writer.println(message);

                if (writer.checkError()) {
                    System.out.println("Failed to send message.");
                    break;
                }

                // Receive the response from the server
                String response = reader.readLine();

                // Check if the server has closed the connection
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                // Display the server response
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