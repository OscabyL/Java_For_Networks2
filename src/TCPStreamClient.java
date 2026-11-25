import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TCPStreamClient {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 8081;

        // Test 1: Send three short messages consecutively
        try (Socket socket = new Socket(host, port)) {

            OutputStream output = socket.getOutputStream();

            System.out.println("Test 1: Multiple short messages");

            output.write("Hello".getBytes(StandardCharsets.UTF_8));
            output.write("World".getBytes(StandardCharsets.UTF_8));
            output.write("Java".getBytes(StandardCharsets.UTF_8));

            output.flush();

            System.out.println("Write calls: 3");
            System.out.println("Total bytes sent: 14");

            socket.shutdownOutput();

        } catch (IOException e) {

            System.out.println("Test 1 error: " + e.getMessage());
        }

        // Test 2: Send 10000 bytes at once
        try (Socket socket = new Socket(host, port)) {

            OutputStream output = socket.getOutputStream();

            System.out.println("\nTest 2: One large message");

            byte[] data = new byte[10000];

            Arrays.fill(data, (byte) 'A');

            output.write(data);
            output.flush();

            System.out.println("Write calls: 1");
            System.out.println("Total bytes sent: 10000");

            socket.shutdownOutput();

        } catch (IOException e) {

            System.out.println("Test 2 error: " + e.getMessage());
        }
    }
}