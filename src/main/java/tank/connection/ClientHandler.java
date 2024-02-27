package tank.connection;

import tank.event.KeyEventDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) {
                KeyEventDto keyEventDto = (KeyEventDto) objectInputStream.readObject();
                System.out.println(keyEventDto.toString());
            }
        } catch (Exception e) {
            try {
                System.out.println("Client disconnect");
                clientSocket.close();
                InputConnection.removeClientLink(getPort());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public int getPort(){
        return clientSocket.getPort();
    }
}
