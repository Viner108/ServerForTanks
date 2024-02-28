package tank.server;

import tank.connection.ClientSender;
import tank.connection.OutputConnection;

public class ServerThread extends Thread {
    OutputConnection outputConnection;
    ClientSender clientSender;


    public ServerThread(ClientSender clientSender) {
        this.clientSender = clientSender;
    }

    @Override
    public void run() {
        while (true){
            clientSender.writeTank(OutputConnection.tankDto);
        }
    }
}
