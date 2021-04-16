import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyClient {
    protected static Logger logger;
    private final String nickName;

    public MyClient(String nickName) {
        this.nickName = nickName;
    }

    public void start(String IpAddress, int port) throws IOException {
        logger = createLogger();


        new ClientConnection(nickName, IpAddress, port);
    }

    private Logger createLogger() {
        Logger log = Logger.getLogger("myLogger");
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("logFileClient.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        log.addHandler(fileHandler);
        log.setUseParentHandlers(false);
        return log;
    }

}
