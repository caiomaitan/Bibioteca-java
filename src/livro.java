public class livro {
    private String titulo;
    private String autor;
    private String categoria;
    private String isbn;
    private boolean disponivel;
    private int prazo;

    public livro(String titulo, String autor, String categoria , String isbn,boolean disponivel, int prazo) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.isbn = isbn;
        this.disponivel = true; 
        this.prazo = prazo;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getISBN(){
        return isbn;
    }

    public String getAutor(){
        return autor;
    }

    public String getCategoria(){
        return categoria;
    }

    public boolean getDisponivel(){
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    

    public String toString() {
        return "Livro [titulo=" + titulo + ", autor=" + autor + ", categoria=" + categoria + ", isbn=" + isbn
                + ", disponivel=" + disponivel + ", prazo=" + prazo + "]";
    }
}
