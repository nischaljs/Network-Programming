import java.io.IOException;
import java.net.*;

public class pickingLocalInterface {
    public static void main(String[] args) {
        try {
           InetAddress address = InetAddress.getByName("localhost");
           for(int i = 1024 ; i<65535; i++){
                Socket socket = new Socket("localhost",3000,address, i);
                System.out.println("Connected to port: " + i);
                socket.close();
           }
          
    }
     catch (Exception e) {  
                e.printStackTrace();
              }
}
}