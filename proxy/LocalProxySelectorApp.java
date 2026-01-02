import java.io.*;
import java.net.*;
import java.util.*;

/*
 ============================================================================
  LocalProxySelectorApp
 ============================================================================
  THEORY OVERVIEW (EXAM IMPORTANT)

  1. Proxy:
     A proxy server acts as an intermediary between a client and the internet.
     Client → Proxy → Internet → Proxy → Client

     Reasons for using proxy:
     - Security (firewall, filtering)
     - Monitoring
     - Caching
     - Anonymity
     - Corporate networks

  2. ProxySelector:
     - A Java mechanism to decide WHICH proxy should be used for a given URI.
     - JVM calls select(URI) BEFORE opening any network connection.
     - If connection via proxy fails, JVM calls connectFailed(...).

  3. ProxySelector is GLOBAL:
     - Once set using ProxySelector.setDefault(),
       all URLConnections in the JVM will use it.

  4. Real-world analogy:
     - You want to send a letter.
     - First you try sending via a courier office (proxy).
     - If the office is closed → you send directly (NO_PROXY).

 ============================================================================
*/

public class LocalProxySelectorApp {

    // ===================== CUSTOM PROXY SELECTOR =====================
    public static class localProxyServer extends ProxySelector {

        /*
         proxyHost & proxyPort:
         - Address of proxy server
         - Example: localhost:8080 (Burp, mitmproxy, etc.)
        */
        public final String proxyHost;
        public final int proxyPort;

        /*
         failedList:
         - Stores URIs for which proxy connection failed
         - Used to switch to DIRECT connection next time
        */
        public final List<URI> failedList = new ArrayList<>();

        public localProxyServer(String host, int port) {
            this.proxyHost = host;
            this.proxyPort = port;
        }

        /*
         select(URI uri)
         ----------------
         - JVM calls this method BEFORE connecting
         - We decide: proxy or direct?
        */
        @Override
        public List<Proxy> select(URI uri) {
            System.out.println("\n[ProxySelector] select() called");
            System.out.println("  Requested URI : " + uri);

            if (uri == null) {
                throw new IllegalArgumentException("URI can't be null");
            }

            /*
             If proxy already failed for this URI
             → bypass proxy → DIRECT connection
            */
            synchronized (failedList) {
                if (failedList.contains(uri)) {
                    System.out.println(
                        "[ProxySelector] Proxy already failed earlier"
                    );
                    System.out.println(
                        "[ProxySelector] Using DIRECT connection (NO_PROXY)"
                    );
                    return Collections.singletonList(Proxy.NO_PROXY);
                }
            }

            /*
             If proxy is not configured
             → DIRECT connection
            */
            if (proxyHost == null || proxyHost.isEmpty()) {
                System.out.println("[ProxySelector] Proxy not configured");
                System.out.println("[ProxySelector] Using DIRECT connection");
                return Collections.singletonList(Proxy.NO_PROXY);
            }

            /*
             Create HTTP proxy
             IMPORTANT: Proxy.Type.HTTP is mandatory for HTTP/HTTPS URLs
            */
            SocketAddress address = new InetSocketAddress(proxyHost, proxyPort);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

            System.out.println(
                "[ProxySelector] Trying HTTP proxy -> " +
                    proxyHost +
                    ":" +
                    proxyPort
            );

            return Collections.singletonList(proxy);
        }

        /*
         connectFailed(...)
         -----------------
         - JVM calls this method when proxy connection fails
         - We log failure and mark URI
        */
        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            System.out.println("\n[ProxySelector] connectFailed() called");
            System.out.println("  URI           : " + uri);
            System.out.println("  Proxy Address : " + sa);
            System.out.println("  Failure Reason: " + ioe);

            if (uri != null) {
                synchronized (failedList) {
                    if (!failedList.contains(uri)) {
                        failedList.add(uri);
                        System.out.println(
                            "[ProxySelector] URI marked as FAILED"
                        );
                        System.out.println(
                            "[ProxySelector] Future requests will use DIRECT"
                        );
                    }
                }
            }
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        /*
         Proxy details
         - localhost:8080 assumes a local proxy
         - If no proxy is running → fallback will happen
        */
        String proxyHost = "localhost";
        int proxyPort = 8080;

        System.out.println("[Main] Installing custom ProxySelector");
        ProxySelector.setDefault(new localProxyServer(proxyHost, proxyPort));

        try {
            System.out.println("\n[Main] Creating URL");
            URL url = new URL(
                "https://www.tutorialsteacher.com/csharp/csharp-interface"
            );

            /*
             We will attempt connection TWICE:
             1st → via proxy
             2nd → direct (if proxy failed)
            */
            boolean connected = false;
            int attempt = 0;

            while (!connected && attempt < 2) {
                attempt++;

                try {
                    System.out.println(
                        "\n[Main] Attempt " + attempt + " to connect"
                    );

                    System.out.println("[Main] Opening connection");
                    URLConnection urlConn = url.openConnection();

                    /*
                     Timeouts (EXAM IMPORTANT)
                     - Prevents infinite waiting
                     - Must be set BEFORE connect()
                    */
                    urlConn.setConnectTimeout(10000);
                    urlConn.setReadTimeout(10000);

                    System.out.println("[Main] Connecting...");
                    urlConn.connect();

                    System.out.println("[Main] Connection SUCCESSFUL");
                    connected = true;

                    System.out.println(
                        "[Main] Reading response (first line)...\n"
                    );

                    try (
                        BufferedReader rd = new BufferedReader(
                            new InputStreamReader(urlConn.getInputStream())
                        )
                    ) {
                        System.out.println(rd.readLine());
                    }
                } catch (IOException ex) {
                    System.out.println(
                        "[Main] Attempt " +
                            attempt +
                            " FAILED: " +
                            ex.getMessage()
                    );
                }
            }

            if (!connected) {
                System.out.println("\n[Main] Could not connect after retries");
            }
        } catch (Exception ex) {
            System.out.println("\n[Main] FATAL ERROR:");
            ex.printStackTrace();
        }
    }
}
