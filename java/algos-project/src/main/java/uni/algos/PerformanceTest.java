package uni.algos;

import java.util.Map;

import java.lang.management.ManagementFactory;

public class PerformanceTest {

  private static final String[] command = { "perf", "stat", "-e",
      "cycles,instructions,cache-references,cache-misses,branches,branch-misses,task-clock,cpu-clock,duration_time,user_time,system_time",
      "-j", "-o", "tests/avl.json", "-p", getPid(), };

  private static final ProcessBuilder pb = new ProcessBuilder(command);
  // avl int random 1000 100
  public static void main(String[] args) throws Exception {
    String treeName = args[0];
    String dataType = args[3];
    String dataName = args[4];
    int size = Integer.parseInt(args[1]);
    int repeats = Integer.parseInt(args[2]);
    
    if (dataType.equals("int")) {
      var map = Maps.intMaps().get(treeName);
      int[] data = switch (dataName) {
        case "sorted" -> data = DataGenerator.sortedNums(size);
        case "random" ->
          DataGenerator.randomNums(size);
        case "hot" ->
          DataGenerator.randomHotNums(size);
        default -> DataGenerator.sortedNums(size);
      };

      pb.start();

      for (int i = 0; i < repeats; i++) {
        insertToMap(data, map);
      }
    } else {
      var map = Maps.strMaps().get(treeName);
      String[] data = switch (dataName) {
        case "short" -> data = DataGenerator.randomStrings(size, 8);
        case "long" ->
          DataGenerator.randomStrings(size, 64);
        case "diff" ->
          DataGenerator.randomStrings(size, 3, 16);
        default -> DataGenerator.randomStrings(size, 8);
      };

      pb.start();

      for (int i = 0; i < repeats; i++) {
        insertToMap(data, map);
      }
    }
    // var data = DataGenerator.randomStrings(size, 8);
    // var data = DataGenerator.randomStrings(size, 64);
    // var data = DataGenerator.randomStrings(size, 3, 16);

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

  static String getPid() {
    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    return pid;
  }
}
