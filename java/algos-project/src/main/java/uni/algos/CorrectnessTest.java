package uni.algos;

import java.util.HashMap;
import java.util.Map;

public class CorrectnessTest {
  public static void main(String... args) {
    int[] sizes = {1024};
    for (int size : sizes) {
      for (var data : DataGenerator.getNums(size)) {
        var correctMap = correctMap(data);
        for (var entry : Maps.intMaps().entrySet()) {
          var map = entry.getValue();
          var name = entry.getKey();
          PerformanceTest.insertToMap(data, map);
          boolean isCorrect = checkMap(data, correctMap, map);
          System.out.printf("%s is correct = %b with size %d on nums\n", name, isCorrect, size);
        }
      }
      for (var data : DataGenerator.getStrings(size)) {
        var correctMap = correctMap(data);
        for (var entry : Maps.strMaps().entrySet()) {
          var map = entry.getValue();
          var name = entry.getKey();
          PerformanceTest.insertToMap(data, map);
          boolean isCorrect = checkMap(data, correctMap, map);
          System.out.printf("%s is correct = %b with size %d on strings\n", name, isCorrect, size);
        }
      }
    }
  }

  static Map<Integer, Integer> correctMap(int[] data) {
    var correctMap = new HashMap<Integer, Integer>();
    for (int key : data) {
      correctMap.put(key, 0);
    }
    return correctMap;
  }

  static Map<String, Integer> correctMap(String[] data) {
    var correctMap = new HashMap<String, Integer>();
    for (String key : data) {
      correctMap.put(key, 0);
    }
    return correctMap;
  }

  static boolean checkMap(int[] data, Map<Integer, Integer> correctMap, Map<Integer, Integer> map) {
    for (int key : correctMap.keySet()) {
      if (!map.containsKey(key)) {
        return false;
      }
    }
    return true;
  }

  static boolean checkMap(String[] data, Map<String, Integer> correctMap, Map<String, Integer> map) {
    for (String key : correctMap.keySet()) {
      if (!map.containsKey(key)) {
        return false;
      }
    }
    return true;
  }
}
