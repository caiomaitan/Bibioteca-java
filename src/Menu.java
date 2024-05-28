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

public class Menu extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);


    private final JButton EditarUsuarios;
    private String username;


    public Menu(String username){
        this.username = username;

        setTitle("Menu");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);



        EditarUsuarios= new JButton("Editar");
        if(isAdmin(username)) {
            add(EditarUsuarios);
        }

        EditarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Usuarios().setVisible(true);
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
            ex.printStackTrace();
            return false;
        }
    }








}
