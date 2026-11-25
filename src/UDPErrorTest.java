import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPErrorTest {

    public static void main(String[] args) {

        System.out.println("Test 1: Unconnected UDP");
        testUDP(false);

        System.out.println("\nTest 2: Connected UDP");
        testUDP(true);
    }

    public static void testUDP(boolean connected) {

        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress address =
                    InetAddress.getByName("127.0.0.1");

            int port = 8080;

            // Wait for a maximum of 3 seconds
            socket.setSoTimeout(3000);

            // Call UDP connect() if needed
            if (connected) {

                socket.connect(address, port);

                System.out.println("UDP socket connected.");
            }

            // Prepare the data to send
            byte[] data = "Hello UDP".getBytes(
                    StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(
                    data, data.length, address, port);

            // Send the data
            socket.send(packet);

            System.out.println("Message sent.");

            // Wait for the server response
            byte[] buffer = new byte[1024];

            DatagramPacket response = new DatagramPacket(
                    buffer, buffer.length);

            socket.receive(response);

            System.out.println("Response received.");

        } catch (PortUnreachableException e) {

            System.out.println(
                    "Error: Destination port unreachable.");

        } catch (SocketTimeoutException e) {

            System.out.println(
                    "Timeout: No response from server.");

        } catch (IOException e) {

            System.out.println(
                    "UDP error: " + e.getMessage());
        }
    }
}