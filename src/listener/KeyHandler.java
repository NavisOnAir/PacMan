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
            case 38: arrowUp = true; break;
            case 39: arrowRight = true; break;
            case 40: arrowDown = true; break;
            case 37: arrowLeft = true; break;
            case 27: esc = true; break;
            case 87: w = true; break;
            case 65: a = true; break;
            case 83: s = true; break;
            case 68: d = true; break;
            case 81: q = true; break;
            default: System.out.println(code);
        }
    }

    // set the matching boolean to false if key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case 38: arrowUp = false; break;
            case 39: arrowRight = false; break;
            case 40: arrowDown = false; break;
            case 37: arrowLeft = false; break;
            case 27: esc = false; break;
            case 87: w = false; break;
            case 65: a = false; break;
            case 83: s = false; break;
            case 68: d = false; break;
            case 81: q = false; break;
            default: {
            }
        }
    }
    
}
