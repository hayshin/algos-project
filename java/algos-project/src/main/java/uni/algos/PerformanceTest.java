package uni.algos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import uni.algos.trees.*;
import uni.algos.hash_table.HashTable;
import uni.algos.hash_table.HashTableOpenAddressing;

public class PerformanceTest {
  public static void testStructure(int[] data, Map<Integer, Integer> dataStructure) {
    for (int value : data) {
      dataStructure.put(value, 0);
    }
    System.out.println(dataStructure.size());
  }

  public static void testStructure(String[] data, Map<String, Integer> dataStructure) {
    for (String value : data) {
      dataStructure.put(value, 0);
    }
    System.out.println(dataStructure.size());
  }

  public static void main(String[] args) {
    int repeats = 1;
    int size = 25_000_000; // Example sizes; adjust as needed.

    String[] data = DataGenerator.randomStrings(size, 8, 8);
    // int[] data = DataGenerator.randomHotNums(size, 0, size);
    // String[] data = DataGenerator.randomStrings(size, 3, 16);

    long totalDuration = 0;

    for (int i = 0; i < repeats; i++) {
      long startTime = System.nanoTime();
      // int[] data = readFromFile("random_nums_" + size, size);
      // String[] data = readFromFileString("random_strings_len3_16_" + size, size);
      testStructure(data, new BTree<String, Integer>());
      long endTime = System.nanoTime();
      totalDuration += (endTime - startTime);
    }

    double averageTime = totalDuration / (double) repeats / 1_000_000_000.0;

    System.out.printf("Average time for %d runs: %.6f seconds per run%n", repeats, averageTime);
  }

  public static int[] readFromFile(String filename, int size) throws IOException {
    String DATA_DIR = "/home/hayshin/algos/data/";
    int[] data = new int[size];
    File file = new File(DATA_DIR + filename);
    int i = 0;
    try {
      final BufferedReader in = new BufferedReader(new FileReader(file));
      String line;
      while ((line = in.readLine()) != null) {
        data[i++] = Integer.parseInt(line);
      }
      in.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  public static String[] readFromFileString(String filename, int size) throws IOException {
    String DATA_DIR = "/home/hayshin/algos/data/";
    String[] data = new String[size];
    File file = new File(DATA_DIR + filename);
    int i = 0;
    try {
      final BufferedReader in = new BufferedReader(new FileReader(file));
      String line;
      while ((line = in.readLine()) != null) {
        data[i++] = line;
      }
      in.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return data;
  }

}
