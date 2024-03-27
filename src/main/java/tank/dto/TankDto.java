package tank.dto;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TankDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817115L;
    int id;
    float X = 1;
    float Y = 1;
    public static float TANK_HEIGHT = 109F;
    public static float TANK_WIDTH = 82F;
    private float speed = 5;
    public float alpha = 0;
    float deltaX = 0;
    float deltaY = 0;
    float deltaAlpha = 0;
    float speedAlpha = 3;
    public boolean isFocusable= false;
    public ToreDto tore;
    public TankDto(int id) {
        this.id = id;
        this.tore=new ToreDto(Y,X);
    }

    public void move() {
        X = X + deltaX;
        Y = Y + deltaY;
        alpha = alpha + deltaAlpha;
        tore.move((X + TANK_HEIGHT / 2.4F), (Y + TANK_WIDTH / 3));
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
                deltaX = (float) (Math.cos(Math.toRadians(alpha)) * speed);
                deltaY = (float) (Math.sin(Math.toRadians(alpha)) * speed);
                break;
            }
            case KeyEvent.VK_S: {
                deltaX = (float) (-Math.cos(Math.toRadians(alpha)) * speed);
                deltaY = (float) (- Math.sin(Math.toRadians(alpha)) * speed);
                break;
            }
            case KeyEvent.VK_Q: {
                tore.turnContrClockArrowDirection();
                break;
            }
            case KeyEvent.VK_E: {
                tore.turnByClockArrowDirection();
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
            case KeyEvent.VK_Q: {
                tore.zeroSpeedAlpha();
                break;
            }
            case KeyEvent.VK_E: {
                tore.zeroSpeedAlpha();
                break;
            }
        }
    }
    public void mouseEventClicked (MouseEventDto e) {
        if ((e.getPoint().getX() <= X + TANK_HEIGHT)
            && (e.getPoint().getX() >= X)
            && (e.getPoint().getY() <= Y + TANK_WIDTH)
            && (e.getPoint().getY() >= Y)
        ) {
            setFocusable(true);
        } else {
            setFocusable(false);
        }
    }


    @Override
    public String toString() {
        return "TankDto{" +
               "X=" + X +
               ", Y=" + Y +
               '}';
    }

    public boolean isFocusable() {
        return isFocusable;
    }

    public void setFocusable(boolean focusable) {
        isFocusable = focusable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }
}
