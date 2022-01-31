package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

import main.Game;
import main.Utils;
import ui.Ui;

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
        // title screen
        if (game.gameState == game.titelState) {

            // start button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Start", frc), ui.startButtonX, ui.startButtonY)) {
                game.gameState = game.ingameState;
            }

            // select level button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Select Level", frc), ui.levelSelectButtonX, ui.levelSelectButtonY)) {
                game.gameState = game.levelSelectState;
            }
        }

        // ingame screen
        if (game.gameState == game.ingameState) {
            
            // stop button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(30f).getStringBounds("Stop", frc), ui.stopButtonX, ui.stopButtonY)) {
                game.gameState = game.pauseState;
                game.timer.pause();
            }
        }

        // pause screen
        if (game.gameState == game.pauseState) {

            // resume/start button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(50f).getStringBounds("Start", frc), ui.startButtonX, ui.startButtonY)) {
                game.gameState = game.ingameState;
                game.timer.start();
            }
        }

        // level selection
        if (game.gameState == game.levelSelectState) {
            // every level select button
            for (int i = 0; i < 10 ; i++) {
                if (ui.levelStrings[i] == null) {
                    return;
                }
                if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(30f).getStringBounds(ui.levelStrings[i], frc),
                 ui.levelButtonX[i],
                  ui.levelButtonY[i])) {
                    game.levelSelected = ui.levelStrings[i];
                    game.setupGame();
                }
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
