package tank.event;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TankDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817115L;
    int X=0;
    int Y=0;
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public void move(KeyEventDto e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: {
                X=X+10;
                break;
            }
            case KeyEvent.VK_S: {
                X=X-10;
                break;
            }case KeyEvent.VK_A: {
                Y=Y-10;
                break;
            }
            case KeyEvent.VK_D: {
                Y=Y+10;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "TankDto{" +
               "X=" + X +
               ", Y=" + Y +
               '}';
    }
}
