package tank.connection;

import tank.event.ChangeTank;
import tank.objectStream.MyObjectOutputStream;
import tank.event.TankDto;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSender {
    private Socket clientSocket;
    private ChangeTank changeTank=new ChangeTank();
    public ClientSender(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void writeTank(TankDto tankDto) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new MyObjectOutputStream(outputStream);
            if (tankDto != null&& tankDto != this.changeTank.tankDto) {
                synchronized (objectOutputStream) {
                    objectOutputStream.writeObject(tankDto);
                }
                changeTank.tankDto=OutputConnection.tankDto;
            }
        } catch (IOException e) {
            try {
                System.out.println("ClientOutput disconnect");
                clientSocket.close();
                OutputConnection.removeClientLink(getPort());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getPort() {
        return clientSocket.getPort();
    }
}
