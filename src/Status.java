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

public class Status extends JFrame {
    private  JButton Devolver;
    private  JButton Retirar;
    private  Menu menu;

    public Status(Menu menu) {
        setTitle("Emprestimos");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel South = new JPanel();
        this.menu = menu;

        Devolver = new JButton("Devolver Livro");
        Retirar = new JButton("Retirar Livro");

        South.add(Devolver);
        South.add(Retirar);
        add(South, BorderLayout.SOUTH);

        Retirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRetirar();
            }
        });

        Devolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDevolver();
            }
        });
    }

    private void setRetirar() {
        JFrame retirar = new JFrame("Retirar Livros");
        retirar.setSize(800, 400);
        retirar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        retirar.setLocationRelativeTo(this);

        JPanel Texto = new JPanel();
        JPanel Botao = new JPanel();

        JTextField Titulo = new JTextField(20);
        JTextField Autor = new JTextField(20);
        JTextField Semanas = new JTextField(10);
        JButton Retirar_ = new JButton("Retirar");

        Texto.add(new JLabel("Titulo"));
        Texto.add(Titulo);
        Texto.add(new JLabel("Autor"));
        Texto.add(Autor);
        Texto.add(new JLabel("Prazo"));
        Texto.add(Semanas);
        Botao.add(Retirar_);

        Retirar_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retirada(Titulo.getText(), Autor.getText(), Semanas.getText());
            }
        });

        retirar.add(Botao, BorderLayout.SOUTH);
        retirar.add(Texto, BorderLayout.CENTER);
        retirar.setVisible(true);
    }

    private void retirada(String titulo, String autor, String semana) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT disponibilidade FROM livros WHERE titulo = ? AND autor = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, autor);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    int disponibilidade = rs.getInt("disponibilidade");
                    if (disponibilidade == 0) {
                        String query_ = "UPDATE livros SET disponibilidade = 1, prazo = ? WHERE titulo = ? AND autor = ?";
                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(query_)) {
                            preparedStatement1.setInt(1, Integer.parseInt(semana));
                            preparedStatement1.setString(2, titulo);
                            preparedStatement1.setString(3, autor);
                            preparedStatement1.executeUpdate();
                            menu.showLivros();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Livro já retirado");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Livro não encontrado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDevolver() {
        JFrame devolver = new JFrame("Devolver");
        devolver.setSize(300, 200);
        devolver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        devolver.setLocationRelativeTo(this);

        JPanel Texto = new JPanel();
        JPanel Botao = new JPanel();

        JTextField Titulo = new JTextField(20);
        JTextField Autor = new JTextField(20);
        JButton Devolver_ = new JButton("Devolver");

        Texto.add(new JLabel("Titulo"));
        Texto.add(Titulo);
        Texto.add(new JLabel("Autor"));
        Texto.add(Autor);

        Botao.add(Devolver_);

        Devolver_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolvido(Titulo.getText(), Autor.getText());
            }
        });

        devolver.add(Botao, BorderLayout.SOUTH);
        devolver.add(Texto, BorderLayout.CENTER);
        devolver.setVisible(true);
    }

    private void devolvido(String titulo, String autor) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT disponibilidade FROM livros WHERE titulo = ? AND autor = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, autor);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    int dispo = rs.getInt("disponibilidade");
                    if (dispo == 1) {
                        String query_ = "UPDATE livros SET disponibilidade = 0, prazo = 0 WHERE titulo = ? AND autor = ?";
                        try (PreparedStatement preparedStatement1 = connection.prepareStatement(query_)) {
                            preparedStatement1.setString(1, titulo);
                            preparedStatement1.setString(2, autor);
                            preparedStatement1.executeUpdate();
                            menu.showLivros();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Livro já devolvido");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Livro não encontrado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
