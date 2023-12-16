package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {

    public static void main(String[] args) {
        String nickname = null;
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Insira um nome:");
            nickname = consoleReader.readLine();

            out.println(nickname);

            // Recebe e exibe mensagens do servidor em tempo real
            Thread serverListener = new Thread(() -> {
                try {
                    while (true) {
                        String serverResponse = in.readLine();
                        if (serverResponse == null) {
                            break;
                        }
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("A connexão com o servidor foi perdida!");
                }
            });
            serverListener.start();

            // Envia mensagens para o servidor
            while (true) {
                String userInput = consoleReader.readLine();
                if ("sair".equalsIgnoreCase(userInput)) {
                    out.println(userInput);
                    break;
                }
                else if(tryParse(userInput)){
                    out.println(userInput);
                }else{
                    System.out.println("o valor inserido não é um numero!");
                }
            }

            serverListener.join(); // Aguarda a thread do servidor encerrar
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Não foi possivel se conectar!");
        }
    }

    public static boolean tryParse(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}