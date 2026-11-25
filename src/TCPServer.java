import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;


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
    public void launch() throws IOException {

        try(ServerSocket socket = new ServerSocket(port);) {
            System.out.println("Well done, the socket is created");


            while (running) {
                // We wait a client
                Socket client = socket.accept();

                // We get what is coming in and what we write
                InputStream input = client.getInputStream();
                OutputStream output = client.getOutputStream();

                // We manage the input and the output (we manage what we write)
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);


                // We get the message from the client
                String message = reader.readLine();
                System.out.printf(message);

                // We send it back to client with the ip
                // We added another loop in order to read all the different messages from the client without we only read the first one
                while ((message = reader.readLine()) != null) {

                    System.out.println(message);
                    String ip = socket.getInetAddress().getHostAddress();
                    writer.println(ip + " : " + message);
                }

                // There is no message anymore, we stop the connection by closing the socket
                if (message == null) {
                    // Never forget to close a socket
                    client.close();
                    System.out.printf("The socket is closed");
                }
            }
        }catch (Exception e){
            System.out.printf("An error appeared");



        }





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