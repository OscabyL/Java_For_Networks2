import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPStreamServer {

    public static void main(String[] args) {

        int port = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("TCP Stream Server started on port " + port);

            for (int test = 1; test <= 2; test++) {

                System.out.println("\nTest " + test);
                System.out.println("Waiting for client...");

                try (Socket socket = serverSocket.accept()) {

                    System.out.println("Client connected.");

                    // Wait for 1 second before reading data
                    Thread.sleep(1000);

                    InputStream input = socket.getInputStream();

                    // Set the receive buffer size to 1024 bytes
                    byte[] buffer = new byte[1024];

                    int bytesRead;
                    int totalBytes = 0;
                    int readCount = 0;

                    while ((bytesRead = input.read(buffer)) != -1) {

                        readCount++;
                        totalBytes += bytesRead;

                        System.out.println("Read " + readCount
                                + ": " + bytesRead + " bytes");

                        if (test == 1) {

                            String message = new String(
                                    buffer, 0, bytesRead,
                                    StandardCharsets.UTF_8);

                            System.out.println("Data: " + message);
                        }
                    }

                    System.out.println("Total read calls: " + readCount);
                    System.out.println("Total bytes received: " + totalBytes);
                    System.out.println("Client disconnected.");
                }
            }

            System.out.println("\nAll tests completed.");

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}