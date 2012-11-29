package sortingalgorithms;

/**
 *  Contains methods common to several sorting algorithms.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class CommonMethods
{
  /**
   *  Swaps the elements at the given positions in the array.
   *
   *  @param array the array containing the elements to swap
   *  @param a     the index for the first element
   *  @param b     the index for the second element
   */
  public static void swap(int[] array, int a, int b)
  {
    array[a] ^= array[b];
    array[b] =  array[a] ^ array[b];
    array[a] ^= array[b];
  }

  /**
   *  Swaps the elements at the given positions in the array.
   *
   *  @param array the array containing the elements to swap
   *  @param a     the index for the first element
   *  @param b     the index for the second element
   */
  public static<T> void swap(T[] array, int a, int b)
  {
    T temp = array[a];
    array[a] = array[b];
    array[b] = temp;
  }
}
