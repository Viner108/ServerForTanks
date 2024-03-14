package tank.connection;

import tank.event.TankDto;
import tank.objectStream.MyObjectOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class OutputConnection extends Thread {
    public Socket output;
    public static Map<Integer, TankDto> tanks = new HashMap<>();
    private AtomicBoolean isConnection = new AtomicBoolean(true);
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;

    public OutputConnection(Socket output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            while (isConnection.get() && !output.isClosed()) {
                if (ListFullConnection.listFullConnection.size() != 0) {
                    writeTank(tanks);
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            closeOut();
        }
        closeOut();
    }

    public void writeTank(Map<Integer, TankDto> tanks) {
        try {
            outputStream = output.getOutputStream();
            objectOutputStream = new MyObjectOutputStream(outputStream);
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
            isConnection.set(false);
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
