package tank.connection;

import tank.objectStream.MyObjectInputStream;
import tank.event.KeyEventDto;
import tank.event.TankDto;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    public TankDto tankDto = new TankDto();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new MyObjectInputStream(inputStream);
            while (true) {
                try {
                    Object object = objectInputStream.readObject();
                    KeyEventDto keyEventDto = (KeyEventDto) object;
                    if (keyEventDto.getKeyCode()!=0) {
                        if (keyEventDto.isPress()) {
                            keyPressed();
                        } else {
                            keyReleased();
                        }
                        System.out.println(keyEventDto.toString());
                        System.out.println(tankDto.toString());
                    }
                }catch (StreamCorruptedException e){
                    e.printStackTrace();
                }
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

    public void keyPressed() {
        tankDto.move();
        OutputConnection.tankDto = tankDto;
    }

    public void keyReleased() {

    }

    public int getPort() {
        return clientSocket.getPort();
    }
}
