package sortingalgorithms;

/**
 *  An implementation of Shellsort. It uses Marcin Ciura's gap sequence
 *  per default.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class ShellSort implements SortingAlgorithm
{
  // Marcin Ciura's gap sequence
  private int[] gaps = {701, 301, 132, 57, 23, 10, 4, 1};

  public static ShellSort sorter = new ShellSort();

  protected ShellSort()
  {
  }

  /**
   *  Returns the gap sequence.
   *
   *  @return the gap sequence
   */
  public int[] getGapSequence()
  {
    return gaps;
  }

  /**
   *  Changes the gap sequence to the given sequence.
   *
   *  @param gaps new gap sequence
   */
  public void setGapSequence(int[] gaps)
  {
    if (gaps != null && gaps.length > 0)
      this.gaps = gaps;
  }

  /**
   *  Sorts the given array using Shellsort.
   *
   *  @param array array to be sorted
   */
  public void sort(int[] array)
  {
    int length = array.length;

    for (int gap : gaps)
    {
      for (int i = 0; i < length; i++)
      {
        int temp = array[i],
            j = i;
        for (; j >= gap && array[j - gap] > temp; j -= gap)
          array[j] = array[j - gap];

        array[j] = temp;
      }
    }
  }
}
