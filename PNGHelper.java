import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class PNGHelper extends Bilderkennung {
	// Festlegen der maximalen Pixelanzahl unserer Bilder in unseren Datensätzen
	public static final int MAX_HEIGHT = 32;
	public static final int MAX_WIDTH = 32;

	// Bild in ein bitonales (binary) Bild umwandeln mit nur Werten 0 oder 1
	public static BufferedImage convertToBinary(BufferedImage image) {
		// Höhe und Breite des Bild auslesen
		int width = image.getWidth();
		int height = image.getHeight();

		// Neues Bitonales Bild erstellen d.h. nur 2 Farbwerte (0, 1)
		BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		// Durchlaufe jedes Pixel des Graustufenbildes und Zuweisung eines neuen Wert (0, 1)
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				// Lese den Graustufenwert des Pixels
				int grayValue = new Color(image.getRGB(y, x)).getRed();
				// Setze den Farbwert des Pixels im binären Bild basierend ob größer 128 oder kleiner
				int binaryValue = (grayValue >= 128) ? 0 : 255; // 0 für Schwarz, 255 für Weiß
				int rgb = new Color(binaryValue, binaryValue, binaryValue).getRGB();
				binaryImage.setRGB(y, x, rgb);
			}
		}
		return binaryImage;
	}

	// Rasterwerte (Bildwerte) in ein zweidimensionales Array umwandeln
	public static int[][] pixelValueToArray(Raster bildRaster) {
		int[][] result = new int[MAX_HEIGHT][MAX_WIDTH];
		for (int y = 0; y < MAX_HEIGHT; y++) {
			for (int x = 0; x < MAX_WIDTH; x++) {
				result[y][x] = bildRaster.getSample(x, y, 0);
			}
		}
		return result;
	}

	// PixelArray in ein Array umwandeln in einen Input (InputVektor)
	public static double[] convertPixelArray(int[][] pixelArray) {
		double[] result = new double[MAX_HEIGHT * MAX_WIDTH];
		for (int x = 0; x < pixelArray.length; x++) {
			for (int y = 0; y < pixelArray[0].length; y++) {
				result[(MAX_HEIGHT * x) + y] = pixelArray[x][y];
			}
		}
		return result;
	}

	// Ausgabe der RGB-Werte eines Bildes (Blau 0-7 | Green 8-15 | Red 16-23)
	public static void printOutRGBValue(BufferedImage image) {
		for (int y = 0; y < MAX_HEIGHT; y++) {
			for (int x = 0; x < MAX_WIDTH; x++) {
				int rgb = image.getRGB(x, y);
				int red = (rgb >> 16) & 0xFF; // Maskieren von Rot in RGB
				int green = (rgb >> 8) & 0xFF; // Maskieren von Grün in RGB
				int blue = rgb & 0xFF; // Maskieren von Blau in RGB
				System.out.println(red + " - " + green + " - " + blue);
			}
		}
		System.out.println();
	}

	// Ausgabe eines kompletten PixelArrays
	public static void printOutArray(int[][] pixelArray) {
		for (int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.print(pixelArray[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	// Ausgabe eines kompletten PixelArrays mit Rasterangaben
	public static void printOutArrayWithRaster(int[][] pixelArray) {
		for (int i = 0; i < MAX_HEIGHT; i++) {
			System.out.print(i + 1 + "\t|\t");
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.print(pixelArray[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Hilfsmethode zum Runden einer Zahl mit Angabe der gewünschten Stelligkeit.
	 *
	 * @param wert Zahlenwert.
	 * @param nachkommastellen Anzahl der Nachkommastellen.
	 * @return Gerundeter Zahlenwerte auf [nachkommastellen] Nachkommastellen.
	 */
	public static double rundeZahl(double wert, int nachkommastellen) {
		int stellen = (int) Math.pow(10, nachkommastellen);
		wert *= stellen;
		wert = Math.round(wert);
		wert /= stellen;
		return wert;
	}

}
