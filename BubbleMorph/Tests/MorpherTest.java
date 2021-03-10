package BubbleMorph.Tests;

import BubbleMorph.Morpher;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MorpherTest {

	public static void main(String[] args) {

		if (args.length == 1) {

			MorpherTest test = new MorpherTest(args[0]);

		}

		System.out.println("Error - you need to specify a file to morph!");
	}

	MorpherTest(String fileName) {

		try {

			BufferedImage testImage = ImageIO.read(new File(fileName));

			Morpher morpher = new Morpher(testImage);

		} catch (Exception e) {

			System.out.println("Error - failed to load " + fileName + "!");
			e.printStackTrace();

		}


	}


}