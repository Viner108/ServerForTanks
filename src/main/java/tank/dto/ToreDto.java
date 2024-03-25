package tank.dto;

import javax.swing.*;
import java.io.Serializable;

public class ToreDto implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;
    public float Y;
    public float X;
    public float alpha = 0.0F;
    public float deltaAlpha = 0.0F;
    public float speedAlpha = 1.5F;

    public ToreDto(float y, float x) {
        Y = y;
        X = x;
    }
    public void move (float baseX, float baseY) {
        X = baseX;
        Y = baseY;
        alpha = alpha + deltaAlpha;
    }

    public void turnContrClockArrowDirection() {
        deltaAlpha = -speedAlpha;
    }

    public void turnByClockArrowDirection() {
        deltaAlpha = speedAlpha;
    }

    public void zeroSpeedAlpha() {
        deltaAlpha = 0;
    }
}
