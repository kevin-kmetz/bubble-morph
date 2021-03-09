package BubbleMorph;

import java.awt.Color;

public class Pixel {

	final int x;				// Represents the pixel's original xy coordinates,
	final int y					// not its present coordinate.

	Color color;

	public Pixel(int x, int y, Color color) {

		this.x = x;
		this.y = y;

		this.color = color;

	}

}