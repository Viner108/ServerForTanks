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
            if(OutputConnection.handlers.get(clientSender.getPort())!=null){
            clientSender.writeTank(OutputConnection.tankDto);
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
