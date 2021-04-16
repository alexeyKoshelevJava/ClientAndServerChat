import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВеди номер порта из файла setting.txt");
        String input = scanner.nextLine();
        int port = Integer.parseInt(input);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started");
        while (true) {

            Socket socket = serverSocket.accept();


            new MyServer(socket).start();
        }

    }

}

