package uni.algos;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGenerator {

  private static final Random random = new Random(1);
  public static final int[] sizes = { 1_024, 1_048_576, 16_777_216 };
  public static void main(String[] args ){
    
    int[] nums = randomNums(100);
    for(int num : nums){
      System.out.print(num + " ");
    }

    String[] strs = randomStrings(100, 64);
    for (String str : strs){
      System.out.println(str);
    }
  }

  public static int[][] getNums(int size) {
    int[][] nums = { sortedNums(size), randomNums(size), randomHotNums(size) };
    return nums;
  }

  public static String[][] getStrings(int size) {
    String[][] nums = { randomStrings(size, 8), randomStrings(size, 3, 16), randomStrings(size, 64) };
    return nums;
  }

  public static int[] sortedNums(int size) {
    int[] sorted = new int[size];
    for (int i = 0; i < size; i++) {
      sorted[i] = i;
    }
    return sorted;
  }

  // Generate a list of random integers between start and end (inclusive)
  public static int[] randomNums(int size, int start, int end) {
    int[] data = new int[size];
    for (int i = 0; i < size; i++) {
      data[i] = random.nextInt(end - start + 1) + start;
    }
    return data;
  }

  public static int[] randomNums(int size) {
    return randomNums(size, 0, size);
  }

  // Generate a list of random strings with lengths between lenMin and lenMax
  public static String[] randomStrings(int size, int lenMin, int lenMax) {
    String[] data = new String[size];
    for (int i = 0; i < size; i++) {
      int stringLength = random.nextInt(lenMax - lenMin + 1) + lenMin;
      data[i] = generateRandomString(stringLength);
    }
    return data;
  }

  public static String[] randomStrings(int size, int len) {
    return randomStrings(size, len, len);
  }

  private static String generateRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append((char) ('a' + random.nextInt(26))); // Random lowercase letter
    }
    return sb.toString();
  }

  public static int[] randomHotNums(int size, int start, int end) {
    int rangeSize = end - start + 1;
    int[] result = new int[size];

    int hotCount = Math.max(1, rangeSize / 10); // 10% of the unique numbers
    int hotAmount = (int) (size * 0.9); // 90% of the total size

    // Create a list of unique numbers
    List<Integer> uniqueNumbers = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      uniqueNumbers.add(i);
    }

    // Shuffle the list and select the "hot" numbers
    Collections.shuffle(uniqueNumbers);
    List<Integer> hotNumbers = uniqueNumbers.subList(0, hotCount);
    List<Integer> remainingNumbers = uniqueNumbers.subList(hotCount, uniqueNumbers.size());

    // Fill 90% of the array with hot numbers
    for (int i = 0; i < hotAmount; i++) {
      result[i] = hotNumbers.get(random.nextInt(hotNumbers.size()));
    }

    // Fill the remaining 10% of the array with other numbers
    for (int i = hotAmount; i < size; i++) {
      result[i] = remainingNumbers.get(random.nextInt(remainingNumbers.size()));
    }

    // Shuffle the final array to mix hot and non-hot numbers
    shuffleArray(result);

    return result;
  }

  public static int[] randomHotNums(int size) {
    return randomHotNums(size, 0, size);
  }

  private static void shuffleArray(int[] array) {
    for (int i = array.length - 1; i > 0; i--) {
      int index = random.nextInt(i + 1);
      // Swap elements
      int temp = array[i];
      array[i] = array[index];
      array[index] = temp;
    }
  }
}
