package tank.connection;

import java.io.IOException;

public interface Connection {
    void run();
    void startConnection();
    void clientConnect() throws IOException;

}
