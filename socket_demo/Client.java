import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server IP address (use "localhost" for same machine)
        int serverPort = 8080; // Server port number

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            // Send a message to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello from Client!");

            // Receive a response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (Exception e) {
            System.err.println("Error in client: " + e.getMessage());
        }
    }
}
