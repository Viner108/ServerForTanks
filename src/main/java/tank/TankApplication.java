package tank;

import tank.connection.Connection;

import java.io.*;

public class TankApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Connection connection = new Connection();
        connection.start();


    }
}
