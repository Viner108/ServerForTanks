package tank.connection;

import tank.event.TankDto;
import tank.server.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class OutputConnection extends Thread implements Connection{
    private static int PORT = 8002;
    private ServerSocket serverSocket;
    private Socket output;
    public static Map<Integer,ClientSender> handlers = new HashMap<>();
    public static TankDto tankDto;


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
            System.out.println("Start OutputConnection");
            serverSocket = new ServerSocket(PORT);
            clientConnect();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
                System.out.println("Try connection by OutputConnection");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void clientConnect() throws IOException {
        output = serverSocket.accept();
        ClientSender clientSender = new ClientSender(output);
        saveClientLink(clientSender);
        ServerThread serverThread = new ServerThread(clientSender,this);
        serverThread.start();
        System.out.println("Client connect by OutputConnection");
    }
    private void saveClientLink(ClientSender clientSender){
        handlers.put(clientSender.getPort(),clientSender);
    }

    public static void removeClientLink(int port){
        handlers.remove(port);
    }



}
