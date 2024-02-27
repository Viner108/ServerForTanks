package tank.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class OutputConnection extends Thread implements Connection{
    private static int PORT = 8002;
    private ServerSocket serverSocket;
    private Socket output;
    private static Map<Integer,ClientSender> handlers = new HashMap<>();

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

    public void clientConnect() throws IOException {
        output = serverSocket.accept();
        ClientSender clientSender = new ClientSender(output);
        new Thread(clientSender).start();
        saveClientLink(clientSender);
        System.out.println("Client connect");
    }
    private void saveClientLink(ClientSender clientSender){
        handlers.put(clientSender.getPort(),clientSender);
    }

    public static void removeClientLink(int port){
        handlers.remove(port);
    }
}
