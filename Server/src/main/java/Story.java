import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Story {
    private LinkedList<String> story = new LinkedList<>();

    public void addStory(String msg) {
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(msg);
        } else {
            story.add(msg);
        }


    }

    public void printStory(BufferedWriter writer) {
        if (story.size() > 0) {
            try {
                writer.write("History messages " + "\r\n");
                for (String value : story) {
                    writer.write(value + "\r\n");
                    writer.write("/...." + "\r\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
