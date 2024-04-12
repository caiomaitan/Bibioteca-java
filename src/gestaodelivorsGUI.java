import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gestaodelivorsGUI extends JFrame {
    private biblioteca biblioteca;
    private lista lista;

    public gestaodelivorsGUI(lista lista) {
        super("Gestão de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        biblioteca = new biblioteca();
        this.lista = lista;

        JButton btnAdicionarLivro = new JButton("Adicionar Livro");
        btnAdicionarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarLivro();
            }
        });

        JButton btnRemoverLivro = new JButton("Remover Livro");
        btnRemoverLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });

        JButton btnAtualizarDisponibilidade = new JButton("Atualizar Disponibilidade");
        btnAtualizarDisponibilidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarDisponibilidade();
            }
        });

        JButton btnAtualizarPrazo = new JButton("Atualizar Prazo");
        btnAtualizarPrazo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarPrazo();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(btnAdicionarLivro);
        panel.add(btnRemoverLivro);
        panel.add(btnAtualizarDisponibilidade);
        panel.add(btnAtualizarPrazo);

        Container container = getContentPane();
        container.add(panel);

        setSize(300, 200);
        setVisible(true);
    }

    private void adicionarLivro() {
        String titulo = JOptionPane.showInputDialog("Informe o título do livro:");
        if (titulo == null || titulo.trim().isEmpty()) { // Verifica se o título está vazio ou só contém espaços em branco
            JOptionPane.showMessageDialog(null, "Por favor, informe um título válido.");
            return;
        }

        String autor = JOptionPane.showInputDialog("Informe o autor do livro:");
        if (autor == null || autor.trim().isEmpty()) { // Verifica se o autor está vazio ou só contém espaços em branco
            JOptionPane.showMessageDialog(null, "Por favor, informe um autor válido.");
            return;
        }

        String categoria = JOptionPane.showInputDialog("Informe a categoria do livro:");
        if (categoria == null || categoria.trim().isEmpty()) { // Verifica se a categoria está vazia ou só contém espaços em branco
            JOptionPane.showMessageDialog(null, "Por favor, informe uma categoria válida.");
            return;
        }

        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro:");
        if (isbn == null || isbn.trim().isEmpty()) { // Verifica se o ISBN está vazio ou só contém espaços em branco
            JOptionPane.showMessageDialog(null, "Por favor, informe um ISBN válido.");
            return;
        }

        if (biblioteca.buscarLivroPorISBN(isbn) != null) {
            JOptionPane.showMessageDialog(null, "Já existe um livro com este ISBN na biblioteca.");
            return;
        }

        int prazo;
        boolean disponivel = true;
        while (true) {
            String input = JOptionPane.showInputDialog("Informe o prazo de empréstimo do livro:");
            try {
                prazo = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "O prazo de empréstimo deve ser um número inteiro.");
            }
        }

        livro novoLivro = new livro(titulo, autor, categoria, isbn, disponivel, prazo);

        biblioteca.adicionarLivro(novoLivro);
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
        lista.adicionarLivroNaLista(novoLivro);
    }

    private void removerLivro() {
        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro que deseja remover:");
        livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            biblioteca.removerLivro(livro);
            JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
            lista.removerLivroDaLista(livro);
        } else {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
        }
    }

    private void atualizarDisponibilidade() {
        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro que deseja atualizar a disponibilidade:");
        livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            boolean disponivel = JOptionPane.showConfirmDialog(null, "O livro está disponível?") == JOptionPane.YES_OPTION;
            biblioteca.atualizarDisponibilidade(livro, disponivel);
            JOptionPane.showMessageDialog(null, "Disponibilidade atualizada com sucesso!");
            lista.atualizarDisponibilidadeNaLista(livro, disponivel);
        } else {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
        }
    }

    private void atualizarPrazo() {
        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro que deseja atualizar o prazo:");
        livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            int novoPrazo = Integer.parseInt(JOptionPane.showInputDialog("Informe o novo prazo de empréstimo do livro:"));
            biblioteca.atualizarPrazo(livro, novoPrazo);
            JOptionPane.showMessageDialog(null, "Prazo atualizado com sucesso!");
            lista.atualizarPrazoNaLista(livro, novoPrazo);
        } else {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
        }
    }
}
