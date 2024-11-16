package uni.algos;

import java.util.Random;

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
}
