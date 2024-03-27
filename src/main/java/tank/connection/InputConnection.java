package tank.connection;

import tank.dto.KeyEventDto;
import tank.dto.MouseEventDto;
import tank.dto.TankDto;
import tank.logic.LogicCreateAndUse;
import tank.objectStream.MyObjectInputStream;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class InputConnection implements Runnable {
    public Socket input;
    public HashMap<Integer, TankDto> map = new HashMap<>();
    public LogicCreateAndUse logic;

    public InputConnection(Socket input) {
        this.input = input;
        this.logic = new LogicCreateAndUse(input, map);
    }

    @Override
    public void run() {
        logic.putNewTank();
        map = logic.putNewTank();
        try (InputStream inputStream = input.getInputStream();
             ObjectInputStream objectInputStream = new MyObjectInputStream(inputStream)) {
            while (true) {
                try {
                    Object object = objectInputStream.readObject();
                    if (object instanceof KeyEventDto) {
                        KeyEventDto keyEventDto = (KeyEventDto) object;
                        if (keyEventDto.getKeyCode() != 0) {
                            if (keyEventDto.isPress()) {
                                keyPressed(keyEventDto);
                            } else {
                                keyReleased(keyEventDto);
                            }
                        }
                        if(keyEventDto.getX()!=0 || keyEventDto.getY()!=0){
                            mouseClicked(keyEventDto);
                            System.out.println("MouseEvent");
                        }
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("ClientInput disconnect");
        } finally {
            closeInput();
            deleteConnection();
            OutputConnection.mapForMap.remove(input.getPort());
        }
    }

    private void deleteConnection() {
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
    }


    public void closeInput() {
        try {
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEventDto e) {
        for (TankDto dto : map.values()) {
            if (dto.isFocusable()) {
                dto.keyEventPressed(e);
                dto.move();
                setMap(logic.putInMap(dto));
            }
        }
        sendTank();

    }

    public void keyReleased(KeyEventDto e) {
        for (TankDto dto : map.values()) {
            if (dto.isFocusable()) {
                dto.keyEventReleased(e);
                setMap(logic.putInMap(dto));
            }
        }
        sendTank();
    }

    public void mouseClicked(KeyEventDto e) {
        for (TankDto dto : map.values()) {
            MouseEventDto mouseEventDto =e.fromMouseEvent();
            dto.mouseEventClicked(mouseEventDto);
            setMap(logic.putInMap(dto));
        }
        sendTank();
    }

    private void sendTank() {
        FullConnection.list.forEach(outputAndInputConnection -> {
            if (!outputAndInputConnection.outputConnection.output.isClosed()) {
                outputAndInputConnection.outputConnection.writeTank(OutputConnection.mapForMap);
            }
        });
    }

    public HashMap<Integer, TankDto> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, TankDto> map) {
        this.map = map;
    }
}
