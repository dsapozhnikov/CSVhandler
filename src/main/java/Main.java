import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CSVOut csvOut = new CSVOut();
        CSVhandler csVhandler = new CSVhandler();

        csVhandler.handleCSV();
        CSVhandler.globalArray.writeToOutPut();
        csvOut.printResult();



    }
}
