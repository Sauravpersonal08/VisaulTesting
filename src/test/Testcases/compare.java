package Testcases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;

public class compare {
//useless
	public static void main(String[] args) throws IOException {
		BufferedImage expectedImage = ImageComparisonUtil
				.readImageFromResources("C:\\Users\\Aman Arora\\OneDrive\\Desktop\\GitPractice\\1.png");
		BufferedImage actualImage = ImageComparisonUtil
				.readImageFromResources("C:\\Users\\Aman Arora\\OneDrive\\Desktop\\GitPractice\\2.png");
		File resultDestination = new File("errorScreenshots\\" + "result.png");
		// Create ImageComparison object with result destination and compare the images.
		ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
		System.out.println(imageComparisonResult.getDifferencePercent());
		if (imageComparisonResult.getDifferencePercent() > 0) {
			ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());
		} else {
			System.out.println("Both images are similar.");
		}
		ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());
	}

}
