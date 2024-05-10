import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookWindow extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtISBN;
    private JTextField txtPrazo;

    private biblioteca biblioteca;
    private lista lista;

    public AddBookWindow(biblioteca biblioteca, lista lista) {
        super("Adicionar Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);

        this.biblioteca = biblioteca;
        this.lista = lista;

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField(20);
        panel.add(txtTitulo);

        panel.add(new JLabel("Autor:"));
        txtAutor = new JTextField(20);
        panel.add(txtAutor);

        panel.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField(20);
        panel.add(txtCategoria);

        panel.add(new JLabel("ISBN:"));
        txtISBN = new JTextField(20);
        panel.add(txtISBN);

        panel.add(new JLabel("Prazo de Empréstimo (dias):"));
        txtPrazo = new JTextField(20);
        panel.add(txtPrazo);

        JButton btnAdicionarLivro = new JButton("Adicionar Livro");
        btnAdicionarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarLivro();
            }
        });
        panel.add(btnAdicionarLivro);

        Container container = getContentPane();
        container.add(panel);

        setVisible(true);
    }

    private void adicionarLivro() {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String isbn = txtISBN.getText().trim();
        String prazoStr = txtPrazo.getText().trim();

        
        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || isbn.isEmpty() || prazoStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
            return;
        }

        
        int prazo;
        try {
            prazo = Integer.parseInt(prazoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O prazo de empréstimo deve ser um número inteiro.");
            return;
        }

    
        livro novoLivro = new livro(titulo, autor, categoria, isbn, true, prazo);

       
        biblioteca.adicionarLivro(novoLivro);
        lista.adicionarLivroNaLista(novoLivro);

        // Confirmation message
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");

        
        dispose();
    }
}
