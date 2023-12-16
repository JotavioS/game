package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final GameServer server;
    private final Socket clientSocket;
    private String nickname;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(GameServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Bem vindo ao jogo de advinhação!");
            out.println("O tente adivinhar o numero que está entre 1 e 100!");

            this.nickname = in.readLine();

            System.out.println("Novo Jogador " + this.nickname + " conectado!");


            while (true) {
                String input = in.readLine();
                if (input == null || "sair".equalsIgnoreCase(input)) {
                    System.out.println("O jogador " + this.nickname + " foi desconectado!");
                    break;
                }

                server.broadcastMessage("O jogador " + this.nickname + " disse que o numero é : " + input, this);

                int playerNumber = Integer.parseInt(input);
                int secreteNumber = server.getNumberToGuess();

                if (playerNumber == secreteNumber) {
                    server.broadcastMessage("Mestre: O jogador " + this.nickname + " acertou! o Numero é: " + input, this);
                    server.broadcastMessage("Mestre: Um novo numero foi escolhido!", this);
                    server.generateSecretNumber();
                }
                else{
                    if(Math.abs(playerNumber - secreteNumber) <= 5){
                        server.broadcastMessage("Mestre: Está pegando fogo!", this);
                    }else if(Math.abs(playerNumber - secreteNumber) <= 10){
                        server.broadcastMessage("Mestre: Está muito quente!", this);
                    }else if (Math.abs(playerNumber - secreteNumber) <= 15) {
                        server.broadcastMessage("Mestre: Está quente!", this);
                    }else if (Math.abs(playerNumber - secreteNumber) <= 20) {
                        server.broadcastMessage("Mestre: Está frio!", this);
                    }else if (Math.abs(playerNumber - secreteNumber) <= 25) {
                        server.broadcastMessage("Mestre: Está muito frio!", this);
                    }else{
                        server.broadcastMessage("Mestre: Está congelando!", this);
                    }
                }
            }
        } catch (IOException e) {
            if(this.nickname != null){
                System.out.println("O Jogador " + this.nickname + " perdeu a conexão!");
            }else{
                System.out.println("Um Jogador não conseguiu completar a conexão!");
            }
        } finally {
            try {
                clientSocket.close();
                server.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
