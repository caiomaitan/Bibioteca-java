import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gestaodelivorsGUI extends JFrame {
    private biblioteca biblioteca;

    public gestaodelivorsGUI() {
        super("Gestão de Livros");

        biblioteca = new biblioteca();

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

        JButton btnListadelivros =new JButton("Lista");                         

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(btnAdicionarLivro);
        panel.add(btnRemoverLivro);
        panel.add(btnAtualizarDisponibilidade);
        panel.add(btnAtualizarPrazo);

        Container container = getContentPane();
        container.add(panel);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void adicionarLivro() {
        String titulo = JOptionPane.showInputDialog("Informe o título do livro:");
        String autor = JOptionPane.showInputDialog("Informe o autor do livro:");
        String categoria = JOptionPane.showInputDialog("Informe a categoria do livro:");
        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro:");
        int prazo = Integer.parseInt(JOptionPane.showInputDialog("Informe o prazo de empréstimo do livro:"));

        livro novoLivro = new livro(titulo, autor, categoria, isbn, prazo);
        biblioteca.adicionarLivro(novoLivro);
        JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
    }

    private void removerLivro() {
        String isbn = JOptionPane.showInputDialog("Informe o ISBN do livro que deseja remover:");
        livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            biblioteca.removerLivro(livro);
            JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
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
        } else {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.");
        }
    }


    //teste
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new gestaodelivorsGUI();
            }
        });
    }
}
