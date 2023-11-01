package csv;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        for (int a = 0; a < dataBlocks.length; a++){
            for (int b = 0; b < dataBlocks[a].length; b++){
                for (int j = 0; j < dataBlocks[a][b].length; j++)
                {System.out.println(dataBlocks[a][b][j]);}

            }
        }
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
        double[][][] dataBlocks = new double[layerConfig.size()-1][][]; // Use a list to store data blocks

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            int currentBlockRow = 0;
            int currentBlock = 0;
            double[][] currentDataBlock = new double[layerConfig.get(currentBlock)+1][layerConfig.get(currentBlock + 1)];
            while ((line = br.readLine()) != null && layerConfig.get(currentBlock + 1) != null) {
                String[] weights = line.split(";");

                if (line.equals(";;;")) {
                    System.out.println("1");

                    dataBlocks[currentBlock] = currentDataBlock;
                    currentBlockRow = 0; // Reset the row count for the next block
                    currentBlock++;
                    currentDataBlock = new double[layerConfig.get(currentBlock)+1][layerConfig.get(currentBlock + 1)];
                    dataBlocks[currentBlock] = new double[layerConfig.get(currentBlock)+1][layerConfig.get(currentBlock + 1)];
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