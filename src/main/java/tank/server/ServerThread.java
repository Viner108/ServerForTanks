package tank.server;

import tank.connection.ListFullConnection;
import tank.connection.OutputConnection;

import java.net.Socket;

public class ServerThread extends Thread {
    OutputConnection outputConnection;
    Socket socket;


    public ServerThread( OutputConnection outputConnection, Socket socket) {
        this.outputConnection =outputConnection;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true){
            if(ListFullConnection.listFullConnection.size() != 0){
            outputConnection.writeTank(OutputConnection.tanks);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                break;
            }
        }
    }
}
