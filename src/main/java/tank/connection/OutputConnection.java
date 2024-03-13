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
    public OutputConnection(Socket output) {
        this.output = output;
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
            OutputStream outputStream = output.getOutputStream();
            ObjectOutputStream objectOutputStream = new MyObjectOutputStream(outputStream);
            if (tanks.size() != 0) {
                synchronized (objectOutputStream) {
                    objectOutputStream.writeObject(tanks);
                }
            }
        } catch (IOException e) {
            System.out.println("ClientOutput disconnect");
            ListFullConnection.removeFullConnection(this);
            close();
            isConnection=false;
        }
    }

    public void close() {
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
