import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.*;

public class PNGHelper {
	// Festelegen der maximalen Pixelanzahl unserer Bilder in unseren Datensätzen
	public static final int MAX_HEIGHT = 64;
	public static final int MAX_WIDTH = 64;

	// Bild in ein bitonales (binary) Bild umwandeln mit nur Werten 0 oder 1
	private static BufferedImage convertToBinary(BufferedImage image) {
		// Höhe und Breite des Bild auslesen
		// TODO durch MAXHEIGHT Werte ersetzen?
		int width = image.getWidth();
		int height = image.getHeight();

		// Neues Bitonales Bild erstellen d.h. nur 2 Farbwerte (0, 1)
		BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		// Durchlaufe jedes Pixel des Graustufenbildes und Zuweisung eines neuen Wert (0, 1)
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Lese den Graustufenwert des Pixels
				int grayValue = new Color(image.getRGB(j, i)).getRed();
				// Setze den Farbwert des Pixels im binären Bild basierend ob größer 128 oder kleiner
				int binaryValue = (grayValue < 128) ? 0 : 255; // 0 für Schwarz, 255 für Weiß
				int rgb = new Color(binaryValue, binaryValue, binaryValue).getRGB();
				binaryImage.setRGB(j, i, rgb);
			}
		}
		return binaryImage;
	}

	// Rasterwerte (Bildwerte) in ein zweidimensionales Array umwandeln
	public static int[][] pixelValueToArray(Raster raster) {
		int[][] result = new int[MAX_HEIGHT][MAX_WIDTH];
		for(int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				result[i][j] = raster.getSample(j, i, 0);
			}
		}
		return result;
	}

	// Ausgabe eines kompletten PixelArrays
	public static void printOutArray(int[][] pixelArray) {
		for(int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.print(pixelArray[i][j]);
				//System.out.print(pixelArray[i][j] + " "); TODO Ausgabe eventuell je nach Präfererenz anzeigen lassen
			}
			System.out.println();
		}
		System.out.println();
	}

	// Ausgabe eines kompletten PixelArrays mit Rasterangaben
	public static void printOutArrayWithRaster(int[][] pixelArray) {
		for(int i = 0; i < MAX_HEIGHT; i++) {
			System.out.print(i+1 + "\t|\t");
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.print(pixelArray[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Ausgabe eines Raster auf dem Bildschirm - 0 = Schwarz - 1 = Weiß
	public static void printOutPixelValues(Raster raster) {
		for(int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.println(raster.getSample(j, i, 0) + " - Pixel: (" + i + " | " + j + ")");
			}
		}
	}

	public static void main(String[] args) {
		try {
			String filepath1 = "img/number1_64x64.png";
			String filepath2 = "img/number1_64x64_2.png";
			BufferedImage image1 = ImageIO.read(new File(filepath1));
			BufferedImage image2 = ImageIO.read(new File(filepath2));

			// TODO mit Threshold oder ohne fest auf 128???
			BufferedImage binaryImage1 = convertToBinary(image1);
			BufferedImage binaryImage2 = convertToBinary(image2);

			Raster rasterIMG1 = binaryImage1.getData();
			Raster rasterIMG2 = binaryImage2.getData();

			int[][] arr1 = pixelValueToArray(rasterIMG1);
			int[][] arr2 = pixelValueToArray(rasterIMG2);

			printOutArray(arr1);
			printOutArray(arr2);

			//printOutArrayWithRaster(arr1);
			//printOutArrayWithRaster(arr2);

			//ImageIO.write(binaryImage, "png", new File("img/schwarz_weiss_64x64.png")); // Abspeichern in Unterordner
		} catch (FileNotFoundException file) {
			// Fehler sollte die Datei nicht gefunden werden
			file.printStackTrace();
		} catch (IOException e) {
			// Fehler beim Einlesen des Bilds
			e.printStackTrace();
		}
	}
}
