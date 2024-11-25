package uni.algos;

import java.util.Map;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class PerfTest {

  // avl int random 1000 10
  public static void main(String[] args) throws Exception {
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

      runPerf(args);
      for (int i = 0; i < repeats; i++) {
        insertToMap(data, map);
      }
    } else {
      var map = Maps.strMaps().get(treeName);
      String[] data = switch (dataName) {
        case "short" -> DataGenerator.randomStrings(size, 8);
        case "diff" -> DataGenerator.randomStrings(size, 3, 16);
        case "long" -> DataGenerator.randomStrings(size, 64);
        default -> DataGenerator.randomStrings(size, 8);
      };

      runPerf(args);
      for (int i = 0; i < repeats; i++) {
        insertToMap(data, map);
      }
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

  static String getPid() {
    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    return pid;
  }

  static String[] command(String... args) {
    String[] command = { "perf", "stat", "-e",
        "cycles,instructions,cache-references,cache-misses,branches,branch-misses,task-clock,cpu-clock,duration_time,user_time,system_time",
        "-j", "-o", "tests/" + String.join("_", args) + ".json", "-p", getPid(), };
    return command;
  }

  static void runPerf(String... args) throws IOException {
    ProcessBuilder pb = new ProcessBuilder(command(args));
    pb.start();
  }
}
