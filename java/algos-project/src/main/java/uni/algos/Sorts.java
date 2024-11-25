package uni.algos;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sorts {

  static int n = 10_000;
  static int[] a = randomArray(n);
  static {
    heapSort(a);
    // reverseArray(a);
  }

  public static void main(String[] args) throws IOException {

    for (int i = 0; i < 100; i++) {
      long startTime = System.nanoTime(); // Record start time

      // Call the method you want to measure
      init();

      long endTime = System.nanoTime(); // Record end time

      // Calculate and print execution time
      long executionTime = endTime - startTime;
      System.out.println("Execution time: " + executionTime/1_000_000 + " milliseconds");

    }
    // mergeSort(a, 0, a.length - 1);
    // quickSort(a, 0, a.length - 1);
    // System.out.println(isSorted(a));
  }

  public static void init() {
    int[] b = Arrays.copyOf(a, n);
    bubbleSort(b, n);
    // quickSort(b, 0, b.length - 1);
    // heapSort(b);
    // mergeSort(b, 0, b.length - 1);
    // java.util.Arrays.sort(a);
  }

  static void reverseArray(int[] arr) {
    int left = 0;
    int right = arr.length - 1;

    while (left < right) {
      swap(arr, left, right);
      left++;
      right--;
    }
  }

  static int[] randomArray(int size) {
    int[] array = new int[size];
    Random random = new Random(123);
    for (int i = 0; i < size; i++) {
      array[i] = random.nextInt(1000) + 1;
    }
    return array;
  }

  static int[] sortedArray(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = i;
    }
    return array;
  }

  static void bubbleSort(int[] a, int n) {
    int i, j;
    boolean swapped;
    for (i = n - 1; i > 0; i--) {
      swapped = false;
      for (j = 0; j < i; j++) {
        if (a[j] > a[j + 1]) {
          swap(a, j, j + 1);
          swapped = true;
        }
      }
      if (!swapped)
        return;
    }
  }

  static void insertionSort(int[] a) {
    for (int i = 1; i < a.length; i++) {
      for (int j = i; j > 0 && a[j - 1] > a[j]; swap(a, j, --j)) {
      }
    }
  }

  static void selectionSort(int[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      int iMin = i;
      for (int j = i + 1; j < a.length; j++) {
        if (a[j] < a[iMin]) {
          iMin = j;
        }
      }
      if (iMin != i) {
        swap(a, i, iMin);
      }
    }
  }

  static void heapSort(int arr[]) {
    int n = arr.length;
    for (int i = n / 2 - 1; i >= 0; i--)
      heapify(arr, n, i);
    for (int i = n - 1; i > 0; i--) {
      swap(arr, i, 0);
      heapify(arr, i, 0);
    }
  }

  static void heapify(int arr[], int N, int i) {
    int largest = i; // Initialize largest as root
    int l = 2 * i + 1; // left = 2*i + 1
    int r = 2 * i + 2; // right = 2*i + 2

    // If left child is larger than root
    if (l < N && arr[l] > arr[largest])
      largest = l;

    // If right child is larger than largest so far
    if (r < N && arr[r] > arr[largest])
      largest = r;

    // If largest is not root
    if (largest != i) {
      swap(arr, i, largest);

      // Recursively heapify the affected sub-tree
      heapify(arr, N, largest);
    }
  }

  static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  static int partition(int[] arr, int low, int high) {
    int mid = low + (high - low) / 2;
    int pivot = medianOfThreeXOR(arr, low, mid, high);

    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (arr[j] <= pivot) {
        i++;
        swap(arr, i, j);
      }
    }
    swap(arr, i + 1, high);
    return i + 1;
  }

  static int medianOfThree(int[] arr, int low, int mid, int high) {
    if (arr[low] > arr[mid])
      swap(arr, low, mid);
    if (arr[low] > arr[high])
      swap(arr, low, high);
    if (arr[mid] > arr[high])
      swap(arr, mid, high);
    swap(arr, mid, high);
    return arr[high];
  }

  static int medianOfThreeXOR(int[] arr, int low, int mid, int high) {
    if (arr[low] > arr[mid] ^ arr[low] > arr[mid]) return arr[low];
    if (arr[mid] < arr[low] ^ arr[mid] < arr[high]) return arr[mid];
    return arr[high];
  }
}
