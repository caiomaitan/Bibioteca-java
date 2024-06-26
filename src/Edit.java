import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Edit extends JFrame {
    JTextField id;
    Usuarios usuarios;
    JButton remover;
    JButton Adicionar;
    JButton Editar;
    JButton Adm;

    public Edit(Usuarios usuarios) {
        this.usuarios = usuarios;
        setTitle("Edição de Usuarios");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        id = new JTextField(20);
        Adicionar = new JButton("Adicionar Usuarios");
        remover = new JButton("Remover Usuario");
        Editar = new JButton("Editar Usuario");
        Adm = new JButton("Permissões");

        JPanel searchPanel = new JPanel();

        searchPanel.add(Adicionar,BorderLayout.WEST);

        JPanel southpanel = new JPanel();
        southpanel.add(new JLabel("ID: "));
        southpanel.add(id);

        southpanel.add(remover);
        southpanel.add(Editar);
        southpanel.add(Adm);

        add(searchPanel, BorderLayout.NORTH);
        add(southpanel, BorderLayout.SOUTH);

        remover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser(id.getText());
            }
        });


        Editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario(id.getText());
            }
        });

        Adm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admUser(id.getText());
            }
        });

        Adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriaUsuario();
            }
        });




    }


    private void editarUsuario(String id) {
        JFrame editar = new JFrame("Editar");
        editar.setSize(300, 200);
        editar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editar.setLocationRelativeTo(this);

        JPanel editPanel = new JPanel(new GridLayout(3, 2));

        JTextField nomeField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField();

        editPanel.add(new JLabel("Novo Nome:"));
        editPanel.add(nomeField);
        editPanel.add(new JLabel("Nova Senha:"));
        editPanel.add(senhaField);

        JButton salvar = new JButton("salvar");
        salvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userid = id;
                String username = nomeField.getText();
                String password = new String(senhaField.getPassword());
                String url = "jdbc:sqlite:banco.db";
                try (Connection connection = DriverManager.getConnection(url)) {
                    String query = "UPDATE usuario SET nome = ?, senha = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
                        preparedStatement.setString(3, userid);
                        preparedStatement.executeUpdate();
                        usuarios.refreshUsers();
                        editar.dispose();

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(editar, "Erro no banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editar.add(editPanel, BorderLayout.CENTER);
        editar.add(salvar, BorderLayout.SOUTH);
        editar.setVisible(true);

    }


    private void removeUser(String id) {
        String userId = id;


        String url = "jdbc:sqlite:banco.db";
        String query = "DELETE FROM usuario WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(userId));
            int rowsAffected = preparedStatement.executeUpdate();
            usuarios.refreshUsers();


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void admUser(String id) {
        String iduser = id;
        String url = "jdbc:sqlite:banco.db";

        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT admim FROM usuario WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet rs = preparedStatement.executeQuery();


                    int admuser = rs.getInt("admim");

                    if (admuser == 1) {
                        String sql = "UPDATE usuario SET admim = 0 WHERE id = ?";

                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
                            preparedStatement1.setInt(1, Integer.parseInt(id));
                            preparedStatement1.executeUpdate();
                            usuarios.refreshUsers();
                        }
                    } else {
                        String sql = "UPDATE usuario SET admim = 1 WHERE id = ?";

                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
                            preparedStatement1.setInt(1, Integer.parseInt(id));
                            preparedStatement1.executeUpdate();
                            usuarios.refreshUsers();
                        }
                    }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CriaUsuario() {
        JFrame frame = new JFrame("AddUser");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(5, 1));

        JTextField userField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JLabel nomeLabel = new JLabel("Nome");
        JLabel passwordLabel = new JLabel("Senha");
        JButton criarButton = new JButton("Criar Usuario");

        frame.add(nomeLabel);
        frame.add(userField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(criarButton);

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = userField.getText();
                String senha = new String(passwordField.getPassword());
                AddUsuario(nome, senha);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void AddUsuario(String username, String senha) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "INSERT INTO usuario (nome, senha) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, senha);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Criado com sucesso");
                usuarios.refreshUsers();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
