class ConstructorToURL {

    public ConstructorToURL(String url) {
        try {
            java.net.URL urlObj = new java.net.URL(url);
            System.out.println("Protocol: " + urlObj.getProtocol());
            System.out.println("Host: " + urlObj.getHost());
            System.out.println("Port: " + urlObj.getPort());
            System.out.println("File: " + urlObj.getFile());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        ConstructorToURL constructorToURL = new ConstructorToURL(
            "http://example.com:8080/path/to/resource"
        );
    }
}
