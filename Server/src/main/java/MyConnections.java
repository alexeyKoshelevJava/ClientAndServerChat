import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyConnections extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private Thread rxThread;


    public MyConnections(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        rxThread = new Thread(() -> {

            String request;
            String nickName = "Unknown";
            System.out.println(nickName + " Client " + MyConnections.this + " connected");
            MyServer.logger.info(nickName + " Client " + MyConnections.this + " connected");
            try {
                while (true) {
                    if ((request = in.readLine()) != null) {
                        String[] headerAndNick = request.split(" ");
                        if ("Hello".equals(headerAndNick[0])) {
                            nickName = headerAndNick[1];
                            MyServer.list.add(MyConnections.this);
                            MyServer.story.printStory(out);
                            out.write(request + "\r\n");
                            out.flush();
                            sendToAllConnections(nickName + " connected" + "\r\n");
                            MyServer.logger.info(nickName + " connected");

                            break;

                        } else {
                            out.write("I don't understand" + "\r\n");
                            out.flush();
                        }
                    } else {
                        out.write("I don't understand" + "\r\n");
                        out.flush();
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection " + MyConnections.this + " exeption: " + e);
                System.out.println("Connection " + MyConnections.this + "disconnected");
                MyConnections.this.disconnect();
            }
            try {

                while (!rxThread.isInterrupted()) {
                    request = in.readLine();


                    if ("end".equals(request)) {
                        MyServer.list.remove(MyConnections.this);
                        sendToAllConnections(nickName + " disconnected" + "\r\n");
                        MyServer.logger.info(nickName + " disconnected");
                        out.write("end" + "\r\n");
                        out.flush();
                        disconnect();

                        break;
                    }
                    if (request == null) break;
                    sendToAllConnections(nickName + ": " + request);
                    MyServer.story.addStory(request);
                    MyServer.logger.info(nickName + ": " + request);

                }

            } catch (IOException e) {
                System.out.println("Connection " + MyConnections.this + " exeption: " + e);

            } finally {
                MyServer.list.remove(MyConnections.this);
                sendToAllConnections("Client " + nickName + " disconnected");
                MyServer.logger.info("Client " + nickName + " disconnected");
            }
        });
        rxThread.start();
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < MyServer.list.size(); i++) {
            MyServer.list.get(i).sendString(value);
        }

    }

    private synchronized void sendString(String msg) {
        try {
            out.write(msg + "\r\n");
            out.flush();
        } catch (IOException e) {
            System.out.println("Connection " + MyConnections.this + " exeption: " + e);
            disconnect();
        }

    }

    private synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
            in.close();
            out.close();

        } catch (IOException e) {
            System.out.println("Connection " + MyConnections.this + " exeption: " + e);
        }

    }


    @Override
    public String toString() {
        return "MyConnections" +
                socket.getInetAddress() + socket.getPort() + '}';
    }

}


