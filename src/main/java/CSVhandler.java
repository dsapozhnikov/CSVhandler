import com.opencsv.CSVReader;

import java.io.*;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVhandler {


    private static BufferedWriter bw;
    private static FileReader reader;
    private static FileReader readerForInts;
    private static BufferedReader bf;
    private static FileWriter writer;
    private static BufferedReader bfForArray;
    static  GlobalArray globalArray;


    public static ArrayList<File> listFilesFromFolder(final File folder) {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return files;

    }

    public static void main(String[] args) {
        final String inputfilename ="C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input\\";
        final String outputfilename = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\output\\output.csv";
        final String folderName = "C:\\Users\\dsapozhnikov\\Documents\\GitHub\\CSVhandler\\input";

        final CSVOut csvOut;

        ExecutorService esex = Executors.newFixedThreadPool(5);

        File f = new File(folderName);
        ArrayList<File>files= listFilesFromFolder(f);
        final CountDownLatch countDownLatch = new CountDownLatch(files.size());


        try {
            writer = new FileWriter(outputfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i <files.size(); i++) {

            final  int w =i;
            final String name = files.get(w).getName();

            esex.execute(new Runnable() {
                public void run() {
                  // synchronized (this) {
                        String s;
                        String forArray;
                        StringBuffer sbf;
                        ArrayList<Integer> order;
                        ArrayList<String> strings;

                        try {
                            order = new ArrayList<Integer>();
                            readerForInts = new FileReader(inputfilename + name);
                            reader = new FileReader(inputfilename + name);
                            bf = new BufferedReader(reader);
                            bfForArray = new BufferedReader(readerForInts);
                            strings = new ArrayList<String>();
                            globalArray = new GlobalArray();

                            try {
//
                                while ((forArray = bfForArray.readLine()) != null) {
                                    strings.add(forArray);
//
                                    String[] splits = forArray.split(",");
                                    if (splits.length - 1 == 3) {
                                        order.add(Integer.parseInt(splits[1].substring(4)));

                                    }
                                }

                                strings.sort(new Comparator<String>() {
                                    public int compare(String o1, String o2) {
                                        return Integer.parseInt(o1.split(",")[1].substring(4)) - Integer.parseInt(o2.split(",")[1].substring(4));
                                    }
                                });

                                for (int j = 0; j < strings.size(); j++) {
                                    System.out.println(strings.get(j));
                                }


                                for (int j = 0; j < strings.size(); j++) {
                                    String string = strings.get(j);

                                    String[] splits = string.split(",");

                                    if (splits.length - 1 == 3) {

                                        long sessionTime = Integer.parseInt(splits[3]);
                                        long timeStamp = Integer.parseInt(splits[0]);
                                        //         System.out.println("Session starts at "+timeStamp);
                                        long resultTime = timeStamp + sessionTime;
//                                    System.out.println("result "+resultTime);
//                                    System.out.println("startSession  "+timeStamp);

                                        sbf = new StringBuffer(string);
                                        if (!Calculator.compareTimes(timeStamp, resultTime)) {

                                            // System.out.println("START  "+Calculator.getTheBeginningOfTheNextDay(resultTime));
                                            long startDay = Calculator.getTheBeginningOfTheNextDay(resultTime);
                                            long before = startDay - timeStamp;
                                            String date = Calculator.getDateForOutPut(timeStamp);
                                            String dateAfter = Calculator.getDateForOutPut(resultTime);

                                            long after = (sessionTime - before);
                                            globalArray.addElements(sbf.append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(before).append("\n ")
                                                    .append(dateAfter).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(after));

                                       //     CSVOut.writeToFile(sbf.append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(before).append("\n ")
                                       //             .append(dateAfter).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(after));

                                        }
                                            long duration = resultTime - timeStamp;
                                            String date = Calculator.getDateForOutPut(timeStamp);
                                            globalArray.addElements(sbf.append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(duration));
                                         //   CSVOut.writeToFile(sbf.append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(duration));




                                    }

                                }

                                while ((s = bf.readLine()) != null) {


//                                }else
                                    //   System.out.println(s);

                                    // CSVOut.writeToFile(s);

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
  //              }
                \\

            });


        }
        esex.shutdown();
        try {
            countDownLatch.await();
            globalArray.sort();



            csvOut = new CSVOut();
            try {
            csvOut.printResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {

            try {
                reader.close();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
