package tank.connection;

import tank.event.KeyEventDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread{
    private static int PORT = 8081;
    private boolean isConnected = false;
    private ServerSocket serverSocket;
    private Socket input;
    private InputStream is;
    private ObjectInputStream ois;

    @Override
    public void run() {
        try {
            startConnection();
            closeConnection();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void startConnection() throws IOException, ClassNotFoundException {
        while (!isConnected) {
            System.out.println("Start");
            serverSocket = new ServerSocket(PORT);
            input = serverSocket.accept();
            is = input.getInputStream();
            ois = new ObjectInputStream(is);
//        KeyEventDto dto = (KeyEventDto) ois.readObject();
//        System.out.println(dto.toString());
        }
    }
    public void closeConnection() throws IOException {
        is.close();
        input.close();
        serverSocket.close();
    }
}
