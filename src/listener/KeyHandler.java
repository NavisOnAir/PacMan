package listener;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {


    // keypressed
    public boolean arrowUp = false;
    public boolean arrowRight = false;
    public boolean arrowDown = false;
    public boolean arrowLeft = false;
    public boolean esc = false;
    public boolean d = false;
    public boolean w = false;
    public boolean a = false;
    public boolean s = false;
    public boolean q = false;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    // set the matching boolean to true if this key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case 38 -> arrowUp = true;
            case 39 -> arrowRight = true;
            case 40 -> arrowDown = true;
            case 37 -> arrowLeft = true;
            case 27 -> esc = true;
            case 87 -> w = true;
            case 65 -> a = true;
            case 83 -> s = true;
            case 68 -> d = true;
            case 81 -> q = true;
            default -> System.out.println(code);
        }
    }

    // set the matching boolean to false if key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case 38 -> arrowUp = false;
            case 39 -> arrowRight = false;
            case 40 -> arrowDown = false;
            case 37 -> arrowLeft = false;
            case 27 -> esc = false;
            case 87 -> w = false;
            case 65 -> a = false;
            case 83 -> s = false;
            case 68 -> d = false;
            case 81 -> q = false;
            default -> {
            }
        }
    }
    
}
