package tank.dto;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class KeyEventDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817116L;
    boolean press=false;
    int keyCode;
    public int x;
    public int y;


    public boolean isPress() {
        return press;
    }

    public int getKeyCode () {
        return keyCode;
    }
    public Point getPoint() {
        return new Point(x, y);
    }
    public MouseEventDto fromMouseEvent() {
        MouseEventDto dto = new MouseEventDto();
        dto.x = this.x;
        dto.y =this.y;
        return dto;
    }

    @Override
    public String toString() {
        return "KeyEventDto{" +
               "press=" + press +
               ", keyCode=" + keyCode +
               ", x=" + x +
               ", y=" + y +
               '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
