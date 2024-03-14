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
    private Socket output;
    public static Map<Integer, TankDto> tanks = new HashMap<>();

    private boolean isConnection=true;

    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    public OutputConnection(Socket output) {
        this.output = output;
        try {
            outputStream = output.getOutputStream();
            objectOutputStream = new MyObjectOutputStream(outputStream);
        } catch (IOException e) {
            System.out.println("ClientOutput disconnect");
            ListFullConnection.removeFullConnection(this);
            close();
            isConnection=false;
        }
    }

    @Override
    public void run() {
        while (isConnection) {
            if (ListFullConnection.listFullConnection.size() != 0 ) {
                    writeTank(tanks);
            }
        }
    }

    public void writeTank(Map<Integer, TankDto> tanks){
        try {
            if (tanks.size() != 0) {
                synchronized (objectOutputStream) {
                    objectOutputStream.writeObject(tanks);
                }
            }
        } catch (IOException e) {
            System.out.println("ClientOutput disconnect");
        }finally {
            ListFullConnection.removeFullConnection(this);
            close();
            isConnection=false;
        }
    }

    public void close() {
        try {
            objectOutputStream.close();
            outputStream.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
