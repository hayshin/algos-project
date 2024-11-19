package uni.algos;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGenerator {

  private static final Random random = new Random(1);

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

  // Generate a list of random strings with lengths between lenMin and lenMax
  public static String[] randomStrings(int size, int lenMin, int lenMax) {
    String[] data = new String[size];
    for (int i = 0; i < size; i++) {
      int stringLength = random.nextInt(lenMax - lenMin + 1) + lenMin;
      data[i] = generateRandomString(stringLength);
    }
    return data;
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
