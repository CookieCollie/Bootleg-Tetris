package application;

import javafx.scene.shape.*;
import java.security.SecureRandom;


public class Controller {
	//Get vars from Tetris
	public static final int MOVE = Tetris.MOVE;
	public static final int SIZE = Tetris.SIZE;
	public static int WIDTH = Tetris.WIDTH;
	public static int HEIGHT = Tetris.HEIGHT;
	public static int [][] GRID = Tetris.GRID;
	
	
	//Move shape to the right
	public static void MoveRight(Form Rotation) {
		if (Rotation.a.getX() + MOVE <= WIDTH - SIZE && Rotation.b.getX() + MOVE <= WIDTH - SIZE
			&& Rotation.c.getX() + MOVE <= WIDTH - SIZE && Rotation.d.getX() + MOVE <= WIDTH - SIZE) {
			int movea = GRID[(int) Rotation.a.getX() / SIZE + 1][(int) Rotation.a.getY() / SIZE];
			int moveb = GRID[(int) Rotation.b.getX() / SIZE + 1][(int) Rotation.b.getY() / SIZE];
			int movec = GRID[(int) Rotation.c.getX() / SIZE + 1][(int) Rotation.c.getY() / SIZE];
			int moved = GRID[(int) Rotation.d.getX() / SIZE + 1][(int) Rotation.d.getY() / SIZE];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				Rotation.a.setX(Rotation.a.getX() + MOVE);
				Rotation.b.setX(Rotation.b.getX() + MOVE);
				Rotation.c.setX(Rotation.c.getX() + MOVE);
				Rotation.d.setX(Rotation.d.getX() + MOVE);
			}
		}
	}
	
	//Move shape to the left
	public static void MoveLeft(Form Rotation) {
		if (Rotation.a.getX() - MOVE >= 0 && Rotation.b.getX() - MOVE >= 0
			&& Rotation.c.getX() - MOVE >= 0 && Rotation.d.getX() - MOVE >= 0) {
			int movea = GRID[(int) Rotation.a.getX() / SIZE - 1][(int) Rotation.a.getY() / SIZE];
			int moveb = GRID[(int) Rotation.b.getX() / SIZE - 1][(int) Rotation.b.getY() / SIZE];
			int movec = GRID[(int) Rotation.c.getX() / SIZE - 1][(int) Rotation.c.getY() / SIZE];
			int moved = GRID[(int) Rotation.d.getX() / SIZE - 1][(int) Rotation.d.getY() / SIZE];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				Rotation.a.setX(Rotation.a.getX() - MOVE);
				Rotation.b.setX(Rotation.b.getX() - MOVE);
				Rotation.c.setX(Rotation.c.getX() - MOVE);
				Rotation.d.setX(Rotation.d.getX() - MOVE);
			}
		}
	}
	
	
	//Make shapes
	public static Form makeShape() {
		SecureRandom random = new SecureRandom();
		int block = random.nextInt(7);
		String Type;
		Rectangle a = new Rectangle(SIZE-1, SIZE-1),
				  b = new Rectangle(SIZE-1, SIZE-1),
				  c = new Rectangle(SIZE-1, SIZE-1),
				  d = new Rectangle(SIZE-1, SIZE-1);
		
		if (block == 0) {
			a.setX(WIDTH/2 - SIZE);
			b.setX(WIDTH/2 - SIZE);
			b.setY(SIZE);
			c.setX(WIDTH/2);
			c.setY(SIZE);
			d.setX(WIDTH/2 + SIZE);
			d.setY(SIZE);
			Type = "J";
		}
		else if (block == 1){
			a.setX(WIDTH/2 + SIZE);
			b.setX(WIDTH/2 - SIZE);
			b.setY(SIZE);
			c.setX(WIDTH/2);
			c.setY(SIZE);
			d.setX(WIDTH/2 + SIZE);
			d.setY(SIZE);
			Type = "L";
		}
		else if (block == 2){
			a.setX(WIDTH/2 - SIZE);
			b.setX(WIDTH/2);
			c.setX(WIDTH/2 - SIZE);
			c.setY(SIZE);
			d.setX(WIDTH/2);
			d.setY(SIZE);
			Type = "O";
		}
		else if (block == 3){
			a.setX(WIDTH/2 + SIZE);
			b.setX(WIDTH/2);
			c.setX(WIDTH/2);
			c.setY(SIZE);
			d.setX(WIDTH/2 - SIZE);
			d.setY(SIZE);
			Type = "S";
		}
		else if (block == 4){
			a.setX(WIDTH/2 + SIZE);
			b.setX(WIDTH/2);
			c.setX(WIDTH/2 + SIZE);
			c.setY(SIZE);
			d.setX(WIDTH/2 + 2*SIZE);
			d.setY(SIZE);
			Type = "Z";
		}
		else if (block == 5){
			a.setX(WIDTH/2 - SIZE);
			b.setX(WIDTH/2);
			c.setX(WIDTH/2);
			c.setY(SIZE);
			d.setX(WIDTH/2 + SIZE);
			Type = "T";
		}
		else {
			a.setX(WIDTH/2 - 2*SIZE);
			b.setX(WIDTH/2 - SIZE);
			c.setX(WIDTH/2);
			d.setX(WIDTH/2 + SIZE);
			Type = "I";
		}
		
		return new Form(a, b, c, d, Type);
	}	
}
