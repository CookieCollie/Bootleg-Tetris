import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainGame {
	public static boolean newGame = false;
	public static final int WIDTH = GridBoard.getCOLUMNS() * GridBoard.getBLOCKSIZE(),
			HEIGHT = GridBoard.getROWS() * GridBoard.getBLOCKSIZE() + (GridBoard.getROWS()+10) * 2;

	public MainGame() {
		JFrame GameWindow = new JFrame("Bootleg Tetris");
		
		GameWindow.setSize(WIDTH + 120, HEIGHT);
		GameWindow.setResizable(false);
		GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameWindow.setLocationRelativeTo(null);
		

		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu game;
		JMenuItem menuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		game = new JMenu("Game");
		game.setMnemonic(KeyEvent.VK_F);
		game.getAccessibleContext().setAccessibleDescription("File menu");
		menuBar.add(game);

		// JMenuItems show the menu items
		menuItem = new JMenuItem("New game");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridBoard Board = new GridBoard();
				GameWindow.add(Board);
				GameWindow.addKeyListener(Board);
				menuBar.remove(0);
				menuBar.revalidate();
			}
		});
		game.add(menuItem);

		// add a separator
		game.addSeparator();

		menuItem = new JMenuItem("Pause", new ImageIcon("images/pause.gif"));
		menuItem.setMnemonic(KeyEvent.VK_P);
		game.add(menuItem);

		menuItem = new JMenuItem("Exit", new ImageIcon("images/exit.gif"));
		menuItem.setMnemonic(KeyEvent.VK_E);
		game.add(menuItem);

		// add menu bar to frame
		GameWindow.setJMenuBar(menuBar);
		
		GameWindow.setVisible(true);
	}

	public static void main(String[] args) {
		new MainGame();
	}
}
