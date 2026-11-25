public class TCPServer {

    private int port;

    // Generate a TCP server with a given port
    public TCPServer(int port) {
        this.port = port;
    }

    // Generate a TCP server with a default port
    public TCPServer() {
        this.port = 8080;
    }

    // Start of the lauch function that the server will listen a socker
    public void launch(){

    }

    // Overide the soString function
    @Override
    public String toString() {
        return "UDPServer: port=" + port ;
    }


    // Main function that open a port
    public static void main(String[] args) {
        System.out.println("Hello");

        int port= 8080;
        TCPServer server = new TCPServer(port);

        server.launch();
    }
}