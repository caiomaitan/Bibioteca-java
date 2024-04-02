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
      ", Prazo: " + livro.getPrazo() + "\n");
    }

  
  
}

