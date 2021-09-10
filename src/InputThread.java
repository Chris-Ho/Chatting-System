import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InputThread implements Runnable {

    private Socket socket;
    private BufferedReader userInput;
    private PrintWriter out;

    public InputThread(Socket socket) {
        this.socket = socket;
        try {
            userInput = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        try {
            socket.close();
            userInput.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       while(!socket.isClosed()){
           String str;
           try {
               str = userInput.readLine();
               out.println(str);
           }catch (IOException e) {
               e.printStackTrace();
           }
       }
       exit();
    }
}
