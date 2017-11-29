import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class GlobalArray {
    ArrayList<StringBuffer>globalArrayList;
    CSVOut csvOut;

    public GlobalArray() {
        this.csvOut = new CSVOut();
        this.globalArrayList = new ArrayList<StringBuffer>();
    }

    public void addElements(StringBuffer str) {
      globalArrayList.add(str);
    }

    public StringBuffer getElement(int index) {

        return  globalArrayList.get(index);
    }


   public void sort() {
        globalArrayList.sort((new Comparator<StringBuffer>() {
            public int compare(StringBuffer o1, StringBuffer o2) {

                return Integer.parseInt(o1.toString().split(",")[1].substring(4)) - Integer.parseInt(o2.toString().split(",")[1].substring(4));
            }
        }));
   }
   public void writeToOutPut() {

       for (int i = 0; i <globalArrayList.size(); i++) {
        csvOut.writeToFile(getElement(i));
       }
   }
 }
