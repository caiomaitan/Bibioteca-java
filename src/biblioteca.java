import java.util.ArrayList;
import java.util.List;

public class biblioteca {
    private List<livro> acervo;

    public biblioteca() {
        this.acervo = new ArrayList<>();
    }

    public void adicionarLivro(livro livro) {
        acervo.add(livro);
    }

    public void removerLivro(livro livro) {
        acervo.remove(livro);
    }

    public void atualizarDisponibilidade(livro livro, boolean disponivel) {
        livro.setDisponivel(disponivel);
    }

    public void atualizarPrazo(livro livro, int novoPrazo) {
        livro.setPrazo(novoPrazo);
    }

    public livro buscarLivroPorISBN(String isbn) {
        for (livro livro : acervo) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    
}
