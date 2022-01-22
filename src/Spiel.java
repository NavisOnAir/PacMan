import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Spiel extends JFrame implements KeyListener {
	private int width = 30;
	private int hight = 20;
	int[] startCords = {1, 10};
	private int[][] spielfeld = new int[hight][width]; //0 = nothing, 1 = wall, 2 = point, 4 = pacman, 8 = ghost, 16 = power_pill
	private PacMan pac = new PacMan(startCords);
	private JTextField inputField = new JTextField();
	private JButton drawButton = new JButton();


	Spiel() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(width * 50, hight * 50);
		setLayout(null);
		setVisible(true);
		addKeyListener(this);
		// get widget container
		Container cp = getContentPane();

		// edit input field
		inputField.setBounds(25, 25, 200, 25);

		// edit draw button
		drawButton.setBounds(250, 25, 100, 25);
		drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                draw();
            }
        });

		// edit container and add components
		cp.setLayout(null);
		cp.add(inputField);
		cp.add(drawButton);
		
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				spielfeld[i][j] = 0;
			}
		}
		for (int i = 0; i < hight; i++) {
			spielfeld[i][0] = 1;
			spielfeld[i][width - 1] = 1;
		}
		for (int j = 0; j < width; j++) {
			spielfeld[0][j] = 1;
			spielfeld[hight - 1][j] = 1;
		}
	}
	
	public void keyPressed (KeyEvent e) {
		
	}
	
	public void keyReleased (KeyEvent e) {
		
	}
	
	public void keyTyped (KeyEvent e) {
		char keyCode = e.getKeyChar();
		switch(keyCode) {
		case 'w':
			if (spielfeld[pac.getx() - 1][pac.gety()] != 1) {
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] - 4;
				pac.move(0);
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] + 4;
			}
			break;
		case 'd':
			if (spielfeld[pac.getx()][pac.gety() + 1] != 1) {
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] - 4;
				pac.move(1);
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] + 4;
			}
			break;
		case 's':
			if (spielfeld[pac.getx() + 1][pac.gety()] != 1) {
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] - 4;
				pac.move(2);
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] + 4;
			}
			break;
		case 'a':
			if (spielfeld[pac.getx()][pac.gety() - 1] != 1) {
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] - 4;
				pac.move(3);
				spielfeld[pac.getx()][pac.gety()] = spielfeld[pac.getx()][pac.gety()] + 4;
			}
			break;
		default:
			
		}
		displayGame();
	}
	
	public void displayGame() {
		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(spielfeld[i][j]);
			}
			System.out.println();
		}
	}

	public void draw() {
		utils util = new utils();
		drawButton.setVisible(false);
		inputField.setVisible(false);
		String inputString = inputField.getText();
		
		this.spielfeld = util.stringTo2DArray(inputString);
		displayGame();
	}
	
	public static void main(String args[]) {
		Spiel spl = new Spiel();
		
		spl.spielfeld[spl.pac.getx()][spl.pac.gety()] = 4;
		spl.displayGame();
		
	}

}