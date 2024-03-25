package tank.connection;

import tank.dto.KeyEventDto;
import tank.dto.TankDto;
import tank.objectStream.MyObjectInputStream;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.Iterator;

public class InputConnection implements Runnable {
    public Socket input;

    public TankDto tankDto;

    public InputConnection(Socket input) {
        this.input = input;
        this.tankDto = new TankDto(input.getPort());

    }

    @Override
    public void run() {
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
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
                            keyReleased(keyEventDto);
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
            FullConnection.list.forEach(outputAndInputConnection1 -> {
                if (outputAndInputConnection1.inputConnection == this) {
                    outputAndInputConnection1.outputConnection.closeOut();
                }
            });
            for (Iterator<OutputAndInputConnection> iterator = FullConnection.list.iterator(); iterator.hasNext(); ) {
                OutputAndInputConnection outputAndInputConnection = iterator.next();
                if (outputAndInputConnection.inputConnection.equals(this)) {
                    iterator.remove();
                }
            }
            OutputConnection.tanks.remove(tankDto.getId());
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
        tankDto.keyEventPressed(e);
        tankDto.move();
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
        FullConnection.list.forEach(outputAndInputConnection -> {
            if (!outputAndInputConnection.outputConnection.output.isClosed()) {
                outputAndInputConnection.outputConnection.writeTank(OutputConnection.tanks);
            }
        });

    }

    public void keyReleased(KeyEventDto e) {
        tankDto.keyEventReleased(e);
        OutputConnection.tanks.put(tankDto.getId(), tankDto);
        FullConnection.list.forEach(outputAndInputConnection -> {
            if (!outputAndInputConnection.outputConnection.output.isClosed()) {
                outputAndInputConnection.outputConnection.writeTank(OutputConnection.tanks);
            }
        });
    }


}
