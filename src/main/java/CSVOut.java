import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class CSVOut {
    static FileReader reader;
    static BufferedReader bf;
    static BufferedWriter bw;

    static {
        try {
            bw = new BufferedWriter(new FileWriter("C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\output\\output.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> strings;

    static String s;


    public synchronized static void writeToFile(StringBuffer output ) {
        try {

            bw.write(output + "\n");
            bw.flush();

           } catch (IOException e) {

            e.printStackTrace();
          }

    }

    public void printResult() throws IOException {
        try {

            reader = new FileReader("C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\output\\output.csv");
            bf = new BufferedReader(reader);
            while ((s=bf.readLine())!=null) {


                System.out.println("Output "+s);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

