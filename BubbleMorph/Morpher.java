package BubbleMorph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Morpher {

	Pixel[][] pixelRaster;
	int rasterWidth;
	int rasterHeight;

	int numberOfSteps = 0;
	int numberOfSwaps = 0;

	public Morpher(BufferedImage image) {

		rasterHeight = image.getHeight();
		rasterWidth = image.getWidth();

		Pixel[] pixels = new Pixel[rasterWidth*rasterHeight];

		for (int y = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

				pixels[y*rasterWidth + x] = new Pixel(x, y, image.getRGB(x, y)); 

			}

		}

		shuffle(pixels);

		pixelRaster = new Pixel[rasterHeight][rasterWidth];

		for (int y = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

				pixelRaster[y][x] = pixels[x*y];

			}

		}

		outputImage(pixelRaster, "scrambled.png");

	}

	private void shuffle(Pixel[] pixels) {

		// Shuffles the array of pixels, by starting at the end, storing that pixel, selecting a random
		// pixel from below that index, inserting it at the back, then putting the stored pixel back into
		// the lower index. This guarantees actual random shuffling, which simple random element swapping does not.

		Random random = new Random();

		for (int i = pixels.length-1; i > 0; i--) {

			Pixel tempPixel = pixels[i];

			int selectedIndex = random.nextInt(i);

			pixels[i] = pixels[selectedIndex];
			pixels[selectedIndex] = tempPixel;

		}

	}

	private void outputImage(Pixel[][] pixelRaster, String fileName) {

		int rasterLength = pixelRaster[0].length;
		int rasterHeight = pixelRaster.length;

		BufferedImage outputImage = new BufferedImage(rasterLength, rasterHeight, BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterLength; x++) {

				outputImage.setRGB(x, y, pixelRaster[y][x].getColor());

			}

		}

		try {

			File outputFile = new File(fileName);
			outputFile.createNewFile();

			ImageIO.write(outputImage, "png", outputFile);

		} catch (Exception e) {

			System.out.println("Error - unable to output image!");
			e.printStackTrace();

		}

	}

	void step() {

		bubbleSortRows(2);			// bubblesort even rows
		bubbleSortColumns(2);	// bubblesort even columns
		bubbleSortEdges(0);		// bubblesort edges of even rows

		bubbleSortRows(1);		// bubblesort odd rows
		bubbleSortColumns(1);		// bubblesort odd columns
		bubbleSortEdges(1);		// bubblesort edges of odd rows

		numberOfSteps++;

	}

	void bubbleSortRows(int startingRow) {

		for (int row = startingRow; row < rasterHeight; row+=2) {

			for (int column = 0; column < rasterWidth; column++) {

				if (pixelRaster[row][column].y < pixelRaster[row-1][column].y) {

					swapPixels(pixelRaster[row][column], pixelRaster[row-1][column]);

				}

			}

		}

	}

	void bubbleSortColumns(int startingColumn) {

		for (int column = startingColumn; column < rasterWidth; column+=2) {

			for (int row = 0; row < rasterHeight; row++) {

				if (pixelRaster[row][column].x < pixelRaster[row][column-1].x) {

					swapPixels(pixelRaster[row][column], pixelRaster[row][column-1]);

				}

			}

		}

	}

	void bubbleSortEdges(int startingRow) {

		for (int row = startingRow; row < rasterHeight-1; row+=2) {

			if (pixelRaster[row][rasterWidth-1] > pixelRaster[row+1][0]) {

				swapPixels(pixelRaster[row]rasterWidth-1], pixelRaster[row+1][0]);

			}

		}

	}

	void swapPixels(Pixel pixelOne, Pixel pixelTwo) {

		Pixel pixelOneTemp = new Pixel(pixelOne.x, pixelOne.y, pixelOne.getColor());
		Pixel pixelTwoTemp = new Pixel(pixelTwo.x, pixelOne.y, pixelTwo.getColor());

		pixelOne = pixelTwoTemp;
		pixelTwo = pixelOneTemp;

		numberOfSwaps++;

	}

}