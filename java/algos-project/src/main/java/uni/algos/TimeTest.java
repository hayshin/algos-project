package uni.algos;

import java.util.Map;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class TimeTest {

  // avl int random 1000 10
  public static void main(String[] args) throws Exception {
    String treeName = args[0];
    String dataType = args[1];
    String dataName = args[2];
    int size = Integer.parseInt(args[3]);
    int repeats = Integer.parseInt(args[4]);

    if (dataType.equals("int")) {
      int[] data = switch (dataName) {
        case "sorted" -> DataGenerator.sortedNums(size);
        case "random" -> DataGenerator.randomNums(size);
        case "hot" -> DataGenerator.randomHotNums(size);
        default -> DataGenerator.sortedNums(size);
      };

      var map = Maps.intMap(treeName);
      insertToMap(data, map);
      long sum = 0;
      for (int i = 0; i < repeats; i++) {
        long startTime = System.nanoTime();
        selectFromMap(data, map);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        sum += elapsedTime / 1_000;
      }
      System.out.print(sum / repeats + " ");
    } else {
      String[] data = switch (dataName) {
        case "short" -> DataGenerator.randomStrings(size, 8);
        case "diff" -> DataGenerator.randomStrings(size, 3, 16);
        case "long" -> DataGenerator.randomStrings(size, 64);
        default -> DataGenerator.randomStrings(size, 8);
      };

      var map = Maps.strMap(treeName);
      insertToMap(data, map);
      long sum = 0;
      for (int i = 0; i < repeats; i++) {
        long startTime = System.nanoTime();
        selectFromMap(data, map);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        sum += elapsedTime / 1_000_000;
      }
      System.out.print(sum / repeats + " ");
    }
  }

  public static void insertToMap(int[] data, Map<Integer, Integer> map) {
    for (int key : data) {
      map.put(key, 0);
    }
  }

  public static void insertToMap(String[] data, Map<String, Integer> map) {
    for (String key : data) {
      map.put(key, 0);
    }
  }

  public static void selectFromMap(int[] data, Map<Integer, Integer> map) {
    for (int key : data) {
      map.get(key);
    }
  }

  public static void selectFromMap(String[] data, Map<String, Integer> map) {
    for (String key : data) {
      map.get(key);
    }
  }
}
