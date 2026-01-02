import java.io.*;
import java.net.*;

public class CreateSocketConstructor{
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            InetSocketAddress address = new InetSocketAddress("localhost", 3000);
            socket.connect(address);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Received: " + reader.readLine());

            if(socket.isConnected()){
                System.out.println("Socket connected successfully.");
            }
            socket.close();
           
        } catch (Exception e) {
            e.printStackTrace();
}
    }
}