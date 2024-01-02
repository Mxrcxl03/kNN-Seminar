import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Bilderkennung extends NeuronalesNetz {
	private static final boolean ENABLE_OUTPUT = true;
	/**
	 * Ausführung von angegeben Trainingsdurchläufen
	 * @param iteration Anzahl der Trainingsdurchläufe
	 * @param n1 Neuronales Netz
	 * @param lernrate Lernrate bei Backpropagation (Default: 0.01)
	 */
	public void runTraining(int iteration, NeuronalesNetz n1, double lernrate) {
		// Error-Array mit der Entwicklung des Fehlers auf die Werte
		double[] errorIteration = new double[iteration];
		Random random = new Random();
		for (int i = 0; i < iteration; i++) {
			// Zahl zwischen 0 - 9 zur Auswahl der unterschiedlichen Trainingsdaten in einer zufälligen Reihenfolge
			int counter = random.nextInt(10);
			double[] input = getTrainingInput(counter);
			double[] ideal = getIdealOutput(counter);
			n1.backpropagation(input, ideal, lernrate);
			if(ENABLE_OUTPUT) {
				System.out.println("\nDurchgang: " + (i + 1));
				//System.out.println("\nDurchgang: " + (i + 1) + " - " + n1.computeError(ideal));
				n1.ausgabeOutput();
			}
			errorIteration[i] = n1.computeError(ideal);
		}
	}

	/**
	 * Ausführung von einem Testdurchlauf für eine bestimmte Ziffer.
	 * @param ziffer Ziffer auf die getestet werden soll.
	 * @param n1 Neuronales Netz
 	 */
	public void runTestingSN(int ziffer, NeuronalesNetz n1) {
		double[] ideal = getIdealOutput(ziffer);
		double[] input = getTestInput(ziffer);
		n1.backpropagation(input, ideal, 0.01);
		//n1.backpropagation(input, ideal, 0.01); //Doppelte Genauigkeit
		n1.computeNN(input);
		printOutWinner(n1.getOutputAsDoubleArray());
		//printOutTopThree(n1.getOutputAsDoubleArray());
		//printOutputToNumber(n1.getOutputAsDoubleArray());
	}

	//TODO nötig?
	public void runTestingAN(NeuronalesNetz n1) {
		for (int i = 0; i < 10; i++) {
			double[] ideal = getIdealOutput(i);
			double[] input = getTestInput(i);
			n1.backpropagation(input, ideal, 0.01);
			n1.backpropagation(input, ideal, 0.01);
			n1.backpropagation(input, ideal, 0.01);
			n1.computeNN(input);
			printOutWinner(n1.getOutputAsDoubleArray());
		}
	}

	/**
	 * Vorhandenen Trainingsdatensätze für eine Ziffer aufbereiten.
	 * @param counter Ziffer für die ein Trainingsdatensatz generiert wird.
	 * @return Input Werte für eine bestimmte Ziffer.
	 */
	public double[] getTrainingInput(int counter) {
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

	/**
	 * Vorhandenen Testdatensätze für eine Ziffer aufbereiten.
	 * @param counter Ziffer für die ein Testdatensatz generiert wird.
	 * @return Input Werte für eine bestimmte Ziffer.
	 */
	public double[] getTestInput(int counter) {
		String filepath = "img/testdata/";
		int auswahl = 4;
		// Ziffern 0 - 9 für Werte im Counter auch über 10
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

	/**
	 * Zurückgeben der eigentlichen Ziffern, dem IdealOutput für einen Datensatz (Bild)-
	 * @param counter Ziffer für die ein IdealOutput erstellt werden soll.
	 * @return Array mit idealen Werten im Format {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}.
	 */
	public double[] getIdealOutput(int counter) {
		double[] ideal = new double[10];
		// Setzen der Stelle (counter % 10) des eigentlichen Ziffernwerts (0 - 9) auf 1
		ideal[counter % 10] = 1;
		return ideal;
	}

	/**
	 * Zurückgeben der Input Werte für ein beliebiges Bild, definiert in filepath.
	 * @param filepath Dateipfad.
	 * @return Input Werte für ein Bild.
	 */
	public double[] getImage(String filepath) {
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

	/**
	 * Ausgabe der wahrscheinlichsten Ziffer
	 * @param output Werte aller OutputNeuronen
	 */
	public void printOutWinner(double[] output) {
		double winner = 0;
		int nummer = 0;
		for (int i = 0; i < output.length; i++) {
			double current = PNGHelper.rundeZahl(output[i], 9);
			if(current > winner) {
				winner = current;
				nummer = i;
			}
		}
		System.out.println("Zahl: " + nummer + " (" + winner + ")");
	}

	/**
	 * Ausgabe der drei wahrscheinlichsten Ziffer
	 * @param output Werte aller OutputNeuronen
	 */
	public void printOutTopThree(double[] output) {
		int[] topThreeIndex = new int[3];
		double[] topThreeValue = new double[3];
		for (int i = 0; i < output.length; i++) {
			int shift1 = 0;
			int shift2 = 0;
			double value1 = 0.0;
			double value2 = 0.0;
			double current = PNGHelper.rundeZahl(output[i], 9);

			// Platz 1 zuweisen, alle anderen Positionen einen Shiften
			if(current > topThreeValue[0]) {
				shift1 = topThreeIndex[0];
				shift2 = topThreeIndex[1];
				value1 = topThreeValue[0];
				value2 = topThreeValue[1];

				topThreeIndex[0] = i;
				topThreeIndex[1] = shift1;
				topThreeIndex[2] = shift2;

				topThreeValue[0] = current;
				topThreeValue[1] = value1;
				topThreeValue[2] = value2;
				continue;
			}

			// Platz 2 zuweisen, alle anderen Positionen einen Shiften
			if(current > topThreeValue[1]) {
				shift1 = topThreeIndex[1];
				value1 = topThreeValue[1];

				topThreeIndex[1] = i;
				topThreeIndex[2] = shift1;

				topThreeValue[1] = current;
				topThreeValue[2] = value1;
				continue;
			}

			// Platz 3 zuweisen
			if(current > topThreeValue[2]) {
				topThreeIndex[2] = i;
				topThreeValue[2] = current;
				continue;
			}
		}
		System.out.println("First: " + topThreeIndex[0] + " (" + PNGHelper.rundeZahl(output[topThreeIndex[0]], 9) + ")");
		System.out.println("Second: " + topThreeIndex[1] + " (" + PNGHelper.rundeZahl(output[topThreeIndex[1]], 9) + ")");
		System.out.println("Third: " + topThreeIndex[2] + " (" + PNGHelper.rundeZahl(output[topThreeIndex[2]], 9) + ")");
	}

	/**
	 * Ausgabe der Werte der Output Neuronen zu den jeweiligen Ziffern.
	 * @param output Werte der Output Neuronen.
	 */
	public void printOutputToNumber(double[] output) {
		//System.out.println("Werte \t\t\t\t\t\t  Ziffer");
		for (int i = 0; i < output.length; i++) {
			System.out.println(PNGHelper.rundeZahl(output[i], 9) + "\t\t-\t\t" + i);
		}
		System.out.println("--------------------------------------");
	}
}
