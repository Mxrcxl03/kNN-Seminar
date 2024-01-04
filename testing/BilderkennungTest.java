import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class BilderkennungTest {
	public static void main(String[] args) {
		Bilderkennung bild = new Bilderkennung();
		//int[] netzkonf1 = {1024, 16, 16, 10};
		//int[] netzkonf1 = {1024, 22, 10};
		int[] netzkonf1 = {1024, 16, 10};
		NeuronalesNetz n1 = new NeuronalesNetz();
		n1.initNetz(netzkonf1);

		n1.initGewichteRandom();
		//double[][][] array = n1.weights;
		//double[][][] arraySize = new double[3][1025][10];
		//CSVReader.writeGewichteCSV("img/weights.csv", array);
		//String gewichtsdatei = "csv/weights.csv";
		//double[][][] array2 = CSVReader.readCSV(gewichtsdatei, arraySize);
		//n1.initGewichte(array2);

		for (int i = 0; i < n1.weights[0][0].length; i++) {
			n1.layer[1][i].setActivationFunction("tanh");
			//n1.layer[2][i].setActivationFunction("tanh");
		}

		bild.runTraining(2500, n1, 0.01);
		System.out.println("--------------------------------------");
		//System.out.println("Error 1: " + n1.computeError(bild.getIdealOutput(1)));
		bild.runTestingSN(1, n1);
		//System.out.println("Error 2: " + n1.computeError(bild.getIdealOutput(1)));
		System.out.println("--------------------------------------");
		double[] output1 = n1.getOutputAsDoubleArray();

		bild.printOutputToNumber(output1);
		bild.printOutWinner(output1);
		bild.printOutTopThree(output1);
		System.out.println("--------------------------------------");
		//bild.runTestingSN(4, n1);
		//bild.runTestingAN(n1);

		/*try {
			//String filepath1 = "img/trainingdata/11.png";
			String filepath1 = "img/testdata/14.png";
			double[] ideal1 = bild.getIdealOutput(1);

			// Einlesen des Bilds
			BufferedImage image1 = ImageIO.read(new File(filepath1));
			// Konvertieren Schwarz / Weiß
			BufferedImage binaryImage1 = PNGHelper.convertToBinary(image1);
			Raster rasterIMG1 = binaryImage1.getData();

			int[][] arr1 = PNGHelper.pixelValueToArray(rasterIMG1);
			double[] input1 = PNGHelper.convertPixelArray(arr1);

			//PNGHelper.printOutArrayWithRaster(arr1);

			//printOutArray(arr1);
			//printOutArrayWithRaster(arr1);

			//printOutRGBValue(image1);
			//printOutRGBValue(binaryImage1);

			//ImageIO.write(binaryImage, "png", new File("img/schwarz_weiss_32x32.png")); // Abspeichern in Unterordner
		} catch (FileNotFoundException file) {
			// Fehler sollte die Datei nicht gefunden werden
			file.printStackTrace();
		} catch (IOException e) {
			// Fehler beim Einlesen des Bilds
			e.printStackTrace();
		}*/
	}
}
