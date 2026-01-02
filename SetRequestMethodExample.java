import java.net.*;

public class SetRequestMethodExample {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://example.com");
            
            // 1. Cast URLConnection to HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 2. Now setRequestMethod is accessible
            connection.setRequestMethod("GET");
            
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // 3. Use getRequestMethod() instead of getRequestProperty
            System.out.println("Request Method: " + connection.getRequestMethod());
            System.out.println("Connection: " + connection.getRequestProperty("Connection"));
            System.out.println("User-Agent: " + connection.getRequestProperty("User-Agent"));
            
            // It's good practice to disconnect
            connection.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}