package tank.connection;

import tank.event.KeyEventDto;
import tank.event.TankDto;
import tank.objectStream.MyObjectInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class InputConnection extends Thread {
    private Socket input;

    public TankDto tankDto;

    public InputConnection(Socket input) {
        this.input = input;
        this.tankDto = new TankDto(input.getPort());
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = input.getInputStream();
            ObjectInputStream objectInputStream = new MyObjectInputStream(inputStream);
            while (true) {
                try {
                    Object object = objectInputStream.readObject();
                    KeyEventDto keyEventDto = (KeyEventDto) object;
                    if (keyEventDto.getKeyCode() != 0) {
                        if (keyEventDto.isPress()) {
                            keyPressed(keyEventDto);
                        } else {
                            keyReleased();
                        }
                        System.out.println(keyEventDto.toString());
                        System.out.println(tankDto.toString());
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("ClientInput disconnect");
            close();
            ListFullConnection.removeFullConnection(this);
            OutputConnection.tanks.remove(tankDto.getId(),tankDto);
        }
    }

    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEventDto e) {
        tankDto.move(e);
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
    }

    public void keyReleased() {

    }


}
