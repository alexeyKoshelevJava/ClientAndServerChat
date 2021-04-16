import com.google.common.base.Joiner;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class MyServerTest extends TestCase {
static final String LN = System.getProperty("line.separator");
    public void testStart2() throws IOException {

        Socket socket = Mockito.mock(Socket.class);


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream("пока"
                .getBytes());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);

        MyServer server = new MyServer(socket);
        server.start();

//        assertThat(out.toString(),is(" Echo пока"));





    }
}