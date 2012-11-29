package sortingalgorithms;

/**
 *  A simple class for running tests on sorting algorithms.
 *  
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class SortTester
{
  /**
   *  Returns the SortingAlgorithm associated with the given name.
   *
   *  @param name name of algorithm
   *  @return     the SortingAlgorithm associated with the name
   */
  private static SortingAlgorithm getSortingAlgorithm(String name)
  {
    switch (name)
    {
      case "combsort":
        return CombSort.sorter;
      case "shellsort":
        return ShellSort.sorter;
      default:
        System.err.println("The sorting algorithm you entered is not implemented!");
        return null;
    }
  }

  /**
   *  Prints help text.
   */
  private static void printHelpText()
  {
    System.out.println("Usage:\n" +
                       "java sortingalgorithms.SortTester ALGORITHM ARRAY_LENGTH TEST_COUNT\n" +
                       "\tALGORITHM - The algorithm to test\n" +
                       "\tARRAY_LENGTH - Length of arrays\n" +
                       "\tTEST_COUNT - Number of tests to perform");
  }

  public static void main(String[] args)
  {
    if (args.length > 2)
    {
      SortingAlgorithm algorithm = getSortingAlgorithm(args[0].toLowerCase());

      if (algorithm != null)
      {
        try
        {
          int length = Integer.parseInt(args[1]),
              n      = Integer.parseInt(args[2]);

          TestResult[] results = SortingTest.tester.test(algorithm, length, n);
          for (TestResult result : results)
            System.out.println(result);
        }
        catch (NumberFormatException e)
        {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
    }
    else
      printHelpText();
  }
}
