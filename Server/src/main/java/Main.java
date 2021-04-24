import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {


        try (BufferedReader reader = new BufferedReader(new FileReader("Server/setting.txt"))) {
            String value = reader.readLine();
            String[] parts = value.split(" ");
            int port = Integer.parseInt(parts[1]);
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            while (true) {
                Socket socket = serverSocket.accept();
                new MyServer(socket).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

