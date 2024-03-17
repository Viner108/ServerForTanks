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

public class OutputConnection implements Runnable {
    public Socket output;
    public static Map<Integer, TankDto> tanks = new HashMap<>();
    public AtomicBoolean isConnection = new AtomicBoolean(true);
    ObjectOutputStream objectOutputStream;

    public OutputConnection(Socket output,ObjectOutputStream objectOutputStream) {
        this.output = output;
        this.objectOutputStream = objectOutputStream;
        isConnection.set(true);
    }

    @Override
    public void run() {
        try {
            while (isConnection.get() && !output.isClosed()) {
                writeTank(tanks);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeOut();
        }
        closeOut();
    }

    public void writeTank(Map<Integer, TankDto> tanks) {
        try {
            if (tanks.size() != 0) {
                for (TankDto tankDto : tanks.values()) {
                    objectOutputStream.writeObject(tankDto);
                    objectOutputStream.reset();
                }
            }else {
                closeOut();
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeOut();
        }
    }

    public void closeOut() {
        System.out.println("ClientOutput disconnect");
        isConnection.set(false);
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
