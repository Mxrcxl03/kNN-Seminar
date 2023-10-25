import java.io.*;
import java.util.Scanner;

public class CSVReader {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Type in your full Path:");
        String path = scan.nextLine();
        CSV(path);
    }
    public static void CSV(String path) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null){
                String[] values = line.split(";");
                //System.out.println(line); // Zeigt alles an
                System.out.println(values[1]); // Zeigt in jeder Reihe nur die erste Ziffer an
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

