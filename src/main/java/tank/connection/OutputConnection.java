package tank.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutputConnection extends Thread {
    private static String HOST = "192.168.1.105";
    private static int PORT = 8002;
    private Socket socketOut;
    private OutputStream outputStream;

    @Override
    public void run() {
        while (true) {
            if (socketOut == null || !isConnected()) {
                startConnection();
            }
        }
    }

    public void startConnection() {
        try {
            System.out.println("Start");
            socketOut = new Socket(HOST, PORT);
            outputStream = socketOut.getOutputStream();
            System.out.println("OutputConnection establishment");
            isConnected();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
                System.out.println("Try connection");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        try {
            outputStream.write(1);
        } catch (IOException e) {
            System.out.println("It isn't connection");
            return false;
        }
        return true;
    }
}
