package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

import main.Game;
import main.Ui;
import main.Utils;

public class MouseHandler implements MouseListener {

    Game game;
    Ui ui;
    Utils utils = new Utils();
    FontRenderContext frc = new FontRenderContext(null, true, true);

    public MouseHandler(Game game) {
        this.game = game;
        this.ui = this.game.ui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // title screen start button listener
        if (game.gameState == game.titelState) {
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Start", frc), ui.startButtonX, ui.startButtonY)) {
                game.gameState = game.ingameState;
            }
        }
          
    }  
    @Override
    public void mouseEntered(MouseEvent e) {  
          
    }
    @Override
    public void mouseExited(MouseEvent e) {  
          
    }
    @Override
    public void mousePressed(MouseEvent e) {  
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {  

    }
    
}
