package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Form {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	Color color;
	private String Type;
	public int Rotation = 1;
	
	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String Type) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.Type = Type;

		//Set color
		switch (Type) {
		case "J":
			color = Color.ALICEBLUE;
			break;
		case "L":
			color = Color.AQUA;
			break;
		case "O":
			color = Color.BEIGE;
			break;
		case "S":
			color = Color.BISQUE;
			break;
		case "T":
			color = Color.CADETBLUE;
			break;
		case "Z":
			color = Color.CRIMSON;
			break;
		case "I":
			color = Color.PINK;
			break;
		default:
			throw new IllegalArgumentException("Unknown shape: " + Type);
		}
		
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}
	
	public String getType() {
		return Type;
	}
	
	public void Rotate() {
		if (Rotation != 4) {
			Rotation++;
		}
		else {
			Rotation = 1;
		}
	}
}
