import java.net.URL
public class ResolvingRelativeURL {
    public static void main(String[] args) {
        try {
            URL base = new URL("http://example.com:8080/path/to/resource");
            URL relative = new URL(base, "subpath");
            System.out.println("Resolved URL: " + relative);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
