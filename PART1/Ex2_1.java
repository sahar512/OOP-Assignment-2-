package PART1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {
    // Q.1
    private static void writeToFile(int lines, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            for (int i = 0; i < lines; i++) {
                writer.append("Hello World\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String[] createTextFiles(int n, int seed, int bound) {
        Random rand = new Random(seed);
        int k = 0;
        int numberOfLines;
        String[] names = new String[n];

        for (int i = 0; i < n; i++) {
            try {
                File f = new File("filename" + k + ".txt");
                if (f.createNewFile()) {
                    System.out.println("File created: " + f.getName());
                    numberOfLines = rand.nextInt(bound);
                    writeToFile(numberOfLines == 0 ? 1 : numberOfLines, f.getName());
                } else {
                    System.out.println("File already exists.");
                }
                names[i] = "filename" + k + ".txt";
                k++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return names;
    }


    // Q.2
    public int getNumberOfLines(String[] fileNames) {
        try {
            int lines = 0;
            for (String s : fileNames) {
                BufferedReader reader = new BufferedReader(new FileReader(s));
                while (reader.readLine() != null) lines++;
                reader.close();
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    // Q.3
    static class CounterThread extends Thread {
        int count = 0;
        String filename;

        public CounterThread(String filename) {
            this.filename = filename;
        }

        @Override
        public void run() {
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader(this.filename));
//                while (reader.readLine() != null) count++;
//                reader.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            Ex2_1 f = new Ex2_1();
            this.count = f.getNumberOfLines(new String[]{this.filename});
        }
    }


    public int getNumOfLinesThreads(String[] fileNames) {
        int lines = 0;
        List<CounterThread> threads = new ArrayList<>();
        CounterThread thread;
        for (String s : fileNames) {
            thread = new CounterThread(s);
            threads.add(thread);
            thread.start();
        }

        for (CounterThread t : threads) {
            try {
                t.join();
                lines += t.count;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return lines;
    }


    static class CallableImp implements Callable<Integer> {
        String filename;

        public CallableImp(String filename) {
            this.filename = filename;
        }

        @Override
        public Integer call() throws Exception {
//            int count = 0;
//            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
//            while (reader.readLine() != null) count++;
//            reader.close();
            Ex2_1 f = new Ex2_1();
            return f.getNumberOfLines(new String[]{this.filename});
        }
    }


    // Q.4
    public int getNumOfLinesThreadPool(String[] fileNames) {
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);

        ArrayList<Future<Integer>> future = new ArrayList<Future<Integer>>();
        for (String s : fileNames) {
            Future<Integer> f = pool.submit(new CallableImp(s));
            future.add(f);
        }

        int lines = 0;
        try {
            for (Future<Integer> f : future) {
                lines += f.get();
            }
            pool.shutdown();
            return lines;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
//

        createTextFiles(100, 1, 100);
        Ex2_1 f = new Ex2_1();
        long start0 = System.currentTimeMillis();
        System.out.println(f.getNumberOfLines(new String[100]));
        long end0 = System.currentTimeMillis();
        System.out.println("time0 is :"+(end0-start0)*0.001);

        long start1 = System.currentTimeMillis();
        System.out.println(f.getNumOfLinesThreads(new String[100]));
        long end1 = System.currentTimeMillis();
        System.out.println("time1thread is :"+(end1-start1)*0.001);

        long start2 = System.currentTimeMillis();
        System.out.println(f.getNumOfLinesThreadPool(new String[100]));
        long end2 = System.currentTimeMillis();
        System.out.println("time2 is :"+(end2-start2)*0.001);

    }
}
