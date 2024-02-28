package tank.objectStream;

import java.io.*;

public class MyObjectInputStream extends ObjectInputStream {
    public MyObjectInputStream(InputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void readStreamHeader() throws IOException, StreamCorruptedException {

    }

}
