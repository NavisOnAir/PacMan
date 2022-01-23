package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {


    // keypressed
    public boolean arrowUp = false;
    public boolean arrowRight = false;
    public boolean arrowDown = false;
    public boolean arrowLeft = false;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case 38:
                arrowUp = true;
                break;
            case 39:
                arrowRight = true;
                break;
            case 40:
                arrowDown = true;
                break;
            case 37:
                arrowLeft = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case 38:
                arrowUp = false;
                break;
            case 39:
                arrowRight = false;
                break;
            case 40:
                arrowDown = false;
                break;
            case 37:
                arrowLeft = false;
                break;
            default:
                break;
        }
    }
    
}
