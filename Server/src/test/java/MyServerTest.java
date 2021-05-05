
import com.google.common.base.Joiner;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class MyServerTest  {
    private static final String LN = System.getProperty("line.separator");

    @Test
    public void whenAsk() throws IOException {
        Socket socket = Mockito.mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        MyServer server = new MyServer(socket);
        server.start();


        MatcherAssert.assertThat(out.toString(),is (
               (

                        ""

                )));

    }


}