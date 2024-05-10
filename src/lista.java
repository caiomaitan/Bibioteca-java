import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lista extends JFrame {
    private JTextArea textarea;
    private JTextField searchField; // Adiciona o campo de pesquisa

    public lista() {
        textarea = new JTextArea();
        textarea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textarea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(); // Cria um painel para a barra de pesquisa
        searchField = new JTextField(20); // Define o tamanho do campo de pesquisa
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Pesquisar"); // Adiciona o botão de pesquisa
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText(); // Obtém o termo de pesquisa
                pesquisarLivros(searchTerm); // Chama o método de pesquisa
            }
        });
        searchPanel.add(searchButton);

        getContentPane().add(searchPanel, BorderLayout.NORTH); // Adiciona o painel de pesquisa acima da área de texto

        JButton btnAbrirGestao = new JButton("Gestao de Livros");
        btnAbrirGestao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new gestaodelivorsGUI(lista.this);
            }
        });
        getContentPane().add(btnAbrirGestao, BorderLayout.SOUTH);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para adicionar um livro à lista
    public void adicionarLivroNaLista(livro livro) {
        textarea.append("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() +
                ", Categoria: " + livro.getCategoria() + ", ISBN: " + livro.getISBN() +
                ", Prazo: " + livro.getPrazo() + ", Disponibilidade: " + livro.getDisponivel() + "\n");
    }

    // Método para remover um livro da lista
    public void removerLivroDaLista(livro livro) {
        String livroInfo = "Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() +
                ", Categoria: " + livro.getCategoria() + ", ISBN: " + livro.getISBN() +
                ", Prazo: " + livro.getPrazo() + ",  Disponibilidade:  "+ livro.getDisponivel() + "\n";

        String text = textarea.getText();
        String newText = text.replace(livroInfo, "");
        textarea.setText(newText);
    }

    // Método para atualizar o prazo de um livro na lista
    public void atualizarPrazoNaLista(livro livro, int novoPrazo) {
        String isbn = livro.getISBN();
        String[] lines = textarea.getText().split("\n");
        StringBuilder newText = new StringBuilder();

        for (String line : lines) {
            if (line.contains("ISBN: " + isbn)) {
                line = line.replaceFirst("Prazo: \\d+", "Prazo: " + novoPrazo);
            }
            newText.append(line).append("\n");
        }

        textarea.setText(newText.toString());
    }

    // Método para atualizar a disponibilidade de um livro na lista
    public void atualizarDisponibilidadeNaLista(livro livro, boolean disponibilidade) {
        String isbn = livro.getISBN();
        String[] lines = textarea.getText().split("\n");
        StringBuilder newText = new StringBuilder();

        for (String line : lines) {
            if (line.contains("ISBN: " + isbn)) {

                String newDisponibilidade = disponibilidade ? "true" : "false";
                line = line.replaceFirst("Disponibilidade: .+", "Disponibilidade: " + newDisponibilidade);
            }
            newText.append(line).append("\n");
        }

        textarea.setText(newText.toString());
    }

    // Método para pesquisar livros por título, autor, categoria ou ISBN
    public void pesquisarLivros(String termo) {
        // Limpa a área de texto
        textarea.setText("");

        // Percorre todos os livros e verifica se o termo de pesquisa está presente em algum atributo
        for (livro livro : biblioteca.getAcervo()) {
            if (livro.getTitulo().toLowerCase().contains(termo.toLowerCase()) ||
                livro.getAutor().toLowerCase().contains(termo.toLowerCase()) ||
                livro.getCategoria().toLowerCase().contains(termo.toLowerCase()) ||
                livro.getISBN().toLowerCase().contains(termo.toLowerCase())) {
                adicionarLivroNaLista(livro); // Se o livro corresponder ao termo, adiciona-o à lista
            }
        }
    }
}