import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPAdvancedClient {

    private String host;
    private int port;

    public TCPAdvancedClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {

        try (
                Socket socket = new Socket(host, port);

                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(
                                System.in,
                                StandardCharsets.UTF_8
                        )
                );

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );

                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),
                                StandardCharsets.UTF_8
                        ),
                        true
                )
        ) {

            System.out.println(
                    "Connected to server: " + host + ":" + port
            );

            // Step 13: Receive the number of history messages
            String historyHeader = reader.readLine();

            if (historyHeader == null) {
                System.out.println("Server closed the connection.");
                return;
            }

            int historyCount = Integer.parseInt(historyHeader);

            System.out.println("\n===== Message History =====");
            System.out.println("History messages: " + historyCount);

            // Read all history messages
            for (int i = 0; i < historyCount; i++) {

                String oldMessage = reader.readLine();

                if (oldMessage == null) {
                    System.out.println("Server closed the connection.");
                    return;
                }

                System.out.println(oldMessage);
            }

            System.out.println("===========================");
            System.out.println("Type /quit to disconnect.");

            // Send and receive messages normally
            while (true) {

                System.out.print("Enter message: ");

                String message = keyboard.readLine();

                if (message == null || message.equals("/quit")) {
                    break;
                }

                writer.println(message);

                if (writer.checkError()) {
                    System.out.println("Failed to send message.");
                    break;
                }

                String response = reader.readLine();

                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                System.out.println("Server response: " + response);
            }

        } catch (UnknownHostException e) {

            System.out.println("Error: Server host not found.");

        } catch (ConnectException e) {

            System.out.println("Error: Cannot connect to the server.");

        } catch (NumberFormatException e) {

            System.out.println("Error: Invalid history format.");

        } catch (IOException e) {

            System.out.println("Communication error: " + e.getMessage());

        } finally {

            System.out.println("Client disconnected.");
        }
    }

    public static void main(String[] args) {

        String host = "localhost";
        int port = 8082;

        if (args.length > 0) {
            host = args[0];
        }

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        TCPAdvancedClient client = new TCPAdvancedClient(host, port);
        client.start();
    }
}