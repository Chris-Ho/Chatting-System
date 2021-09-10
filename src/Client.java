import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private BufferedReader in;
    private Socket socket;

    public Client(){
        try {
            socket = new Socket("localhost", 14001);
            new Thread(new InputThread(socket)).start();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assert socket != null;
        loop(socket, in);
    }

    public void loop(Socket socket, BufferedReader in){
        while(!socket.isClosed()){
            String received;
            try {
                received = in.readLine();
                if (received != null){
                    System.out.println(received);
                }
                else{
                    socket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        exit();
    }

    public void exit(){
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
