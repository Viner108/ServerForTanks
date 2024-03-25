package tank.connection;

import tank.dto.TankDto;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class OutputConnection{
    public Socket output;
    public static Map<Integer, HashMap<Integer,TankDto>> mapForMap = new HashMap<>();
    public AtomicBoolean isConnection = new AtomicBoolean(true);
    ObjectOutputStream objectOutputStream;

    public OutputConnection(Socket output,ObjectOutputStream objectOutputStream) {
        this.output = output;
        this.objectOutputStream = objectOutputStream;
        isConnection.set(true);
    }

    public void writeTank(Map<Integer, HashMap<Integer,TankDto>> tanks) {
        try {
            synchronized (tanks) {
                if (tanks.size() != 0) {
                    for (HashMap<Integer, TankDto> tankDto : tanks.values()) {
                        objectOutputStream.writeObject(tankDto);
                        objectOutputStream.reset();
                    }
                } else {
                    closeOut();
                }
            }
        } catch (Exception e) {
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
