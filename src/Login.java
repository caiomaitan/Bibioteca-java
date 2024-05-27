import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel statusLabel;

    public Login() {
        setTitle("Login Screen");
        setSize(300, 150); // Configura tamanho da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Configura ação ao fechar janela
        setLocationRelativeTo(null); // Centraliza janela

        // Inicializa componentes
        usernameLabel = new JLabel("Usuário:");
        passwordLabel = new JLabel("Senha:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        statusLabel = new JLabel();

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Adiciona componentes ao frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);
        add(new JLabel());
        add(statusLabel);

        // Adiciona ActionListener ao botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        });
    }

    private void validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        statusLabel.setText("Validating...");
        if (authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            statusLabel.setText("Login Successful");
            this.setVisible(false);
            new Menu().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
            statusLabel.setText("Invalid username or password.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        String dbUrl = "jdbc:sqlite:banco.db";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashPassword(password));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
