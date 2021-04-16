import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientConnection extends Thread {
    private Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String nickName;
    private final Thread rxThread;


    public ClientConnection(String nickName, String ip, int port) throws IOException {
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        this.nickName = nickName;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        rxThread = new Thread(() -> {

            String line;
            try {
                line = in.readLine();
                System.out.println(line);
                MyClient.logger.info(line);
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
                        System.out.println(line);
                        MyClient.logger.info(line);
                    }
                }

            } catch (IOException e) {
                System.out.println("Connection " + ClientConnection.this + " exeption: " + e);
                MyClient.logger.info("Connection " + ClientConnection.this + " exeption: " + e);
            } finally {
                System.out.println("Connection disconnected");
                MyClient.logger.info("Connection " + ClientConnection.this + " disconnected");
            }
        });
        rxThread.start();
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
        rxThread.interrupt();
        try {
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Connection " + ClientConnection.this + " exeption: " + e);
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
                System.out.println("Connection " + ClientConnection.this + " exeption: " + e);
            }
        }

    }
}