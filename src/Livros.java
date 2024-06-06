import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livros extends JFrame {

    private final JTextArea displayArea;

    private final JButton EditLivros;
    private final JTextField searchField;
    private final JButton searchButton;

    public Livros() {
        setTitle("Livros");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        EditLivros = new JButton("Edit");
        searchField = new JTextField(15);
        searchButton = new JButton("Buscar");

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(EditLivros, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Buscar: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        northPanel.add(searchPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchLivro(searchField.getText());
            }
        });



        EditLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditLivros(Livros.this).setVisible(true);


            }
        });

        showLivro();
    }

    public void showLivro() {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM livros";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String categoria = rs.getString("categoria");
                    int isbn = rs.getInt("isbn");
                    int prazo = rs.getInt("prazo");
                    int disponibilidade = rs.getInt("disponibilidade");

                    result.append("Título: ").append(titulo).append("  ");
                    result.append("Autor: ").append(autor).append("  ");
                    result.append("Categoria: ").append(categoria).append("  ");
                    result.append("ISBN: ").append(isbn).append("  ");
                    result.append("Prazo: ").append(prazo).append("  ");
                    result.append("Disponibilidade: ").append(disponibilidade).append("\n");
                }
                displayArea.setText(result.toString());
            } catch (SQLException e) {

                e.printStackTrace();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private void searchLivro(String query) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM livros WHERE titulo LIKE ? OR autor LIKE ? OR isbn LIKE ? OR categoria LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, "%" + query + "%");
                pstmt.setString(3, "%" + query + "%");
                pstmt.setString(4, "%" + query + "%");
                ResultSet rs = pstmt.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String categoria = rs.getString("categoria");
                    int isbn = rs.getInt("isbn");
                    int prazo = rs.getInt("prazo");
                    int disponibilidade = rs.getInt("disponibilidade");

                    result.append("Título: ").append(titulo).append("  ");
                    result.append("Autor: ").append(autor).append("  ");
                    result.append("Categoria: ").append(categoria).append("  ");
                    result.append("ISBN: ").append(isbn).append("  ");
                    result.append("Prazo: ").append(prazo).append("  ");
                    result.append("Disponibilidade: ").append(disponibilidade).append("\n");
                }
                displayArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}





