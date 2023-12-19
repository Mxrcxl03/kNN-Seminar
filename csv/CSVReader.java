package csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Bitte geben Sie den vollständigen Pfad ein:");
		String path = scan.nextLine();
		scan.close(); // Scanner schließen, um Ressourcen freizugeben
		List<Integer> layerConfig = readLayer(path);
		// Zeigen Sie die Layer-Konfiguration
        /*for (int layer : layerConfig) {
            System.out.print(layer + " ");
        }
        System.out.println();*/
		double[][][] dataBlocks = readCSV(path, layerConfig);

		// Iterieren Sie durch die Datenblöcke
		for (int a = 0; a < dataBlocks.length; a++) {
			for (int b = 0; b < dataBlocks[a].length; b++) {
				for (int j = 0; j < dataBlocks[a][b].length; j++) {
					System.out.println(dataBlocks[a][b][j]);
				}

			}
		}
	}

	// Methoden zum Erstellen einer eigenen CSV-Datei in einem angegeben Dateipfad
	public static void writeGewichteCSV(String filepath, double[][][] gewichte) {
		try {
			BufferedWriter datei = Files.newBufferedWriter(Paths.get(filepath));
			for (int i = 0; i < gewichte.length; i++) {
				for (int j = 0; j < gewichte[0].length; j++) {
					for (int k = 0; k < gewichte[0][0].length; k++) {
						//datei.write((gewichte[i][j[k]]);
						datei.write(String.valueOf(gewichte[i][j][k]) + "; ");
					}

					if ((j + 1) != gewichte[0].length) {
						datei.newLine();
					}

					/* // Bei Bedarf Abtrennung mit ;;;
					if ((j + 1) == gewichte[0].length) {
						datei.write(";;;");
					}*/
				}
			}
			datei.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Methode zum Lesen von CSV-Datei in einem eigenem Format
	public static double[][][] readCSV(String filepath, double[][][] gewichte) {
		double[][][] gewichteCopy = gewichte;
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(filepath));
			for (int i = 0; i < gewichte.length; i++) {
				for (int j = 0; j < gewichte[0].length; j++) {
					String[] zeile = reader.readLine().split("; ");
					for (int k = 0; k < gewichte[0][0].length; k++) {
						gewichteCopy[i][j][k] = Double.parseDouble(zeile[k]);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gewichteCopy;
	}

	public static List<Integer> readLayer(String path) {
		List<Integer> layerConfig = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			// Lese die erste Zeile mit der Layer-Konfiguration
			String layersInfo = br.readLine();
			String[] layers = layersInfo.split(";");
			for (int i = 1; i < layers.length; i++) {
				layerConfig.add(Integer.parseInt(layers[i]));
			}
			br.close(); // BufferedReader schließen, um Ressourcen freizugeben
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layerConfig;
	}

	public static double[][][] readCSV(String path, List<Integer> layerConfig) {
		double[][][] dataBlocks = new double[layerConfig.size() - 1][][]; // Use a list to store data blocks

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			int currentBlockRow = 0;
			int currentBlock = 0;
			double[][] currentDataBlock = new double[layerConfig.get(currentBlock) + 1][layerConfig.get(currentBlock + 1)];
			while ((line = br.readLine()) != null && layerConfig.get(currentBlock + 1) != null) {
				String[] weights = line.split(";");

				if (line.equals(";;;")) {
					System.out.println("1");

					dataBlocks[currentBlock] = currentDataBlock;
					currentBlockRow = 0; // Reset the row count for the next block
					currentBlock++;
					currentDataBlock = new double[layerConfig.get(currentBlock) + 1][layerConfig.get(currentBlock + 1)];
					dataBlocks[currentBlock] = new double[layerConfig.get(currentBlock) + 1][layerConfig.get(currentBlock + 1)];
				} else {
					// Make sure we don't exceed the limit
					double[] rowData = new double[layerConfig.get(currentBlock + 1)];
					for (int i = 0; i < weights.length; i++) {
						rowData[i] = Double.parseDouble(weights[i]);
					}
					currentDataBlock[currentBlockRow] = rowData;
					currentBlockRow++;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataBlocks;
	}
}
