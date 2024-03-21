import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final int port = Server.port;
    private static final String address = "127.0.0.1";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try (Socket client = new Socket(address, port)) {
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outputStream = new PrintWriter(client.getOutputStream(), true);

                System.out.println(inputStream.readLine());
                System.out.println("Inserisci il comando");
                String comando = scanner.nextLine().trim();

                if(!checkComando(comando))
                {
                    System.out.println("Comando non valido");
                    break;
                }

                if (comando.equals("exit")) {
                    // You may add any cleanup code here before breaking the loop
                    break;
                }

                if (comando.contains(" "))
                    comando = comando.substring(1);

                if (comando.equals("addAuthor") || comando.equals("addBook") || comando.equals("getAuthorId")
                        || comando.equals("getBooksOf")) {
                    System.out.println("Inserisci il parametro extra");
                    String paraExtra = scanner.nextLine().trim();
                    outputStream.write("1" + comando + " " + paraExtra + "\n");
                    outputStream.flush();
                } else {
                    outputStream.write("2" + comando + "\n");
                    outputStream.flush();
                }

                System.out.println("La risposta del server: " + inputStream.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private static boolean  checkComando(String comando){
        boolean out = false;
        if(comando == null) return false;
        switch (comando) {
            case "addAuthor", "addBook", "getBooksOf", "getAuthorId", "getAllBooks", "exit" -> out = true;
        }
        return out;
    }
}
