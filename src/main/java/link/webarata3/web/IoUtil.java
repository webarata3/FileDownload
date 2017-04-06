package link.webarata3.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtil {
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
    private static final int EOF = -1;

    public static void copy(InputStream is, OutputStream os) throws IOException {
        int bytes = 0;
        byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
        while ((bytes = is.read(buff)) != EOF) {
            os.write(buff, 0, bytes);
        }
        os.flush();
    }
}
