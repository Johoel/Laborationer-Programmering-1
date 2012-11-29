package sortingalgorithms;

/**
 *  An implementation of Comb sort. It uses the golden ratio as the shrink factor.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class CombSort implements SortingAlgorithm
{
  private final double SHRINK_FACTOR = 1.247330950103979;

  public static CombSort sorter = new CombSort();

  protected CombSort()
  {
  }

  /**
   *  Sorts the given array using Comb sort.
   *
   *  @param array array to be sorted
   */
  public void sort(int[] array)
  {
    int length = array.length,
        gap    = length;

    boolean swapped = false;

    while (gap > 1 || swapped)
    {
      gap = (int)((double)gap / SHRINK_FACTOR);
      
      if (gap < 0)
        gap = 1;

      swapped = false;
      for (int i = 0; (gap + i) < length; i++)
      {
        if ((array[i] - array[i + gap]) > 0)
        {
          CommonMethods.swap(array, i, i + gap);
          swapped = true;
        }
      }
    }
  }
}
