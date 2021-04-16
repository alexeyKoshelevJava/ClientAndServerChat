import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyServer {
    protected static List<MyConnections> list = new ArrayList<>();
    protected static Story story = new Story();
    protected static Logger logger;
    private final Socket socket;

    public MyServer(Socket socket) {
        logger = createLogger();
        this.socket = socket;
    }

    public void start() {

        try {
            new MyConnections(this.socket);
        } catch (IOException e) {
            System.out.println("Server " + MyServer.this + " exeption " + e);
        }

    }


    private Logger createLogger() {
        Logger log = Logger.getLogger("myLogger");
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logFileServer.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        Objects.requireNonNull(fileHandler).setFormatter(simpleFormatter);
        log.addHandler(fileHandler);
        log.setUseParentHandlers(false);
        return log;
    }
}