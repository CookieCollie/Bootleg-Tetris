import javax.swing.*;

public class MainGame {
	public static final int WIDTH = 315, HEIGHT = 635;
	
	public MainGame() {
		JFrame GameWindow = new JFrame("Bootleg Tetris");
		GameWindow.setSize(WIDTH,HEIGHT);
		GameWindow.setResizable(false);
		GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameWindow.setLocationRelativeTo(null);
		
		GridBoard Board = new GridBoard();
		GameWindow.add(Board);
		
		GameWindow.addKeyListener(Board);
		
		GameWindow.setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) {
		new MainGame();
	}
}
