package tank.connection;

public class TankConnection extends Thread{
    String string="run";
    @Override
    public void run() {
        while (true){
            System.out.println("run");
        }
    }
}
