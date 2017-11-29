
import java.io.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
Создаем основной класс для обработки операций входных данных
часть кода могла бы быть вынесена в отдельне классы, не успел реализовать некоорые вещи связанные с приведением к "нормально" ООП
получилось немного мусорно...
 */
public class CSVhandler extends AbstractCSVhandler {

    static GlobalArray globalArray;
// метод для внесения в список всех файлов в указанной директории

    public  ArrayList<File> listFilesFromFolder(final File folder) {
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return files;

    }
// основной метод обработки входных данных
    public void handleCSV() {

        ExecutorService esex = Executors.newFixedThreadPool(5);

        File f = new File(folderName);
        ArrayList<File> files = listFilesFromFolder(f);
        final CountDownLatch countDownLatch = new CountDownLatch(files.size()); // делаем защелку ( если по русски это так) для синхронизации всех потоков


        try {
            writer = new FileWriter(outputfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < files.size(); i++) {

            final int w = i;
            final String name = files.get(w).getName();

            esex.execute(new Runnable() {
                //запускаем Executerservice для распределения задач равномерно между потоками,
                // по задумке каждый поок обрабатыввет свой файл
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
                                strings.add(write);// для начала пишем данные из инпут папки в промежуточный массив для дальней обработки
                            }

                            for (int j = 0; j < strings.size(); j++) {
                                String string = strings.get(j);

                                String[] splits = string.split(",");

                                if (splits.length - 1 == 3) {  // защита от возможного выхода за пределы массива(подсмотрел на стэковерфлоу)

                                    long sessionTime = Integer.parseInt(splits[3]);
                                    long timeStamp = Integer.parseInt(splits[0]);
                                    long resultTime = timeStamp + sessionTime;


                                    sbf = new StringBuffer(string); // создал буфферб совсем неоптимально т.к каждый раз создается новвый объект
                                    // да и использование самого буффера под вопросом т.к возвращаюсь к своему вопросу - будет ли код в run  потокобезопасным
                                    // при использованиии ExecuterService , так что повторюсь any comments are welcome
                                    //

                                    if (!Calculator.compareTimes(timeStamp, resultTime)) { //сравниваем даты
                                        //если неодинаковы то значит транзакция перевалила на следующие сутки


                                        long startDay = Calculator.getTheBeginningOfTheNextDay(resultTime);//запоминаем в миллисекндах начало нового дня
                                        long before = startDay - timeStamp; // запоминаем сколько прошло от начала транзакции до начала след дня.

                                        String date = Calculator.getDateForOutPut(timeStamp); //получаем дату для печати в файл
                                        String dateAfter = Calculator.getDateForOutPut(resultTime);// получаем дату в момент кончания транзацкии на след.день

                                        long after = (sessionTime - before); //вычисляем остаточную длительность транзакции после начала нового дня
                                        globalArray.addElements(sbf.append("\n ").append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(before).append("\n ")
                                                .append(dateAfter).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(after).append("\n "));

                                    }else {// в случае если транзакция не перевалила на другие сутки просто считаемразницу между началом и окончаением транзакции
                                        // чусствую что сделал слишком сложно , наверняка есть более оптимальный путь , опять же был бы рад ответным комментариям
                                        long duration = resultTime - timeStamp;
                                        String date = Calculator.getDateForOutPut(timeStamp);
                                   // вносим в массив необходмую информацию(использовал стрингбуффер во избежаниии конкатенации)
                                        globalArray.addElements(sbf.append("\n ").append(date).append("\n ").append(timeStamp).append(", ").append(splits[1]).append(", ").append(splits[2]).append(", ").append(duration).append("\n "));
                                    }
                                }


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown(); //каждый поток отщелкивает
                }

            });

        }
        esex.shutdown();

        try {
            countDownLatch.await(); // ждем пока все потоки не завершат задачу
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }



        try {
            readerForInts.close();
            bfForArray.close();
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        globalArray.sort(); // сортируем глобальный массив  в ASC  порядке

    }

}

