import java.net.InetAddress;

public class InetAddressClass {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName("www.facebook.com");
            System.out.println(addr.getHostAddress());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
