public class EqualitandCOmperisionUri {
    public static void main(String[] args) {
        try {
            URL base = new URL("http://example.com:8080/path/to/resource");
            URL url = new URL(base, "subpath");
            URL url2 = new URL(base, "subpath");
            System.out.println("Resolved URL: " + url);
            if(url.equals(url2)) {
                System.out.println("URLs are equal");
            } else {
                System.out.println("URLs are not equal");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }