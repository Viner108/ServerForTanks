package tank.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {
    private static int PORT = 8001;
    private boolean isConnected = false;
    private ServerSocket serverSocket;
    private Socket input;


    @Override
    public void run() {
        startConnection();
        while (true) {
            try {
                clientConnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startConnection() {
        while (!isConnected) {
            try {
                System.out.println("Start");
                serverSocket = new ServerSocket(PORT);
                clientConnect();
                isConnected = true;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clientConnect() throws IOException {
        input = serverSocket.accept();
        System.out.println("Client connect");
    }


}
