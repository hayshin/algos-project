package uni.algos;

import java.util.Map;

public class MemoryTest {

  // avl int random 1000 10
  public static void main(String[] args) {
    String treeName = args[0];
    String dataType = args[1];
    String dataName = args[2];
    int size = Integer.parseInt(args[3]);
    int repeats = Integer.parseInt(args[4]);

    if (dataType.equals("int")) {
      var map = Maps.intMaps().get(treeName);
      int[] data = switch (dataName) {
        case "sorted" -> DataGenerator.sortedNums(size);
        case "random" -> DataGenerator.randomNums(size);
        case "hot" -> DataGenerator.randomHotNums(size);
        default -> DataGenerator.sortedNums(size);
      };

      for (int i = 0; i < repeats; i++) {
        measureMemory(data, map);
      }
    } else {
      var map = Maps.strMaps().get(treeName);
      String[] data = switch (dataName) {
        case "short" -> DataGenerator.randomStrings(size, 8);
        case "diff" -> DataGenerator.randomStrings(size, 3, 16);
        case "long" -> DataGenerator.randomStrings(size, 64);
        default -> DataGenerator.randomStrings(size, 8);
      };

      for (int i = 0; i < repeats; i++) {
        measureMemory(data, map);
      }
    }
  }

  public static void measureMemory(int[] data, Map<Integer, Integer> map) {
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();

    long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used Memory before execution (bytes): " + beforeUsedMemory);

    for (int key : data) {
      map.put(key, 0);
    }
    long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used Memory after execution (bytes): " + afterUsedMemory);

    long memoryUsedByCode = afterUsedMemory - beforeUsedMemory;
    System.out.println("Memory used by code (bytes): " + memoryUsedByCode);
  }

  public static void measureMemory(String[] data, Map<String, Integer> map) {
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();

    long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used Memory before execution (bytes): " + beforeUsedMemory);

    for (String key : data) {
      map.put(key, 0);
    }

    long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used Memory after execution (bytes): " + afterUsedMemory);

    long memoryUsedByCode = afterUsedMemory - beforeUsedMemory;
    System.out.println("Memory used by code (bytes): " + memoryUsedByCode);
  }
}
