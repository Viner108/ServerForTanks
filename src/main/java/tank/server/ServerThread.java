package tank.server;

import tank.connection.ClientSender;
import tank.connection.OutputConnection;

public class ServerThread extends Thread {
    OutputConnection outputConnection;
    ClientSender clientSender;


    public ServerThread(ClientSender clientSender, OutputConnection outputConnection) {
        this.outputConnection =outputConnection;
        this.clientSender = clientSender;
    }

    @Override
    public void run() {
        while (true){
            if(OutputConnection.handlers.get(clientSender.getPort())==clientSender){
            clientSender.writeTank(OutputConnection.tankDto);
            }else {
                break;
            }
        }
    }
}
