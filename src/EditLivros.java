import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditLivros extends JFrame {
    JTextField isbn;
    Livros livros;
    JButton Adicionar;

    JButton remover;
    JButton Editar;



    public EditLivros(Livros livros) {
        this.livros = livros;
        setTitle("Edição de Livros");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        isbn = new JTextField(20);
        Adicionar = new JButton(("Adicionar um Livro"));


        remover = new JButton("Remover Livro");
        Editar = new JButton("Editar Livro");



        JPanel centerpanel = new JPanel();
        centerpanel.add(Adicionar);




        JPanel searchPanel = new JPanel();




        JPanel southpanel = new JPanel();
        southpanel.add(remover);
        southpanel.add(Editar);
        southpanel.add(isbn);
        southpanel.add(new JLabel("ISBN do livro que deseja editar ou remover: "));






        add(southpanel, BorderLayout.SOUTH);
        add(centerpanel,BorderLayout.CENTER);



        remover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeLivro(isbn.getText());
            }
        });


        Editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro(isbn.getText());
            }
        });

        Adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriaLivros();

            }
        });




    }


    private void editarLivro(String isbn) {
        JFrame editar = new JFrame("Editar");
        editar.setSize(800, 400);
        editar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editar.setLocationRelativeTo(this);

        JPanel editPanel = new JPanel();

        JTextField nomeField = new JTextField(5);
        JTextField autorField = new JTextField(5);
        JTextField cateoriaField = new JTextField(5);
        JTextField isbnField = new JTextField(5);


        editPanel.add(new JLabel("Nome:"));
        editPanel.add(nomeField);
        editPanel.add(new JLabel("Autor: "));
        editPanel.add(autorField);
        editPanel.add(new JLabel("Categ:"));
        editPanel.add(cateoriaField);
        editPanel.add(new JLabel("ISBN:"));
        editPanel.add(isbnField);






        JButton salvar = new JButton("salvar");
        salvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String titulo = nomeField.getText();
                String autor = autorField.getText();
                String categoria =cateoriaField.getText();
                String isbn_ = isbnField.getText();

                String url = "jdbc:sqlite:banco.db";
                try (Connection connection = DriverManager.getConnection(url)) {
                    String query = "UPDATE livros SET titulo= ?, autor = ?,categoria = ? , isbn = ? WHERE isbn = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, titulo);
                        preparedStatement.setString(2, autor);
                        preparedStatement.setString(3, categoria);
                        preparedStatement.setInt(4,Integer.parseInt(isbn_));
                        preparedStatement.setInt(5, Integer.parseInt(isbn));
                        preparedStatement.executeUpdate();
                        livros.showLivro();
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


    private void removeLivro(String isbn) {
        String livroIsnb = isbn;


        String url = "jdbc:sqlite:banco.db";
        String query = "DELETE FROM livros WHERE isbn = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(livroIsnb));
            preparedStatement.executeUpdate();
            livros.showLivro();


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void CriaLivros(){
        JFrame AddEdit = new JFrame("Adicionar Livro");
        AddEdit.setSize(800, 400);
        AddEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AddEdit.setLocationRelativeTo(this);

        JTextField nome = new JTextField(20);
        JTextField autor = new JTextField(20);
        JTextField isbn = new JTextField(20);
        JTextField categoria = new JTextField(20);


        JPanel Addlivro = new JPanel(new GridLayout(5,2));
        Addlivro.add(new JLabel("Nome"));
        Addlivro.add(nome);
        Addlivro.add(new JLabel("Autor"));
        Addlivro.add(autor);
        Addlivro.add(new JLabel("Catego"));
        Addlivro.add(categoria);
        Addlivro.add(new JLabel("ISBN"));
        Addlivro.add(isbn);

        JButton Adicionar = new JButton("Adicionar");
        AddEdit.add(Adicionar,BorderLayout.SOUTH);

        AddEdit.add(Addlivro,BorderLayout.CENTER);

        Adicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionaLivro(nome.getText(), autor.getText(), categoria.getText(),isbn.getText());
                AddEdit.dispose();
            }
        });

        AddEdit.setVisible(true);
    }

    private void AdicionaLivro(String nome, String autor,String categoria ,String isbn_) {
        String url = "jdbc:sqlite:banco.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "INSERT INTO livros (titulo, autor, categoria, isbn) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, autor);
                preparedStatement.setString(3, categoria);
                preparedStatement.setInt(4, Integer.parseInt(isbn_));
                preparedStatement.executeUpdate();
                livros.showLivro();



                JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
