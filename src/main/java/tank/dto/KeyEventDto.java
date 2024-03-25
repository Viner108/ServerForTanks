package tank.dto;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyEventDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817116L;
    boolean press=false;
    int keyCode;
    public static KeyEventDto fromKeyEvent(KeyEvent e){
        KeyEventDto dto = new KeyEventDto();
        dto.setKeyCode(e.getKeyCode());
        return dto;
    }

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }

    public int getKeyCode () {
        return keyCode;
    }

    public void setKeyCode (int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public String toString() {
        return "KeyEventDto{" +
               "press=" + press +
               ", keyCode=" + keyCode +
               '}';
    }
}
