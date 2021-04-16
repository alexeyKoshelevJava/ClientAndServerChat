import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("" +
                "Enter the IP and port to run App separated by a space from the fail setting.txt");
        String input = scanner.nextLine();
        String[] value = input.split(" ");
        String IpAddress = value[0];
        int port = Integer.parseInt(value[1]);
        System.out.println("" +
                "Enter the NickName");

        String nickname = scanner.nextLine();

        MyClient myClient = new MyClient(nickname);

        myClient.start(IpAddress, port);

    }
}
