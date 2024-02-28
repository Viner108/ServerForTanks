package tank.connection;

import tank.event.TankDto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class InputConnection extends Thread implements Connection{
    private static int PORT = 8001;
    private ServerSocket serverSocket;
    private Socket input;
    public static Map<Integer,ClientHandler> handlers = new HashMap<>();
    public TankDto tankDto;

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
        input = serverSocket.accept();
        ClientHandler clientHandler = new ClientHandler(input);
        new Thread(clientHandler).start();
        saveClientLink(clientHandler);
        System.out.println("Client connect");
    }

    private void saveClientLink(ClientHandler clientHandler){
        handlers.put(clientHandler.getPort(),clientHandler);
    }

    public static void removeClientLink(int port){
        handlers.remove(port);
    }

}
