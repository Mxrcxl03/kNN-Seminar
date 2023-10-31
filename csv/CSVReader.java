package csv;

import java.io.*;
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
        int[][][] dataBlocks = readCSV(path, layerConfig);

        // Iterieren Sie durch die Datenblöcke
        for (int i = 0; i < dataBlocks.length; i++) {
            System.out.println("Datenblock " + (i + 1) + ": ");
            for(int j = 0; j < dataBlocks[0].length; j++){
                for(int k = 0 ; k < dataBlocks[0][0].length; k++){
                    System.out.println(dataBlocks[i][j][k]);
                }
            }
        }
    }

    public static List<Integer> readLayer(String path){
        List<Integer> layerConfig = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            // Lese die erste Zeile mit der Layer-Konfiguration
            String layersInfo = br.readLine();
            String[] layers = layersInfo.split(";");
            for(int i = 1; i < layers.length; i++) {
                layerConfig.add(Integer.parseInt(layers[i]));
            }
            br.close(); // BufferedReader schließen, um Ressourcen freizugeben
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layerConfig;
    }
    public static int[][][] readCSV(String path, List<Integer> layerConfig) {
        int[][][] dataBlocks = new int[layerConfig.size()][][];
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            // Lese die erste Zeile mit der Layer-Konfiguration
            String tmp = br.readLine();
            String line = "";

            int[][] currentDataBlock = null;
            int currentBlockRow = 0;
            int k = 0;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    if (currentDataBlock != null) {
                        // Erstellen eines neuen zweidimensionalen Arrays, das nur die tatsächlich verwendeten Zeilen enthält
                        int[][] trimmedDataBlock = new int[currentBlockRow][];
                        for (int i = 0; i < currentBlockRow; i++) {
                            trimmedDataBlock[i] = currentDataBlock[i];
                        }
                        dataBlocks[k] = trimmedDataBlock;
                        k++;
                    }
                    currentDataBlock = null;
                    currentBlockRow = 0;
                } else {
                    if (currentDataBlock == null) {
                        currentDataBlock = new int[100][layerConfig.size()]; // Annahme von maximal 100 Zeilen pro Block
                    }
                    String[] values = line.split(";");
                    for (int i = 0; i < values.length; i++) {
                        currentDataBlock[currentBlockRow][i] = Integer.parseInt(values[i]);
                    }
                    currentBlockRow++;
                }
            }
            if (currentDataBlock != null) {
                // Erstellen eines neuen zweidimensionalen Arrays, das nur die tatsächlich verwendeten Zeilen enthält
                int[][] trimmedDataBlock = new int[currentBlockRow][];
                for (int i = 0; i < currentBlockRow; i++) {
                    trimmedDataBlock[i] = currentDataBlock[i];
                }
                dataBlocks[k] = trimmedDataBlock;
                k++;
            }

            br.close(); // BufferedReader schließen, um Ressourcen freizugeben
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataBlocks;
    }
}