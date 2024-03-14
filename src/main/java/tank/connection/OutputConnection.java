package tank.connection;

import tank.event.TankDto;
import tank.objectStream.MyObjectOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class OutputConnection extends Thread {
    public Socket output;
    public static Map<Integer, TankDto> tanks = new HashMap<>();

    private boolean isConnection = true;

    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;

    public OutputConnection(Socket output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            outputStream = output.getOutputStream();
            objectOutputStream = new MyObjectOutputStream(outputStream);
        } catch (Exception e) {
            closeOut();
        }
        while (isConnection && !output.isClosed()) {
            if (ListFullConnection.listFullConnection.size() != 0) {
                writeTank(tanks);
            }
        }
        closeOut();
    }

    public void writeTank(Map<Integer, TankDto> tanks) {
        try {
            if (tanks.size() != 0) {
                synchronized (objectOutputStream) {
                    objectOutputStream.writeObject(tanks);
                }
            }
        } catch (Exception e) {
            closeOut();
        }
    }

        public void closeOut() {
            System.out.println("ClientOutput disconnect");
            ListFullConnection.removeFullConnection(this);
            isConnection = false;
            try {
                objectOutputStream.close();
                outputStream.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
