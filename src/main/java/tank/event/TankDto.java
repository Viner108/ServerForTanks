package tank.event;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TankDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817115L;
    int id;
    int X = 0;
    int Y = 0;
    private int speed = 5;
    public int alpha = 0;
    int deltaX = 0;
    int deltaY = 0;
    int deltaAlpha = 0;
    int speedAlpha = 1;
    public TankDto(int id) {
        this.id = id;
    }

    public void move() {
        X = X + deltaX;
        Y = Y + deltaY;
        alpha = alpha + deltaAlpha;
    }
    public void keyEventPressed(KeyEventDto e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: {
                deltaAlpha = -speedAlpha;
                break;
            }
            case KeyEvent.VK_D: {
                deltaAlpha = speedAlpha;
                break;
            }
            case KeyEvent.VK_W: {
                deltaX = (int) (Math.cos(Math.toRadians(alpha)) * speed);
                deltaY = (int) (Math.sin(Math.toRadians(alpha)) * speed);
                break;
            }
            case KeyEvent.VK_S: {
                deltaX = (int) (-Math.cos(Math.toRadians(alpha)) * speed);
                deltaY = (int) (- Math.sin(Math.toRadians(alpha)) * speed);
                break;
            }
        }
    }
    public void keyEventReleased(KeyEventDto e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: {
                deltaAlpha = 0;
                break;
            }
            case KeyEvent.VK_D: {
                deltaAlpha = 0;
                break;
            }
            case KeyEvent.VK_W: {
                deltaX = 0;
                deltaY = 0;
                break;
            }
            case KeyEvent.VK_S: {
                deltaX = 0;
                deltaY = 0;
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
}
