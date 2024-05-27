public class livro {
    public String Titlulo;
    public String Autor;
    public String Categoria;
    public String ISBN;
    public boolean Disponibilidade;
    public int Prazo;

    public livro(String titulo, String autor, String categoria , String isbn,boolean disponivel, int prazo) {
        this.Titlulo= titulo;
        this.Autor = autor;
        this.Categoria = categoria;
        this.ISBN = isbn;
        this.Disponibilidade = true;
        this.Prazo =prazo;
    }

}
