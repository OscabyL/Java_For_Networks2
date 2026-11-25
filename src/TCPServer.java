import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    private int port;
    private boolean running = false;

    // Generate a TCP server with a given port
    public TCPServer(int port, boolean running) {
        this.port = port;
        this.running = running;
    }

    // Generate a TCP server with a default port
    public TCPServer() {
        this.port = 8080;
        this.running = true;
    }

    // Start of the lauch function that the server will listen a socker
    public void launch() throws SocketException{
        DatagramSocket socket = new DatagramSocket(port);
        System.out.println("Well done, the socket is created");
        System.out.println(" ");



    }

    // Overide the soString function
    @Override
    public String toString() {
        return "UDPServer: port=" + port ;
    }


    // Main function that open a port
    public static void main(String[] args) throws Exception{
        System.out.println("Hello");

        // We create the parameters, the server and we launch it
        int port= 8080;
        boolean running = true;
        TCPServer server = new TCPServer(port, running);

        server.launch();
    }
}