package tank;

import tank.connection.ClientListenerThread;
import tank.connection.FullConnection;
import tank.connection.InputConnection;
import tank.connection.OutputConnection;

import java.io.*;

public class TankApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InputConnection inputConnection = new InputConnection();
        OutputConnection outputConnection =new OutputConnection();
        inputConnection.start();
        outputConnection.start();


    }
}
