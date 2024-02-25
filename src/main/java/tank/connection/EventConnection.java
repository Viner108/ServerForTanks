package tank.connection;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class EventConnection extends Thread{
    ObjectInputStream receiver;
    private boolean isAlive = true;
    Socket eventSocket;
    public EventConnection (Socket eventSocket, ObjectInputStream receiver) {
        System.out.println("EventClientConnection");
        this.eventSocket = eventSocket;
        this.receiver = receiver;
    }
    @Override
    public void run () {
        listening();
    }

    private void listening () {
        try {
            eventListeningCycle();
        } catch (Exception ioException) {
            System.out.println("EventClientConnection listening error");
        } finally {
            try {
                eventSocket.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection eventSocket close error1");
            }

            try {
                receiver.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection receiver close error");
            }

            try {
                eventSocket.close();
            } catch (IOException e) {
                System.out.println("EventClientConnection eventSocket close error2");
            }
        }
    }

    private void eventListeningCycle() {

    }
}
