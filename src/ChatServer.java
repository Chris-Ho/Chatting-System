import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private ServerSocket serverSocket;
    private ArrayList<ConnectionThread> clients;

    public ChatServer(){
        clients = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(14001); //creates server socket with port "14001" to allow connections
        } catch (IOException e) {
            e.printStackTrace();
        }
        loop();
    }

    public void loop(){
        while(!serverSocket.isClosed()){
            acceptClients(serverSocket);
        }

    }

    public synchronized void acceptClients(ServerSocket serverSocket){ //synchronized prevents unexpected errors if two clients connect simultaneously
        try {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            ConnectionThread connection = new ConnectionThread(socket, this);
            clients.add(connection);
            new Thread(connection).start();
        } catch (IOException ignored) {
        }
    }

    public void exit(){
        System.out.println("Server is closing down");
        try{
            serverSocket.close();
            for(ConnectionThread client: clients){
                client.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        for (ConnectionThread client: clients){
            client.out.println(message);
        }
    }

    public static void main(String[] args){
        new ChatServer();
    }

}