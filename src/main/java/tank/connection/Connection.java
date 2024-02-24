package tank.connection;

import tank.event.KeyEventDto;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {
    private static int PORT = 8001;
    private boolean isConnected = false;
    private ServerSocket serverSocket;
    private Socket input;
    private InputStream is;
    private ObjectInputStream ois;
    TankConnection connection = new TankConnection();

    @Override
    public void run() {
        startConnection();
        while (true) {
            try {
                connection = new TankConnection();
                is = input.getInputStream();
                ois = new ObjectInputStream(is);
                DataInputStream dataInputStream = new DataInputStream(is);
                String string = dataInputStream.readUTF();
                System.out.println(string);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    isConnected = false;
                    Thread.sleep(5000);
                    clientConnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
//        try {
//            while (!isConnected) {
//                closeConnection();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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

    private void closeConnection() throws IOException {
        is.close();
        input.close();
        serverSocket.close();
    }

}
