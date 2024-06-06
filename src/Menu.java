import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Menu extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    private final JButton editarUsuarios;
    private final JButton Status;
    private final JButton editarLivros;
    private final JTextArea displayArea;
    private String username;
    private final JTextField searchField;
    private final JButton searchButton;

    public Menu(String username) {
        this.username = username;

        setTitle("Menu");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel north = new JPanel();

        searchField = new JTextField(15);
        searchButton = new JButton("Buscar");
        north.add(searchField);
        north.add(searchButton);


        Status = new JButton("Retirada/Devolução");
        north.add(Status);

        editarLivros = new JButton("Editar livros");
        editarUsuarios = new JButton("Editar Usuarios");
        JPanel admPanel = new JPanel();
        admPanel.add(editarLivros);
        admPanel.add(editarUsuarios);
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        if (isAdmin(username)) {
            add(admPanel, BorderLayout.SOUTH);
        }

        add(north, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        showLivros();

        editarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Usuarios().setVisible(true);
            }
        });

        editarLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Livros().setVisible(true);
            }
        });

        Status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Status(Menu.this).setVisible(true);
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                searchLivro(query);
            }
        });
    }

    public boolean isAdmin(String username) {
        String dbUrl = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT admim FROM usuario WHERE nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int isAdmin = resultSet.getInt("admim");
                        return isAdmin == 1;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Database connection error: ", ex);
            return false;
        }
    }

    public void showLivros() {
        String dbUrl = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT * FROM livros WHERE disponibilidade = 0";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet rs = preparedStatement.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String categoria = rs.getString("categoria");
                    int isbn = rs.getInt("isbn");

                    result.append("Título: ").append(titulo).append("  ");
                    result.append("Autor: ").append(autor).append("  ");
                    result.append("Categoria: ").append(categoria).append("  ");
                    result.append("ISBN: ").append(isbn).append('\n');
                }
                displayArea.setText(result.toString());
            } catch (SQLException e) {
                logger.error("Error fetching book data: ", e);
            }
        } catch (SQLException e) {
            logger.error("Database connection error: ", e);
        }
    }

    private void searchLivro(String query) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM livros WHERE (titulo LIKE ? OR autor LIKE ? OR isbn LIKE ? OR categoria LIKE ?) AND disponibilidade = 0";
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


                    result.append("Título: ").append(titulo).append("  ");
                    result.append("Autor: ").append(autor).append("  ");
                    result.append("Categoria: ").append(categoria).append("  ");
                    result.append("ISBN: ").append(isbn).append('\n');

                }
                displayArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



}

