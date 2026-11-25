import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    // Port on which the server listens
    private int port;

    // Constructor: Set the specified port
    public TCPServer(int port) {
        this.port = port;
    }

    // Default constructor: Use port 8080
    public TCPServer() {
        this(8080);
    }

    // Describe the server information
    @Override
    public String toString() {
        return "TCP Server listening on port " + port;
    }

    // Start the server
    public void launch() {

        // Create a ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println(this);
            System.out.println("Server is waiting for clients...");

            // Keep the server running
            while (true) {

                // Wait for a client connection
                Socket socket = serverSocket.accept();

                // Get the client's IP address
                String clientIP = socket.getInetAddress().getHostAddress();

                System.out.println("Client connected: " + clientIP);

                // Handle the current client
                try (
                        Socket clientSocket = socket;

                        // Create an input stream to receive messages
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        );

                        // Create an output stream to send messages
                        PrintWriter writer = new PrintWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream(),
                                        StandardCharsets.UTF_8
                                ),
                                true
                        )
                ) {

                    String message;

                    // Continuously read messages from the client
                    while ((message = reader.readLine()) != null) {

                        // Display the received message
                        System.out.println("Received: " + message);

                        // Reply to the client: IP address + original message
                        writer.println(clientIP + ": " + message);
                    }

                } catch (IOException e) {

                    // A client error does not affect the entire server
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

    // Entry point of the Java program
    public static void main(String[] args) {

        int port = 8080;

        // Use the specified port if the user provides one
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        TCPServer server = new TCPServer(port);
        server.launch();
    }
}