import  java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    protected static int port = 8080;
    private static Socket client = null;
    private static PrintWriter outputStream = null;
    private static BufferedReader inputStream = null;
    private static String paraExtra = null;
   // private static final Scanner scanner = new Scanner(System.in);

    private static HashMap<Integer, Autore> autori;






    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        autori = new HashMap<>();
        autori.put(1, new Autore("Andrea1"));
        autori.put(2, new Autore("Andrea2"));
        autori.put(3, new Autore("Andrea3"));
        autori.put(4, new Autore("Andrea4"));
        autori.put(5, new Autore("Andrea5"));
        autori.get(1).addBook(new Libro("Cozzi1"));
        autori.get(1).addBook(new Libro("Cozzi2"));
        autori.get(1).addBook(new Libro("Cozzi3"));
        autori.get(1).addBook(new Libro("Cozzi4"));

        while (true) {
            try {
                System.out.println("Aspettando connessioni...");


                client = socket.accept();
                inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outputStream = new PrintWriter(client.getOutputStream(), true);

                String UserComand = "I comandi sono:\t-addAuthor: paramentro extra\t-addBook: parametro extra\t-getAuthorId: paramentro extra\t-getBooksOf: paramentro extra\t-getAuthorId\t-getAllBook\t";
                outputStream.write(UserComand+"\n");
                outputStream.flush();
                outputStream.wait();



                System.out.printf("Il client si è connesso, ed è sulla porta: %s\n", client.getLocalSocketAddress());




                String comando =inputStream.readLine();
                //System.out.println(comando);
                if(comando == null) comando ="";
               // System.out.printf("DEBUG: %s\n", comando );
                int code =(!comando.isEmpty()) ?Integer.parseInt(String.valueOf(comando.charAt(0))) : 0; //.valueOf trasforma il char in stringa
                comando = (!comando.isEmpty()) ?comando.substring(1) : "";
                if(code == 1 ){

                    paraExtra = comando.substring(comando.indexOf(" ")+1);
                    comando = comando.substring(0, comando.indexOf(" "));
                }

               // System.out.printf("DEBUG: %s\n", comando);
                if(!comando.isEmpty()) {
                    switch (comando) {
                        case "addAuthor" -> {
                            outputStream.write(addAuthor(paraExtra) + "\n");
                            outputStream.flush();
                        }
                        case "addBook" -> {
                            String nomeAutore = paraExtra.substring(0, paraExtra.indexOf("-"));
                            String nomeLibro = paraExtra.substring(paraExtra.indexOf("-") + 1);

                            //System.out.printf("DEBUG: %s\n%s\n", nomeAutore, nomeLibro);
                            outputStream.write(addBook(nomeAutore, nomeLibro) + "\n");
                            outputStream.flush();
                        }
                        case "getAllAuthor" -> {
                            outputStream.write(getAllAuthor() + "\n");
                            outputStream.flush();
                        }
                        case "getAuthorId" -> {
                            outputStream.write(getAuthorId(paraExtra) + "\n");
                            outputStream.flush();
                        }
                        case "getAllBooks" -> {
                            outputStream.write(getAllBooks() + "\n");
                            outputStream.flush();
                        }
                        case "getBooksOf" -> {
                            outputStream.write(getBookOf(paraExtra) + "\n");
                            outputStream.flush();
                        }
                        default -> {
                            outputStream.write("Nessun comando disponibile\n");
                            outputStream.flush();

                        }


                    }
                }




            } catch (Exception e) {
                outputStream.close();
                inputStream.close();
                socket.close();
                client.close();

                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
    private static String getAllBooks(){
        StringBuilder out = new StringBuilder();
        for(Map.Entry<Integer, Autore> autore : autori.entrySet()) {
            Autore nomeAutore = autore.getValue();
            out.append(nomeAutore.getNome()).append(":");
            out.append(nomeAutore.getLibri()).append(" ");
        }
        return out.toString();
    }
    private static boolean addBook(String nomeAutore, String nomeLibro) {
        for(Map.Entry<Integer,Autore> autoreCoppia : autori.entrySet()){
            Autore autore = autoreCoppia.getValue();
            if(autore.getNome().equals(nomeAutore)){
                return autore.addBook(new Libro(nomeLibro));
            }
        }
        return false;
    }

    private static String getAllAuthor(){
        ArrayList<String> out = new ArrayList<>();

        for(Map.Entry<Integer,Autore> autore : autori.entrySet()){
            Autore aut = autore.getValue();
            out.add(aut.getNome());
        }
        return  out.toString();
    }
    private static int getAuthorId(String nome)
    {
        int out =0;
        for(Map.Entry<Integer,Autore> autore : autori.entrySet()) {
            String nomeA = autore.getValue().getNome();
            if (nomeA.equals(nome)) {
                out = autore.getKey();
                break;
            }

        }
        return  out;
    }

    private static int addAuthor(String nome){
        Autore temp = new Autore(nome);
        autori.put(autori.size(), temp);
        return  (autori.containsValue(temp)) ? autori.get(autori.size()).getId(): -1;
    }
    private static String getBookOf(String nomeAutore){

        String libri = null;
        for(Map.Entry<Integer, Autore> auot : autori.entrySet()){
            String nome = auot.getValue().getNome();
            if(nome != null && nome.equals(nomeAutore)){
                libri = auot.getValue().getLibri();
                break;
            }
        }
    return (libri !=null) ? libri : "-1";
    }




}


