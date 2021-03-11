package BubbleMorph.Tests;

import BubbleMorph.Morpher;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MorpherTest {

	public static void main(String[] args) {

		if (args.length == 1 || args.length == 2) {

			String outputName = "unnamed_";

			if (args.length == 2) {

				outputName = args[1];

			}

			MorpherTest test = new MorpherTest(args[0], outputName);

		}

		System.out.println("Error - you need to specify a file to morph!");
	}

	MorpherTest(String fileName, String outputName) {

		int stepsPerFrame = 1;

		try {

			BufferedImage testImage = ImageIO.read(new File(fileName));

			Morpher morpher = new Morpher(testImage);

			String outputNameBase = outputName;

			morpher.outputImage(outputNameBase + "unaltered.png");

			morpher.shuffle();

			int frameNumber = 0;

			morpher.outputImage(outputNameBase + String.format("%05d", frameNumber) + ".png");

			frameNumber++;

			while (!morpher.isSorted()) {

				morpher.step();

				if (morpher.getNumberOfSteps() % stepsPerFrame == 0) {

					morpher.outputImage(outputNameBase + String.format("%05d", frameNumber) + ".png");
					frameNumber++;

					System.out.println("Steps completed: " + morpher.getNumberOfSteps());
					System.out.println("Number of swaps: " + morpher.getNumberOfSwaps());
					System.out.println();

				}

			}


			// Output the final fully sorted frame.
			morpher.outputImage(outputNameBase + String.format("%05d", frameNumber) + ".png");
			System.out.println("Done!");

		} catch (Exception e) {

			System.out.println("Error - failed to load " + fileName + "!");
			e.printStackTrace();

		}


	}


}