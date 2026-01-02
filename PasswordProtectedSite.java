import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class PasswordProtectedSite {

    public static void main(String[] args) {
        // Register custom authenticator
        Authenticator.setDefault(new DialogAuthenticator());

        try {
            // Protected URL (HTTP Basic Authentication)
            URL url = new URL("https://httpbin.org/basic-auth/user/passwd");
            HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            BufferedReader reader;

            if (status >= 200 && status < 300) {
                reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
                );
            } else {
                reader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream())
                );
            }

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DialogAuthenticator extends Authenticator {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private PasswordAuthentication authentication;

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        showLoginDialog();
        return authentication;
    }

    private void showLoginDialog() {
        JDialog dialog = new JDialog((Frame) null, "Login", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 180);
        dialog.setLocationRelativeTo(null);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(userLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passLabel);
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            authentication = new PasswordAuthentication(
                usernameField.getText(),
                passwordField.getPassword()
            );
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            authentication = null;
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}
