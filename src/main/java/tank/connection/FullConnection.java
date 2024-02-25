package tank.connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class FullConnection extends Thread {
    public EventConnection eventClientConnection;
    public TankConnection tankClientConnection;


    public FullConnection(EventConnection eventClientConnection,
                                TankConnection tankClientConnection){
        this.tankClientConnection = tankClientConnection;
        this.eventClientConnection = eventClientConnection;

    }
}
