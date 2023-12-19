import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Bilderkennung {
	// Ausführung von einem Trainingsdurchlauf
	public static void runTraining(int iteration, NeuronalesNetz n1) {
		// TODO Lernrate anpassen
		double lernrate = 0.01;
		// Error-Array mit der Entwicklung des Fehlers auf die Werte
		double[] errorIteration = new double[iteration];
		Random random = new Random();
		for (int i = 0; i < iteration; i++) {
			// Zahl zwischen 0 - 9 zur Auswahl der unterschiedlichen Trainingsdaten in einer zufälligen Reihenfolge
			int counter = random.nextInt(10);
			double[] input = getTrainingInput(counter);
			double[] ideal = getIdealOutput(counter);
			n1.backpropagation(input, ideal, lernrate);
			System.out.println();
			System.out.println("Durchgang: " + (i + 1));
			n1.ausgabeOutput();
			errorIteration[i] = n1.computeError(ideal);
		}
	}

	// Ausführung von einem Testdurchlauf
	public static void runTesting(int iteration, NeuronalesNetz n1) {
		double[] errorIteration = new double[iteration];
	}

	// Trainingsdatensätze aufbereiten
	public static double[] getTrainingInput(int counter) {
		// Zahl zwischen 1 - 3 erzeugen
		Random random = new Random();
		int auswahl = random.nextInt(3) + 1;
		String filepath = "img/trainingdata/";
		// Ziffern 0 - 9
		switch (counter % 10) {
			case 0:
				filepath = filepath + 0 + auswahl + ".png";
				return getImage(filepath);
			case 1:
				filepath = filepath + 1 + auswahl + ".png";
				return getImage(filepath);
			case 2:
				filepath = filepath + 2 + auswahl + ".png";
				return getImage(filepath);
			case 3:
				filepath = filepath + 3 + auswahl + ".png";
				return getImage(filepath);
			case 4:
				filepath = filepath + 4 + auswahl + ".png";
				return getImage(filepath);
			case 5:
				filepath = filepath + 5 + auswahl + ".png";
				return getImage(filepath);
			case 6:
				filepath = filepath + 6 + auswahl + ".png";
				return getImage(filepath);
			case 7:
				filepath = filepath + 7 + auswahl + ".png";
				return getImage(filepath);
			case 8:
				filepath = filepath + 8 + auswahl + ".png";
				return getImage(filepath);
			case 9:
				filepath = filepath + 9 + auswahl + ".png";
				return getImage(filepath);
			default:
				throw new RuntimeException("Kein passender Dateipfad!");
		}
	}

	// Zurückgeben der eigentlichen Ziffern, dem IdealOutput für einen Datensatz (Bild)
	public static double[] getIdealOutput(int counter) {
		double[] ideal = new double[10];
		// Setzen der Stelle (counter % 10) des eigentlichen Ziffernwerts (0 - 9) auf 1
		ideal[counter % 10] = 1;
		return ideal;
	}

	// Zurückgeben der Input Werte für ein beliebiges Bild, definiert in filepath
	public static double[] getImage(String filepath) {
		// Input Array mit der Anzahl der maximalen Pixel in einem Bild
		double[] input = new double[PNGHelper.MAX_HEIGHT * PNGHelper.MAX_WIDTH];
		try {
			// Einlesen des Bilds
			BufferedImage image1 = ImageIO.read(new File(filepath));
			// Konvertieren Schwarz / Weiß
			BufferedImage binaryImage1 = PNGHelper.convertToBinary(image1);
			Raster rasterIMG1 = binaryImage1.getData();
			int[][] pixelValue = PNGHelper.pixelValueToArray(rasterIMG1);
			input = PNGHelper.convertPixelArray(pixelValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
}
