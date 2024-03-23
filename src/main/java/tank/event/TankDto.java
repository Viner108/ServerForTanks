package tank.event;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TankDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817115L;
    int id;
    int X = 0;
    int Y = 0;
    private int speed = 15;

    public TankDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void move(KeyEventDto e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: {
                X = X + speed;
                break;
            }
            case KeyEvent.VK_S: {
                X = X - speed;
                break;
            }
            case KeyEvent.VK_A: {
                Y = Y - speed;
                break;
            }
            case KeyEvent.VK_D: {
                Y = Y + speed;
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
