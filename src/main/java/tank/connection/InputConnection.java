package tank.connection;

import tank.event.KeyEventDto;
import tank.event.TankDto;
import tank.objectStream.MyObjectInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class InputConnection extends Thread {
    public Socket input;

    public TankDto tankDto;
    InputStream inputStream;
    ObjectInputStream objectInputStream;

    public InputConnection(Socket input) {
        this.input = input;
        this.tankDto = new TankDto(input.getPort());
        try {
            inputStream = input.getInputStream();
            objectInputStream = new MyObjectInputStream(inputStream);
        } catch (IOException e) {
            System.out.println("ClientInput disconnect");
            closeInput();
            ListFullConnection.removeFullConnection(this);
            OutputConnection.tanks.remove(tankDto.getId(),tankDto);
        }
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
        }finally {
            closeInput();
            ListFullConnection.removeFullConnection(this);
            OutputConnection.tanks.remove(tankDto.getId(),tankDto);
        }
    }

    public void closeInput() {
        try {
            objectInputStream.close();
            inputStream.close();
            input.close();
        } catch (Exception e) {
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
