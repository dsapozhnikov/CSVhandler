
import java.io.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVhandler extends AbstractCSVhandler {

    static GlobalArray globalArray;

    public  ArrayList<File> listFilesFromFolder(final File folder) {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return files;

    }

    public void handleCSV() {

        ExecutorService esex = Executors.newFixedThreadPool(5);

        File f = new File(folderName);
        ArrayList<File> files = listFilesFromFolder(f);
        final CountDownLatch countDownLatch = new CountDownLatch(files.size());


        try {
            writer = new FileWriter(outputfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < files.size(); i++) {

            final int w = i;
            final String name = files.get(w).getName();

            esex.execute(new Runnable() {
                public void run() {

                    String write;
                    StringBuffer sbf;
                    ArrayList<String> strings;

                    try {
                        readerForInts = new FileReader(inputfilename + name);
                        reader = new FileReader(inputfilename + name);
                        bf = new BufferedReader(reader);
                        bfForArray = new BufferedReader(readerForInts);
                        strings = new ArrayList<String>();
                        globalArray = new GlobalArray();

                        try {
//
                            while ((write = bfForArray.readLine()) != null) {
                                strings.add(write);
                            }
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

                                    }
                                    long duration = resultTime - timeStamp;
                                    String date = Calculator.getDateForOutPut(timeStamp);
                                    globalArray.addElements(sbf.append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(duration));

                                }

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }

            });

        }
        esex.shutdown();

        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        globalArray.sort();

        try {
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        globalArray.sort();

    }

}

