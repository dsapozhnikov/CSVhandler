import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/*
Creating an Abstract class for future scaling of the project and for making it bit closer to OOP concept ;)
sorry a for incompleteness,  my current job is bothering me while doing this project :(
 */
public abstract class AbstractCSVhandler {


   protected String inputfilename = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input\\";
   protected String outputfilename = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\output\\output.csv";
   protected String folderName = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input";

// оставил протэктэд для будущего расширения проэкта, учитывая появление новый пэкэджей и наследников в других пэкэджах
    protected BufferedWriter bw;
    protected  FileReader reader;
    protected FileReader readerForInts;  // использовал два разных ридера 1- для output.csv для вывода результата в консоль ,

                                         //другой для считывания данных из промежуточного массива strings in CSVhandler class.
    protected BufferedReader bf;
    protected FileWriter writer;
    protected BufferedReader bfForArray;

}
