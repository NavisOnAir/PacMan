package graphics.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.font.FontRenderContext;

public class GButton implements MouseListener {

    // position
    int x, y, width, height;
    float size;

    // content
    String str;

    // instances
    Ui ui;
    FontRenderContext frc = new FontRenderContext(null, true, true);

    public GButton(String str, int x, int y, Ui ui, float size) {
        this.str = str;
        this.width = (int) Math.round(ui.defaultFont.deriveFont(size).getStringBounds(str, frc).getWidth());
        this.height = (int) Math.round(ui.defaultFont.deriveFont(size).getStringBounds(str, frc).getHeight() / 2);
        this.x = x;
        this.y = y;
        this.ui = ui;
        this.size = size;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(ui.defaultFont.deriveFont(size));
        g2.drawString(str, x, y);
    }

    public void drawDebug(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.drawRect(x, y - height, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (mouseX > this.x && mouseX < this.x + width && mouseY > this.y - height && mouseY < this.y) {
            buttonPressed();
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void buttonPressed() {
        System.out.println("Pressed");
    }
    
}
