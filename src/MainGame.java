import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class MainGame {
	public static boolean newGame = false;
//	public static final int WIDTH = GridBoard.getCOLUMNS() * GridBoard.getBLOCKSIZE(),
//			HEIGHT = GridBoard.getROWS() * GridBoard.getBLOCKSIZE() + (GridBoard.getROWS()+10) * 2;
	public static final int WIDTH = 336, HEIGHT = 660;
	
	private static JMenuBar menuBar = new JMenuBar();;
	private JMenu game = new JMenu("Game");
	private JMenuItem gameItem = new JMenuItem("New game");
	private static JFrame GameWindow = new JFrame("Bootleg Tetris");
	private static Audio BGM;

	public MainGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		GameWindow.setSize(WIDTH + 120, HEIGHT);
		GameWindow.setResizable(false);
		GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameWindow.setLocationRelativeTo(null);

		// Build the first menu.
		menuBar.add(game);

		// JgameItems show the menu items
		gameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridBoard Board = null;
				try {
					Board = GridBoard.getInstance();
				} catch (UnsupportedAudioFileException unsupportedAudioFileException) {
					unsupportedAudioFileException.printStackTrace();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				} catch (LineUnavailableException lineUnavailableException) {
					lineUnavailableException.printStackTrace();
				}
				GameWindow.add(Board);
				GameWindow.addKeyListener(Board);
				//menuBar.remove(0);
				menuBar.revalidate();
				
				try {
					BGM = new Audio("audio/NyanCatOriginal-DangCapNhat_4237d_hq.wav");
					BGM.playBGM();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		game.add(gameItem);

		// add a separator
		game.addSeparator();

		gameItem = new JMenuItem("Pause");
		gameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean pause = GridBoard.getPause();
				pause = !pause;
				GridBoard.setPause(pause);
				
				if (pause) {
					PauseScreen.drawPauseScreen();
					BGM.pauseBGM();
					GridBoard.getGameLoop().stop();
				}
				else {
					PauseScreen.removePauseScreen();
					try {
						BGM.resumeBGM();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GridBoard.getGameLoop().start();
				}
			}
		});
		game.add(gameItem);

		gameItem = new JMenuItem("Exit");
		gameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		game.add(gameItem);
		
		
		game = new JMenu("Difficulty");
		menuBar.add(game);
		
		gameItem = new JMenuItem("Easy");
		gameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulty.setDifficulty(0);
				//FormBlock.setSpeed(Difficulty.changeDifficulty());
				FormBlock.setCurrentSpeed(Difficulty.changeDifficulty());
			}
		});
		game.add(gameItem);
		
		gameItem = new JMenuItem("Medium");
		gameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulty.setDifficulty(1);
				//FormBlock.setSpeed(Difficulty.changeDifficulty());
				FormBlock.setCurrentSpeed(Difficulty.changeDifficulty());
			}
		});
		game.add(gameItem);
		
		gameItem = new JMenuItem("Hard");
		gameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulty.setDifficulty(2);
				//FormBlock.setSpeed(Difficulty.changeDifficulty());
				FormBlock.setCurrentSpeed(Difficulty.changeDifficulty());
			}
		});
		game.add(gameItem);

		// add menu bar to frame
		GameWindow.setJMenuBar(menuBar);
		GameWindow.add(PauseScreen.pauseScreen);
		
		GameWindow.setVisible(true);
	}
	
	public static Audio getBGM() {
		return BGM;
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new MainGame();
	}
}
