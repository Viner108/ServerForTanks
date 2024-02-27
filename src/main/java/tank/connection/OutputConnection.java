package tank.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OutputConnection extends Thread {
    private static int PORT = 8002;
    private ServerSocket serverSocket;
    private Socket output;

    @Override
    public void run() {
        startConnection();
        while (true) {
            try {
                Thread.sleep(500);
                clientConnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startConnection() {
        try {
            System.out.println("Start");
            serverSocket = new ServerSocket(PORT);
            clientConnect();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
                System.out.println("Try connection");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void clientConnect() throws IOException {
        output = serverSocket.accept();
        ClientSender clientSender = new ClientSender(output);
        new Thread(clientSender).start();
        System.out.println("Client connect");
    }
}
