package tank.dto;

import java.awt.*;
import java.io.Serializable;

public class MouseEventDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817111L;
    public int x;
    public int y;

    public Point getPoint() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "MouseEventDto{" +
               "x=" + x +
               ", y=" + y +
               '}';
    }
}
