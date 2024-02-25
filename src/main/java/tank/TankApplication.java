package tank;

import tank.connection.ClientListenerThread;
import tank.connection.FullConnection;

import java.io.*;

public class TankApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientListenerThread connection = new ClientListenerThread();
        connection.start();


    }
}
