package tank;

import tank.connection.FullConnection;

import java.io.*;

public class TankApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FullConnection fullConnection = new FullConnection();
        fullConnection.start();


    }
}
