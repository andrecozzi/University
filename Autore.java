import java.util.ArrayList;

public class Autore {
    private final String nome;
    private  final int id = (int)(Math.random()*100);
    private final  ArrayList<Libro> libri;

   public Autore(String nome){
       this.nome = nome;
       this.libri = new ArrayList<>();
   }
   public boolean addBook(Libro libro){
       this.libri.add(libro);
       return this.libri.contains(libro);
   }

    public String getLibri() {
       StringBuilder out= new StringBuilder();
        for(Libro libro : libri){
            if(!libro.getNome().isEmpty())
                out.append(libro.getNome()).append(" ");
        }
        return out.toString();
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}
