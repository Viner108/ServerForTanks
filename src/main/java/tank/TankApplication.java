package tank;

import tank.connection.FullConnection;
import tank.connection.InputConnection;
import tank.connection.ListFullConnection;
import tank.connection.OutputConnection;
import tank.event.TankDto;

import java.io.*;

public class TankApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ListFullConnection listFullConnection = new ListFullConnection();
        listFullConnection.start();


    }
}
