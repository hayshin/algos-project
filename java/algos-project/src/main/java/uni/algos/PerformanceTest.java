package uni.algos;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.Random;
import uni.algos.trees.SplayTree;

import java.util.function.Supplier;

public class PerformanceTest {

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
        data[i++] =line;
      }
      in.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  public static void testStructure(int[] data) {
    HashTable<Integer, Integer> tree = new HashTable<>();
    // SplayTree<Integer, Integer> tree = new SplayTree<>();
    for (int value : data) {
      tree.set(value, value);
    }
  }

  public static void main(String[] args) {
    int repeats = 1;
    int[] sizes = { 25_000_000 }; // Example sizes; adjust as needed.

    for (int size : sizes) {
      try {
        int[] data = DataGenerator.sortedNums(size);
          // String[] data = DataGenerator.randomStrings(size, 3, 16);

        long totalDuration = 0;

        for (int i = 0; i < repeats; i++) {
          long startTime = System.nanoTime();
          // int[] data = readFromFile("random_nums_" + size, size);
          // String[] data = readFromFileString("random_strings_len3_16_" + size, size);
          testStructure(data);
          long endTime = System.nanoTime();
          totalDuration += (endTime - startTime);
        }

        double averageTime = totalDuration / (double) repeats / 1_000_000_000.0;

        System.out.printf("Average time for %d runs: %.6f seconds per run%n", repeats, averageTime);

      } catch (Exception e) {
        System.err.println("Error reading file: " + e.getMessage());
      }
    }
  }
}
