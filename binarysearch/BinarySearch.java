package binarysearch;

import java.util.Scanner;
import static java.util.Arrays.sort;

public class BinarySearch
{
  /**
   *  Fills given array with random values.
   *
   *  @param array    the array
   *  @param maxValue the maximum allowed value
   */
  private static void fillWithRandomValues(int[] array, int maxValue)
  {
    int length = array.length;

    for (int i = 0; i < length; i++)
      array[i] = (int)(Math.random() * maxValue);
  }

  /**
   *  Prints the program's help text.
   */
  private static void printHelpText()
  {
    System.out.println("Usage:\n" +
                       "java binarysearch.BinarySearch ARRAY_SIZE MAX_VALUE\n" +
                       "\tARRAY_SIZE - The size of the array\n" +
                       "\tMAX_VALUE  - The maximum value allowed in the array");
  }

  /**
   *  Performs a binary search for the given key in the array.
   *
   *  @param array the array to search in
   *  @param key   the searched value
   *  @param min   lowest index
   *  @param max   highest index
   *
   *  @return      the position of the key in the array, if found, otherwise -1.
   */
  public static int binarySearch(int[] array, int key, int min, int max)
  {
    if (max < min)
      return -1;

    int midpoint = (min + max) / 2;

    if (array[midpoint] > key)
      return binarySearch(array, key, min, midpoint - 1);
    else if (array[midpoint] < key)
      return binarySearch(array, key, midpoint + 1, max);
    else
      return midpoint;
  }

  public static void main(String[] args)
  {
    if (args.length > 1)
    {
      try
      {
        int arraySize = Integer.parseInt(args[0]),
            maxValue  = Integer.parseInt(args[1]);

        if (arraySize > 0)
        {
          int array[] = new int[arraySize];

          fillWithRandomValues(array, maxValue);
          sort(array);

          for (int value : array)
            System.out.print(value + " ");

          System.out.println("");
          
          Scanner scanner = new Scanner(System.in);

          int key = 0;
          while ((key = scanner.nextInt()) > -1)
          {
            System.out.println("Found key at position: " + 
                               binarySearch(array, key, 0, arraySize - 1));
          }
        }
        else
          System.err.println("Array size can not be negative!");
      }
      catch (Exception e)
      {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    else
      printHelpText();
  }
}
