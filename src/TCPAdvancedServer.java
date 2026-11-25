import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TCPAdvancedServer {

    private int port;

    // Step 13: Store message history
    private final List<String> history = new ArrayList<>();

    // Step 14: Client ID
    private int nextClientId = 1;

    public TCPAdvancedServer(int port) {
        this.port = port;
    }

    public TCPAdvancedServer() {
        this(8082);
    }

    @Override
    public String toString() {
        return "TCP Advanced Server listening on port " + port;
    }

    public void launch() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println(this);

            while (true) {

                System.out.println("\nServer is waiting for clients...");

                Socket socket = serverSocket.accept();

                // Step 14: Assign a client ID
                int clientId = nextClientId++;

                String clientIP = socket.getInetAddress().getHostAddress();

                // Record the connection time
                Instant startTime = Instant.now();

                System.out.println(
                        "[" + LocalDateTime.now() + "] Client #"
                                + clientId + " connected: " + clientIP
                );

                try (
                        Socket clientSocket = socket;

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        );

                        PrintWriter writer = new PrintWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream(),
                                        StandardCharsets.UTF_8
                                ),
                                true
                        )
                ) {

                    // Step 13: Send the number of history messages
                    writer.println(history.size());

                    // Send history messages to the new client
                    for (String oldMessage : history) {
                        writer.println(oldMessage);
                    }

                    System.out.println(
                            "Sent " + history.size()
                                    + " history messages to Client #" + clientId
                    );

                    String message;

                    while ((message = reader.readLine()) != null) {

                        System.out.println(
                                "Client #" + clientId + ": " + message
                        );

                        // Step 13: Save the new message
                        String savedMessage =
                                "Client #" + clientId + ": " + message;

                        history.add(savedMessage);

                        // Keep only the last 10 messages
                        if (history.size() > 10) {
                            history.remove(0);
                        }

                        System.out.println(
                                "History size: " + history.size()
                        );

                        // Send a response to the client
                        writer.println(clientIP + ": " + message);

                        if (writer.checkError()) {
                            System.out.println("Failed to send response.");
                            break;
                        }
                    }

                } catch (IOException e) {

                    System.out.println(
                            "Client error: " + e.getMessage()
                    );

                } finally {

                    // Step 14: Calculate the connection duration
                    Instant endTime = Instant.now();

                    long duration = Duration.between(
                            startTime, endTime
                    ).getSeconds();

                    System.out.println(
                            "[" + LocalDateTime.now() + "] Client #"
                                    + clientId + " disconnected."
                    );

                    System.out.println(
                            "Session duration: " + duration + " seconds"
                    );
                }
            }

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        int port = 8082;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        TCPAdvancedServer server = new TCPAdvancedServer(port);
        server.launch();
    }
}