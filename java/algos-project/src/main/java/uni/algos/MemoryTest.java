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
      if (treeName.equals("st") && dataName.equals("sorted"))
        return;
      int[] data = switch (dataName) {
        case "sorted" -> DataGenerator.sortedNums(size);
        case "random" -> DataGenerator.randomNums(size);
        case "hot" -> DataGenerator.randomHotNums(size);
        default -> DataGenerator.sortedNums(size);
      };
      var map = Maps.intMap(treeName);
      measureMemory(data, map);
      long sum = 0;
      for (int i = 0; i < repeats; i++) {
        map = Maps.intMap(treeName);
        sum += measureMemory(data, map);
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
      measureMemory(data, map);
      long sum = 0;
      for (int i = 0; i < repeats; i++) {
        map = Maps.strMap(treeName);
        sum += measureMemory(data, map);
      }
      System.out.print(sum / repeats + " ");
    }
  }

  public static long measureMemory(int[] data, Map<Integer, Integer> map) {
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();

    long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();

    for (int key : data) {
      map.put(key, 0);
    }

    long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();

    long memoryUsedByCode = afterUsedMemory - beforeUsedMemory;
    return memoryUsedByCode / 1024;
  }

  public static long measureMemory(String[] data, Map<String, Integer> map) {
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();

    long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();

    for (String key : data) {
      map.put(key, 0);
    }

    long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();

    long memoryUsedByCode = afterUsedMemory - beforeUsedMemory;
    return memoryUsedByCode / 1024;
  }
}
