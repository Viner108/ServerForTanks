package tank.connection;

import tank.event.KeyEventDto;
import tank.event.TankDto;
import tank.objectStream.MyObjectInputStream;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class InputConnection implements Runnable {
    public Socket input;

    public TankDto tankDto;

    public InputConnection(Socket input) {
        this.input = input;
        this.tankDto = new TankDto(input.getPort());
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
    }

    @Override
    public void run() {
        try (InputStream inputStream = input.getInputStream();
             ObjectInputStream objectInputStream = new MyObjectInputStream(inputStream)) {
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
        } finally {
            closeInput();
            FullConnection.list.forEach(outputAndInputConnection -> {
                if (outputAndInputConnection.inputConnection == this) {
                    outputAndInputConnection.outputConnection.closeOut();
                }
            });
            OutputConnection.tanks.remove(tankDto.getId(), tankDto);
        }
    }

    public void closeInput() {
        try {
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEventDto e) {
        tankDto.move(e);
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
        FullConnection.list.forEach(outputAndInputConnection -> {
            outputAndInputConnection.outputConnection.writeTank(OutputConnection.tanks);
        });

    }

    public void keyReleased() {

    }


}
