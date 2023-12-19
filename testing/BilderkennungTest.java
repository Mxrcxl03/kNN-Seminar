import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BilderkennungTest {
	public static void main(String[] args) {
		Bilderkennung bild = new Bilderkennung();
		int[] netzkonf1 = {1024, 16, 16, 10};
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
			n1.layer[2][i].setActivationFunction("tanh");
		}

		try {
			String filepath1 = "img/testdata/04.png";
			//int[] ideal0 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			double[] ideal1 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0};

			// Einlesen des Bilds
			BufferedImage image1 = ImageIO.read(new File(filepath1));
			// Konvertieren Schwarz / Weiß
			BufferedImage binaryImage1 = PNGHelper.convertToBinary(image1);
			Raster rasterIMG1 = binaryImage1.getData();

			int[][] arr1 = PNGHelper.pixelValueToArray(rasterIMG1);
			double[] input1 = PNGHelper.convertPixelArray(arr1);

			bild.runTraining(1000, n1);
			System.out.println("--------------------------------------");
			n1.computeNN(input1);
			double[] output = n1.getOutputAsDoubleArray();
			int x = 0;
			for (double j : output) {
				System.out.println(j + "\t\t\t-\t\t" + x);
				x++;
			}

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
		}
	}

	/*// Ausführung von einem Trainingsdurchlauf
	public static void runTraining(int iteration, double[] input, double[] ideal, NeuronalesNetz n1) {
		// Error-Array mit der Entwicklung des Fehlers auf die Werte
		double[] errorIteration = new double[iteration];
		// Counter zur Auswahl der unterschiedlichen Trainingsdaten in einer Reihenfolge
		int counter = 0;
		for(int i = 0; i < iteration; i++) {
			n1.backpropagation(input, ideal, lernrate);
			System.out.println("Durchgang: " + (i + 1));
			n1.ausgabeOutput();
			errorIteration[i] = n1.computeError(ideal);


			if (counter == 30) {
				counter = 0;
			} else {
				counter++;
			}
		}
	}*/
}
