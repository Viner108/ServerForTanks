package tank.connection;

import tank.event.KeyEventDto;
import tank.event.TankDto;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class TankConnection {
    ObjectOutputStream objectOutputStreamSender;
    public boolean isAlive = true;
    Socket tankSocket;

    public TankConnection (Socket tankSocket, ObjectOutputStream objectOutputStreamSender){
        System.out.println("EventClientConnection");
        this.objectOutputStreamSender = objectOutputStreamSender;
    }
}
