
import java.io.*;
import java.util.List;

public class CSVOut extends AbstractCSVhandler {

    public CSVOut() {

        try {

            bw = new BufferedWriter(new FileWriter(outputfilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String[]> strings;

    static String s;


    public synchronized  void writeToFile(StringBuffer output ) {
        try {
            bw.write(output + "\n");
            bw.flush();

           } catch (IOException e) {

            e.printStackTrace();
          }
    }

    public void printResult() throws IOException {
        try {

            reader = new FileReader(outputfilename);
            bf = new BufferedReader(reader);
            while ((s=bf.readLine())!=null) {

                System.out.println("Output "+s);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

