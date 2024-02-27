package tank.event;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyEventDto implements Serializable {
    private static final long serialVersionUID = 8038539938717817116L;
    int keyCode;
    public static KeyEventDto fromKeyEvent(KeyEvent e){
        KeyEventDto dto = new KeyEventDto();
        dto.setKeyCode(e.getKeyCode());
        return dto;
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
               "keyCode=" + keyCode +
               '}';
    }
}
