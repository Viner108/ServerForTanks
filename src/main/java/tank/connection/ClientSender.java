package tank.connection;

import tank.event.KeyEventDto;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSender extends Thread {
    private Socket clientSocket;

    public ClientSender(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            while (true) {

            }
        } catch (Exception e) {
            try {
                System.out.println("Client disconnect");
                clientSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
