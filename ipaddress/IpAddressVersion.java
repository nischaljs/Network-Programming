
import java.net.InetAddress;

public class IpAddressVersion {
    public static int getVersion(InetAddress addr) {
        byte[] baddr = addr.getAddress();
        if (baddr.length == 4) {
            return 4;
        } else {
            return 6;
        }
    }

    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            int v = getVersion(addr);
            System.out.println("Ip version " + v);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
