package uni.algos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.lang.management.ManagementFactory;
import uni.algos.trees.*;
import uni.algos.hash_table.HashTable;
import uni.algos.hash_table.HashTableOpenAddressing;

public class PerformanceTest {
  public static void testMap(int[] data, Map<Integer, Integer> map) {
    for (int value : data) {
      map.put(value, 0);
    }
    // System.out.println(map.size());
  }

  public static void testMap(String[] data, Map<String, Integer> map) {
    for (String value : data) {
      map.put(value, 0);
    }
    // System.out.println(map.size());
  }

  public static void main(String[] args) throws Exception {
    // int size = 1_000_000; // Example sizes; adjust as needed.

    // String[] data = DataGenerator.randomStrings(size, 8, 8);
    // int[] data = DataGenerator.randomHotNums(size, 0, size);
    // int[] data = DataGenerator.sortedNums(size);
    // String[] data = DataGenerator.randomStrings(size, 3, 16);
    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    String[] command = { "perf", "stat", "-e",
        "task-clock,cpu-clock,cycles,instructions,cache-references,cache-misses,branches,branch-misses,dummy",
        "-o", "out", "-p", pid, };

    ProcessBuilder pb = new ProcessBuilder(command);

    // Process process = pb.start();
    pb.start();
    // process.onExit().thenRun(PerformanceTest::printGrepResults);

    // Thread.sleep(1000);
    // testMap(data, new AVLTree<Integer, Integer>());

  }

  static void printGrepResults() {
    String[] command = { "cat", "out" };

    ProcessBuilder pb = new ProcessBuilder(command);

    try {
      pb.start();
    } catch (IOException e) {
      System.out.println("Cannot print results");
    }
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
