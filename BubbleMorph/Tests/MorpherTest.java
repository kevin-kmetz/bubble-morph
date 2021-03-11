package BubbleMorph.Tests;

import BubbleMorph.Morpher;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MorpherTest {

	public static void main(String[] args) {

		if (args.length >= 1 && args.length <= 3) {

			String outputName = "unnamed";
			int stepsPerFrame = 1;

			if (args.length >= 2) {

				outputName = args[1];

			}

			if (args.length == 3) {

				stepsPerFrame = Integer.parseInt(args[2]);

			}

			MorpherTest test = new MorpherTest(args[0], outputName, stepsPerFrame);

		} else {

			System.out.println("Error - you need to specify a file to morph!");

		}

	}

	MorpherTest(String fileName, String outputName, int stepsPerFrame) {

		try {

			BufferedImage testImage = ImageIO.read(new File(fileName));

			File folder = new File(outputName);
			folder.mkdir();

			String fileNamePrefix = "./" + outputName + "/" + outputName + "_";

			Morpher morpher = new Morpher(testImage);

			morpher.outputImage(fileNamePrefix + "_unaltered.png");

			morpher.shuffle();

			int frameNumber = 0;

			morpher.outputImage(fileNamePrefix + String.format("%05d", frameNumber) + ".png");

			frameNumber++;

			while (!morpher.isSorted()) {

				morpher.step();

				if (morpher.getNumberOfSteps() % stepsPerFrame == 0) {

					morpher.outputImage(fileNamePrefix + String.format("%05d", frameNumber) + ".png");
					frameNumber++;

					System.out.println("Steps completed: " + morpher.getNumberOfSteps());
					System.out.println("Number of swaps: " + morpher.getNumberOfSwaps());
					System.out.println();

				}

			}


			// Output the final fully sorted frame.
			morpher.outputImage(fileNamePrefix + String.format("%05d", frameNumber) + ".png");
			System.out.println("Done!");

		} catch (Exception e) {

			System.out.println("Error - failed to load " + fileName + "!");
			e.printStackTrace();

		}


	}


}