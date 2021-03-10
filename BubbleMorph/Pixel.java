package BubbleMorph;

class Pixel {

	final int x;				// Represents the pixel's original xy coordinates that it is
	final int y;				// trying to return to, not its present coordinate.
	final int ordinality;		// A measure of linear ordinality, if the raster was converted to a 1-dimensional array.

	private int color;

	Pixel(int x, int y, int color, int ordinality) {

		this.x = x;
		this.y = y;

		this.color = color;

		this.ordinality = ordinality;
		
	}

	int getColor() {

		return color;

	}

}