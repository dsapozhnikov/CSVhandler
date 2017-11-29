import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public abstract class AbstractCSVhandler {

   protected String inputfilename = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input\\";
   protected String outputfilename = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\output\\output.csv";
   protected String folderName = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input";

    protected BufferedWriter bw;
    protected  FileReader reader;
    protected FileReader readerForInts;
    protected BufferedReader bf;
    protected FileWriter writer;
    protected BufferedReader bfForArray;

}
