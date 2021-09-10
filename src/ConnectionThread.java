import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionThread implements Runnable{
    private Socket socket;
    private ChatServer server;
    public PrintWriter out;
    public BufferedReader in;

    public ConnectionThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //takes data sent from socket input stream
            out = new PrintWriter(socket.getOutputStream(), true);
            while(!socket.isClosed()){
                String str = in.readLine();
                if(str!=null){
                    if(str.equals("EXIT")){
                        server.exit();
                    }
                    server.sendMessage(str);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
