package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

import graphics.ui.Ui;
import main.Game;
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

    // checks on every mouse clicked event if mouse coordinates are matching the coordinates of a down listet string
    // before comparing coordinates checks the current game state
    @Override
    public void mouseClicked(MouseEvent e) {
        // title screen
        if (game.getGameState() == game.getTitleState()) {

            // resume button
            if (game.getStartGame()) {
                if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Resume", frc), ui.resumeButtonX, ui.resumeButtonY)) {
                    game.resumeGame();
                }
            }

            // start button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Start", frc), ui.startButtonX, ui.startButtonY)) {
                game.startGame();
            }

            // select level button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(40f).getStringBounds("Select Level", frc), ui.levelSelectButtonX, ui.levelSelectButtonY)) {
                game.switchLevelSelect();
            }
        }

        // ingame screen
        if (game.getGameState() == game.getIngameState()) {
            
            // stop button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(30f).getStringBounds("Stop", frc), ui.stopButtonX, ui.stopButtonY)) {
                game.switchPause();
            }
        }

        // pause screen
        if (game.getGameState() == game.getPauseState()) {

            // resume/start button
            if (utils.checkRectangle(e.getX(), e.getY(), ui.defaultFont.deriveFont(50f).getStringBounds("Start", frc), ui.startButtonX, ui.startButtonY)) {
                game.resumeGame();
            }
        }

        // level selection
        if (game.getGameState() == game.getLevelSelectState()) {
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
