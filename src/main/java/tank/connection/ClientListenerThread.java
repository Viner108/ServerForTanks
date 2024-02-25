package tank.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientListenerThread extends Thread{
    private static String HOST = "192.168.1.105";
    private static final int TANK_OUT_PORT = 8001;
    private static final int KEY_EVENT_IN_PORT = 8002;
    private boolean isAlive = true;
    private boolean isConnected = false;
    public List<FullConnection> fullClientConnectionList;
    ServerSocket eventServerSocket;
    ServerSocket tankServerSocket;
    @Override
    public void run () {
        try {
            eventServerSocket = new ServerSocket(KEY_EVENT_IN_PORT);
            tankServerSocket = new ServerSocket(TANK_OUT_PORT);
        } catch (Exception e) {
            System.out.println("ClientListenerThread ServerSocket error");
        }
        while (isAlive) {
            receiveNewConnection();
        }
        try {
            eventServerSocket.close();
            System.out.println("ClientListenerThread close");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void receiveNewConnection(){
        try {
            EventConnection eventClientConnection = getEventClientConnection();
            TankConnection tankClientConnection = getTankClientConnection();
            fullClientConnectionList.add(new FullConnection(
                    eventClientConnection,
                    tankClientConnection));
            eventClientConnection.start();

            System.out.println("ClientListenerThread startServerSocket create new connection");
        } catch (Exception e) {
            System.out.println("ClientListenerThread startServerSocket error");
        }
    }

    private TankConnection getTankClientConnection () throws IOException {
        Socket tankSocket = tankServerSocket.accept();
        ObjectOutputStream objectSender = new ObjectOutputStream(tankSocket.getOutputStream());
        System.out.println("ClientListenerThread objectSender");
        var tConn = new TankConnection(tankSocket, objectSender);
        return tConn;
    }

    private EventConnection getEventClientConnection () throws IOException {
        Socket eventSocket = eventServerSocket.accept();
        System.out.println("ClientListenerThread serverSocket.accept()");

        ObjectInputStream objectReceiver = new ObjectInputStream(eventSocket.getInputStream());
        System.out.println("ClientListenerThread objectReceiver");
        EventConnection eventClientConnection = new EventConnection(eventSocket, objectReceiver);
        return eventClientConnection;
    }

}
