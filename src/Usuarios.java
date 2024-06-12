import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Usuarios extends JFrame {

    private  JTextArea displayArea;

    private  JButton Edit;
    private  JTextField searchField;
    private  JButton searchButton;

    public Usuarios() {
        setTitle("Usuarios");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        Edit = new JButton("Edit");
        searchField = new JTextField(15);
        searchButton = new JButton("Buscar");


        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(Edit, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Buscar: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        northPanel.add(searchPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);


        showUsers();



        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Edit(Usuarios.this).setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUsers(searchField.getText());
            }
        });
    }

    public void showUsers() {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT id, nome, admim FROM usuario";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int isadm = rs.getInt("admim");
                    result.append("ID: ").append(id).append(", Nome: ").append(nome).append(" ADM: ").append(isadm).append("\n");
                }
                displayArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchUsers(String query) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT id, nome, admim FROM usuario WHERE id LIKE ? OR nome LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, "%" + query + "%");
                ResultSet rs = pstmt.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int isadm = rs.getInt("admim");
                    result.append("ID: ").append(id).append(", Nome: ").append(nome).append(" ADM: ").append(isadm).append("\n");
                }
                displayArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshUsers() {
        showUsers();
    }

}