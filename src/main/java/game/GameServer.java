package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private static final int PORT = 12345;
    private int secretNumber;

    private List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("O Servidor está rodando. Aguardando por jogadores...");
            generateSecretNumber();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public int getNumberToGuess() {
        return secretNumber;
    }

    public void generateSecretNumber(){
        this.secretNumber = (int) (Math.random() * 100) + 1;
        System.out.println("O numero escolhido foi: " + this.secretNumber);
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}