package BubbleMorph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Morpher {

	private Pixel[][] pixelRaster;
	private final int rasterWidth;
	private final int rasterHeight;

	private int numberOfSteps = 0;
	private int numberOfSwaps = 0;

	public Morpher(BufferedImage image) {

		rasterHeight = image.getHeight();
		rasterWidth = image.getWidth();

		pixelRaster = new Pixel[rasterHeight][rasterWidth];

		// Extract pixel values from the BufferedImaged and put them into a custom raster.
		for (int y = 0, currentOrdinality = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

				currentOrdinality = y*rasterWidth + x;

				pixelRaster[y][x] = new Pixel(x, y, image.getRGB(x, y), currentOrdinality);

			}

		}

	}

	public void shuffle() {

		// Shuffles the array of pixels, by starting at the end, storing that pixel, selecting a random
		// pixel from below that index, inserting it at the back, then putting the stored pixel back into
		// the lower index. This guarantees actual random shuffling, which simple random element swapping does not.

		Pixel[] pixels = new Pixel[rasterWidth*rasterHeight];

		// Convert the 2D pixel raster into a linear 1D array (linearize the raster.)
		for (int y = 0, currentOrdinality = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

				currentOrdinality = y*rasterWidth + x;
				pixels[currentOrdinality] = pixelRaster[y][x]; 

			}

		}

		Random random = new Random();

		// Randomize the linearized raster.
		for (int i = pixels.length-1; i > 0; i--) {

			Pixel tempPixel = pixels[i];

			int selectedIndex = random.nextInt(i);

			pixels[i] = pixels[selectedIndex];
			pixels[selectedIndex] = tempPixel;

		}

		// Convert the linearized raster back into an actual 2D raster (de-linearize it).
		for (int y = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

				pixelRaster[y][x] = pixels[y*rasterWidth + x];

			}

		}

	}

	public void outputImage(String fileName) {

		BufferedImage outputImage = new BufferedImage(rasterWidth, rasterHeight, BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < rasterHeight; y++) {

			for (int x = 0; x < rasterWidth; x++) {

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

	public void step() {

		bubbleSortRows(2);			// bubblesort even rows
		bubbleSortColumns(2);		// bubblesort even columns
		bubbleSortEdges(0);			// bubblesort edges of even rows

		bubbleSortRows(1);			// bubblesort odd rows
		bubbleSortColumns(1);		// bubblesort odd columns
		bubbleSortEdges(1);			// bubblesort edges of odd rows

		numberOfSteps++;

	}

	private void bubbleSortRows(int startingRow) {

		for (int row = startingRow; row < rasterHeight; row+=2) {

			for (int column = 0; column < rasterWidth; column++) {

				if (pixelRaster[row][column].ordinality < pixelRaster[row-1][column].ordinality) {

					swapPixels(pixelRaster[row][column], pixelRaster[row-1][column]);

				}

			}

		}

	}

	private void bubbleSortColumns(int startingColumn) {

		for (int column = startingColumn; column < rasterWidth; column+=2) {

			for (int row = 0; row < rasterHeight; row++) {

				if (pixelRaster[row][column].ordinality < pixelRaster[row][column-1].ordinality) {

					swapPixels(pixelRaster[row][column], pixelRaster[row][column-1]);

				}

			}

		}

	}

	private void bubbleSortEdges(int startingRow) {

		for (int row = startingRow; row < rasterHeight-1; row+=2) {

			if (pixelRaster[row][rasterWidth-1].ordinality > pixelRaster[row+1][0].ordinality) {

				swapPixels(pixelRaster[row][rasterWidth-1], pixelRaster[row+1][0]);

			}

		}

	}

	private void swapPixels(Pixel pixelOne, Pixel pixelTwo) {

		Pixel pixelOneTemp = new Pixel(pixelOne.x, pixelOne.y, pixelOne.getColor(), pixelOne.ordinality);
		Pixel pixelTwoTemp = new Pixel(pixelTwo.x, pixelTwo.y, pixelTwo.getColor(), pixelTwo.ordinality);

		//pixelOne = pixelTwoTemp;
		//pixelTwo = pixelOneTemp;

		pixelOne = new Pixel(pixelTwoTemp.x, pixelTwoTemp.y, pixelTwoTemp.getColor(), pixelTwoTemp.ordinality);
		pixelTwo = new Pixel(pixelOneTemp.x, pixelOneTemp.y, pixelOneTemp.getColor(), pixelOneTemp.ordinality);

		numberOfSwaps++;

	}

	public boolean isSorted() {

		boolean rasterIsSorted = true;

		for (int row = 0; row < rasterHeight && rasterIsSorted; row++) {

			for (int column = 0; column < rasterWidth-1 && rasterIsSorted; column++) {

				if (pixelRaster[row][column].ordinality > pixelRaster[row][column+1].ordinality) {

					rasterIsSorted = false;

				}

			}

			if (row != rasterHeight-1) {

				if (pixelRaster[row][rasterWidth-1].ordinality > pixelRaster[row+1][0].ordinality) {

					rasterIsSorted = false;

				}

			}

		}

		return rasterIsSorted;

	}

	public int getNumberOfSteps() {

		return numberOfSteps;

	}

	public int getNumberOfSwaps() {

		return numberOfSwaps;

	}

}