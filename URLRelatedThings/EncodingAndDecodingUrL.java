
import java.net.URLEncoder;
import java.net.URLDecoder;

public class EncodingAndDecodingUrL {

    public static void main(String[] args) {
        try {
            String text = "Hello World!";
            String encoded = URLEncoder.encode(text, "UTF-8");
            String decoded = URLDecoder.decode(encoded, "UTF-8");
            System.out.println("Original: " + text);
            System.out.println("Encoded: " + encoded);
            System.out.println("Decoded: " + decoded);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
