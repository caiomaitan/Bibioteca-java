import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUsers extends JFrame{

    private final JTextField User;
    private final JPasswordField Senha;
    private final JLabel nome;
    private final JLabel password;
    private final JButton Criar;
    private final Usuarios usuarios;


    public AddUsers(Usuarios usuarios) {
        this.usuarios = usuarios;
        setTitle("AddUser");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        Criar = new JButton("Criar Usuario");
        User = new JTextField(20);
        Senha = new JPasswordField(20);
        nome = new JLabel("Nome");
        password = new JLabel("Senha");

        add(nome);
        add(User);
        add(password);
        add(Senha);
        add(Criar);

        Criar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarusuario();
            }
        });



    }

    private void criarusuario() {
        String nome = User.getText();
        String senha = new String(Senha.getPassword());

        addUser(nome, senha);
    }

    private void addUser(String username, String senha) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "INSERT INTO usuario (nome, senha) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, senha);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Criado com sucesso");
                usuarios.refreshUsers();



            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

}






