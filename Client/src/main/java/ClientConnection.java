import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientConnection {
    private Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String nickName;
    private final Thread listener;


    public ClientConnection(String nickName, String ip, int port) throws IOException {
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
           logAndPrint("Socket failed");
        }
        this.nickName = nickName;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        listener = new Thread(() -> {

            String line;
            try {
                line = in.readLine();
                logAndPrint(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                while (true) {

                    if ((line = in.readLine()) != null) {
                        if ("end".equals(line)) {
                            disconnect();
                            break;
                        }
                        logAndPrint(line);
                    }
                }

            } catch (IOException e) {
                logAndPrint("Connection " + ClientConnection.this + " exeption: " + e);
            } finally {

                logAndPrint("Connection " + ClientConnection.this + " disconnected");
                ClientConnection.this.disconnect();
            }
        });
        listener.start();
        writeMsg();
    }


    private void headerSend() {
        try {
            out.write("Hello" + " " + nickName + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void disconnect() {
        listener.interrupt();
        try {
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {

            logAndPrint("Connection " + ClientConnection.this + " exeption: " + e);
        }

    }

    private void writeMsg() {
        headerSend();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("введи сообщение: ");

            try {
                String line = scanner.nextLine();

                if ("end".equals(line)) {
                    out.write("end" + "\r\n");
                    out.flush();
                    break;
                } else {
                    out.write(line + "\r\n");
                    out.flush();
                    MyClient.logger.info(line);
                }


            } catch (IOException e) {
                logAndPrint("Connection " + ClientConnection.this + " exeption: " + e);
            }
        }

    }

    private void logAndPrint(String line) {
        MyClient.logger.info(line);
        System.out.println(line);

    }
}