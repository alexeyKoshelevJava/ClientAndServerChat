import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             BufferedReader reader = Files.newBufferedReader(Paths.get("Client/setting.txt"))) {
            String line1 = reader.readLine();
            String[] parts1 = line1.split(" ");
            String IpAddress = parts1[1];
            String line2 = reader.readLine();
            String[] parts2 = line2.split(" ");
            int port = Integer.parseInt(parts2[1]);
            System.out.println("" +
                    "Enter the NickName");
            String nickname = scanner.nextLine();
            MyClient myClient = new MyClient(nickname);
            myClient.start(IpAddress, port);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
