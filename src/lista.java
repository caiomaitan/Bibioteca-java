import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lista extends JFrame {
    private JTextArea textarea;
    

    public lista() {
         textarea = new JTextArea();
        textarea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textarea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

      

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

   

    public void adicionarLivroNaLista(livro livro) {
        textarea.append("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() +
        ", Categoria: " + livro.getCategoria() + ", ISBN: " + livro.getISBN() +
        ", Prazo: " + livro.getPrazo() + ", Disponibilidade: " + livro.getDisponivel() + "\n");
    }

    public void removerLivroDaLista(livro livro) {
        String livroInfo = "Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() +
                ", Categoria: " + livro.getCategoria() + ", ISBN: " + livro.getISBN() +
                ", Prazo: " + livro.getPrazo() + ",  Disponibilidade:  "+ livro.getDisponivel() + "\n";
    
        String text = textarea.getText();
        String newText = text.replace(livroInfo, ""); 
        textarea.setText(newText);
    }

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
}
    

  
  


