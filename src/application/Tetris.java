package application;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {

	// Vars
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int WIDTH = SIZE * 12;
	public static int HEIGHT = SIZE * 24;
	public static int[][] GRID = new int[WIDTH / SIZE][HEIGHT / SIZE]; // Creates the grid
	private static Pane pane = new Pane(); // Creates elements inside GameWindow
	private static Form shape; // Creates Tetris shapes
	private static Scene GameWindow = new Scene(pane, WIDTH, HEIGHT + 150); // Creates the window for the game
	public static int Score = 0;
	public static int OnTop = 0;
	private static boolean Runtime = true;
	private static Form NextShape = Controller.makeShape();
	private static int BrokenLines = 0;

	// Create game window
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		for (int[] a : GRID) {
			Arrays.fill(a, 0);
		}
		Line line = new Line(0, 150, WIDTH, 150);
		Text ScoreText = new Text("Score: ");
		ScoreText.setX(5);
		ScoreText.setY(50);
		Text BrknLinesText = new Text("Lines broke: ");
		ScoreText.setX(5);
		ScoreText.setY(140);
		pane.getChildren().addAll(line, ScoreText, BrknLinesText);

		Form FirstBlock = NextShape;
		pane.getChildren().addAll(FirstBlock.a, FirstBlock.b, FirstBlock.c, FirstBlock.d);
		MoveOnKeyPress(FirstBlock);
		shape = FirstBlock;
		NextShape = Controller.makeShape();
		stage.setScene(GameWindow);
		stage.setTitle("Bootleg Tetris");
		stage.show();

		Timer FallingTime = new Timer();
		TimerTask Task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (shape.a.getY() == 0 || shape.b.getY() == 0 || shape.c.getY() == 0 || shape.d.getY() == 0) {
							OnTop++;
						} else {
							OnTop = 0;
						}

						// Game over
						if (OnTop == 2) {
							Text GameOver = new Text("GAME OVER");
							GameOver.setFill(Color.RED);
							pane.getChildren().add(GameOver);
							Runtime = false;
						}

						// Exit game
						if (OnTop == 15) {
							System.exit(0);
						}

						// Runtime
						if (Runtime) {
							MoveDown(shape);
							ScoreText.setText("Score: " + Integer.toString(Score));
							BrknLinesText.setText("Lines broke: " + Integer.toString(BrokenLines));
						}
					}
				});
			}
		};
		FallingTime.schedule(Task, 0, 300);
	}

	// Detect key press and move shape (Currently use arrow keys, will try to add
	// WASD later)
	private void MoveOnKeyPress(Form shape) {
		GameWindow.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.MoveRight(shape);
					break;
				case LEFT:
					Controller.MoveLeft(shape);
					break;
				case DOWN:
					MoveDown(shape);
					break;
				case UP:
					MoveRotate(shape);
					break;
				}
			}
		});
	}

	// Rotate shape
	private void MoveRotate(Form shape) {
		int sh = shape.Rotation;
		Rectangle a = shape.a;
		Rectangle b = shape.b;
		Rectangle c = shape.c;
		Rectangle d = shape.d;
		switch (shape.getType()) {
		case "J":
			if (sh == 1 && CheckShape(a, 1, -1) && CheckShape(c, -1, -1) && CheckShape(d, -2, -2)) {
				MoveRight(shape.a);
				MoveDown(shape.a);
				MoveDown(shape.c);
				MoveLeft(shape.c);
				MoveDown(shape.d);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(a, 1, -1) && CheckShape(c, -1, -1) && CheckShape(d, -2, -2)) {
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				MoveUp(shape.d);
				MoveUp(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(a, 1, -1) && CheckShape(c, -1, -1) && CheckShape(d, -2, -2)) {
				MoveLeft(shape.a);
				MoveUp(shape.a);
				MoveUp(shape.c);
				MoveRight(shape.c);
				MoveUp(shape.d);
				MoveUp(shape.d);
				MoveRight(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(a, 1, -1) && CheckShape(c, -1, -1) && CheckShape(d, -2, -2)) {
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveRight(shape.d);
				MoveRight(shape.d);
				MoveDown(shape.d);
				MoveDown(shape.d);
				shape.Rotate();
				break;
			}
			break;

		case "L":
			if (sh == 1 && CheckShape(a, 1, -1) && CheckShape(c, 1, 1) && CheckShape(d, 2, 2)) {
				MoveRight(shape.a);
				MoveDown(shape.a);
				MoveUp(shape.c);
				MoveRight(shape.c);
				MoveUp(shape.b);
				MoveUp(shape.b);
				MoveRight(shape.b);
				MoveRight(shape.b);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(a, 1, -1) && CheckShape(c, -1, -1) && CheckShape(b, -2, -2)) {
				MoveRight(shape.a);
				MoveDown(shape.a);
				MoveDown(shape.c);
				MoveLeft(shape.c);
				MoveDown(shape.d);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(a, -1, 1) && CheckShape(c, -1, -1) && CheckShape(d, -2, -2)) {
				MoveLeft(shape.a);
				MoveUp(shape.a);
				MoveDown(shape.c);
				MoveLeft(shape.c);
				MoveDown(shape.d);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(a, 1, 1) && CheckShape(c, -1, 1) && CheckShape(b, -2, 2)) {
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveLeft(shape.b);
				MoveLeft(shape.b);
				MoveUp(shape.b);
				MoveUp(shape.b);
				shape.Rotate();
				break;
			}
			break;

		case "O":
			break;

		case "S":
			if (sh == 1 && CheckShape(a, -1, -1) && CheckShape(c, -1, 1) && CheckShape(d, 0, 2)) {
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveUp(shape.d);
				MoveUp(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(a, 1, 1) && CheckShape(c, 1, -1) && CheckShape(d, 0, -2)) {
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveDown(shape.d);
				MoveDown(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(a, -1, -1) && CheckShape(c, -1, 1) && CheckShape(d, 0, 2)) {
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveUp(shape.d);
				MoveUp(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(a, 1, 1) && CheckShape(c, 1, -1) && CheckShape(d, 0, -2)) {
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveDown(shape.d);
				MoveDown(shape.d);
				shape.Rotate();
				break;
			}
			break;

		case "T":
			if (sh == 1 && CheckShape(a, 1, 1) && CheckShape(c, -1, 1) && CheckShape(d, -1, -1)) {
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(a, 1, -1) && CheckShape(c, 1, 1) && CheckShape(d, -1, 1)) {
				MoveRight(shape.a);
				MoveDown(shape.a);
				MoveUp(shape.c);
				MoveRight(shape.c);
				MoveLeft(shape.d);
				MoveUp(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(a, 1, -1) && CheckShape(c, 1, 1) && CheckShape(d, -1, 1)) {
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveUp(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(a, -1, 1) && CheckShape(c, -1, -1) && CheckShape(d, 1, -1)) {
				MoveLeft(shape.a);
				MoveUp(shape.a);
				MoveDown(shape.c);
				MoveLeft(shape.c);
				MoveRight(shape.d);
				MoveDown(shape.d);
				shape.Rotate();
				break;
			}
			break;

		case "Z":
			if (sh == 1 && CheckShape(b, 1, 1) && CheckShape(c, -1, 1) && CheckShape(d, -2, 0)) {
				MoveUp(shape.b);
				MoveRight(shape.b);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(b, -1, -1) && CheckShape(c, 1, -1) && CheckShape(d, 2, 0)) {
				MoveDown(shape.b);
				MoveLeft(shape.b);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveRight(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(b, 1, 1) && CheckShape(c, -1, 1) && CheckShape(d, -2, 0)) {
				MoveUp(shape.b);
				MoveRight(shape.b);
				MoveLeft(shape.c);
				MoveUp(shape.c);
				MoveLeft(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(b, -1, -1) && CheckShape(c, 1, -1) && CheckShape(d, 2, 0)) {
				MoveDown(shape.b);
				MoveLeft(shape.b);
				MoveRight(shape.c);
				MoveDown(shape.c);
				MoveRight(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			break;

		case "I":
			if (sh == 1 && CheckShape(a, 2, 2) && CheckShape(b, 1, 1) && CheckShape(d, -1, -1)) {
				MoveUp(shape.a);
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveRight(shape.a);
				MoveUp(shape.b);
				MoveRight(shape.b);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 2 && CheckShape(a, -2, -2) && CheckShape(b, -1, -1) && CheckShape(d, 1, 1)) {
				MoveDown(shape.a);
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveLeft(shape.a);
				MoveDown(shape.b);
				MoveLeft(shape.b);
				MoveUp(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 3 && CheckShape(a, 2, 2) && CheckShape(b, 1, 1) && CheckShape(d, -1, -1)) {
				MoveUp(shape.a);
				MoveUp(shape.a);
				MoveRight(shape.a);
				MoveRight(shape.a);
				MoveUp(shape.b);
				MoveRight(shape.b);
				MoveDown(shape.d);
				MoveLeft(shape.d);
				shape.Rotate();
				break;
			}
			if (sh == 4 && CheckShape(a, -2, -2) && CheckShape(b, -1, -1) && CheckShape(d, 1, 1)) {
				MoveDown(shape.a);
				MoveDown(shape.a);
				MoveLeft(shape.a);
				MoveLeft(shape.a);
				MoveDown(shape.b);
				MoveLeft(shape.b);
				MoveUp(shape.d);
				MoveRight(shape.d);
				shape.Rotate();
				break;
			}
			break;
		}

	}

	// Remove full lines and add points
	private void RemoveFullRows(Pane pane) {
		// Stores elements in arrays
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Node> newRects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		int FullLine = 0;

		// Check full line position
		for (int y = 0; y < GRID[0].length; y++) {
			for (int x = 0; x < GRID.length; x++) {
				if (GRID[x][y] == 1) {
					FullLine++;
				}
			}

			if (FullLine == GRID.length) {
				lines.add(y);
			}

			FullLine = 0;
		}

		// Remove full line and increase points
		if (lines.size() > 0) {
			do {
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle) {
						rects.add(node);
					}
				}
				Score += 10;
				BrokenLines++;

				// Remove lines
				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() == lines.get(0) * SIZE) {
						GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						pane.getChildren().remove(node);
					} else
						newRects.add(node);
				}

				for (Node node : newRects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() < lines.get(0) * SIZE) {
						GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						a.setY(a.getY() + SIZE);
					}
					lines.remove(0);
					rects.clear();
					newRects.clear();

					for (Node node : pane.getChildren()) {
						if (node instanceof Rectangle) {
							rects.add(node);
						}
					}

					for (Node node : rects) {
						Rectangle a = (Rectangle) node;
						try {
							GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
						} catch (ArrayIndexOutOfBoundsException e) {

						}
					}
					rects.clear();
				}
			} while (lines.size() > 0);
		}
	}

	// Check shape to rotate
	private boolean CheckShape(Rectangle shape, int x, int y) {
		boolean xBool = false;
		boolean yBool = false;
		if (x >= 0) {
			xBool = shape.getX() + x * MOVE <= WIDTH - SIZE;
		} else {
			xBool = shape.getX() + x * MOVE >= 0;
		}

		if (y >= 0) {
			yBool = shape.getY() + y * MOVE > 0;
		} else {
			yBool = shape.getY() + y * MOVE < HEIGHT - SIZE;
		}

		return xBool && yBool && GRID[((int) shape.getX() / SIZE) + x][((int) shape.getY() / SIZE) - y] == 0;
	}

	// Move individual square
	private void MoveUp(Rectangle rect) {
		if (rect.getY() - MOVE > 0) {
			rect.setY(rect.getY() - MOVE);
		}
	}

	private void MoveDown(Rectangle rect) {
		if (rect.getY() + MOVE > HEIGHT) {
			rect.setY(rect.getY() + MOVE);
		}
	}

	private void MoveLeft(Rectangle rect) {
		if (rect.getX() - MOVE >= 0) {
			rect.setY(rect.getY() - MOVE);
		}
	}

	private void MoveRight(Rectangle rect) {
		if (rect.getY() + MOVE < WIDTH - SIZE) {
			rect.setY(rect.getY() + MOVE);
		}
	}

	public void MoveDown(Form form) {
		if (form.a.getY() == HEIGHT - SIZE || form.b.getY() == HEIGHT - SIZE || form.c.getY() == HEIGHT - SIZE
				|| form.d.getY() == HEIGHT - SIZE || MoveA(form) || MoveB(form) || MoveC(form) || MoveD(form)) {
			GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			GRID[(int) form.b.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			GRID[(int) form.c.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			GRID[(int) form.d.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			RemoveFullRows(pane);

			// Create another shape
			Form NextBlock = NextShape;
			NextShape = Controller.makeShape();
			shape = NextBlock;
			pane.getChildren().addAll(NextBlock.a, NextBlock.b, NextBlock.c, NextBlock.d);
			MoveOnKeyPress(NextBlock);
		}

		if (form.a.getY() + MOVE < HEIGHT && form.b.getY() + MOVE < HEIGHT && form.c.getY() + MOVE < HEIGHT
				&& form.d.getY() + MOVE < HEIGHT) {
			int DownA = GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE + 1];
			int DownB = GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE + 1];
			int DownC = GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE + 1];
			int DownD = GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE + 1];

			if (DownA == 0 && DownA == DownB && DownB == DownC && DownC == DownD) {
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
			}
		}
	}

	private boolean MoveA(Form form) {
		return (GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE + 1] == 1);
	}

	private boolean MoveB(Form form) {
		return (GRID[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE + 1] == 1);
	}

	private boolean MoveC(Form form) {
		return (GRID[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE + 1] == 1);
	}

	private boolean MoveD(Form form) {
		return (GRID[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE + 1] == 1);
	}
}
