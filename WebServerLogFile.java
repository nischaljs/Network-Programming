import java.io.*;

public class WebServerLogFile {
    public static void main(String[] args) throws Exception {
        // Ensure the file www.google.com exists in the same directory as your compiled
        // class
        // and contains the log content provided above.

        File logFile = new File("www.google.com");
        if (!logFile.exists()) {
            System.err.println("Error: Log file 'www.google.com' not found.");
            return;
        }

        FileInputStream fin = new FileInputStream(logFile);
        InputStreamReader rd = new InputStreamReader(fin);
        BufferedReader breader = new BufferedReader(rd);

        String logEntry;

        try {
            System.out.println("--- Reading Web Server Log Entries ---");
            // Loop until breader.readLine() returns null (end of file)
            while ((logEntry = breader.readLine()) != null) {
                // Print the log entry
                System.out.println(logEntry);

                if (logEntry.contains(" 404 ")) {
                    System.out.println(" --> Found a 404 error!");
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {

            try {
                if (breader != null)
                    breader.close();
                if (rd != null)
                    rd.close();
                if (fin != null)
                    fin.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
