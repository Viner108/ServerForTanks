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
            int i = 0;
            while (true) {
                KeyEventDto keyEventDto = new KeyEventDto();
                keyEventDto.setKeyCode(i);
                objectOutputStream.writeObject(keyEventDto);
                i++;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            try {
                System.out.println("Client disconnect");
                clientSocket.close();
                OutputConnection.removeClientLink(getPort());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public int getPort(){
        return clientSocket.getPort();
    }
}
