package sortingalgorithms;

import java.util.Arrays;

/**
 *  A TestResult-object stores the results from a test.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class TestResult
{
  private String description;
  private boolean sorted;
  private long averageTime;
  private long[] times;
  private long totalTime;

  /**
   *  Creates a new TestResult-object that keeps test results.
   *
   *  @param description  a short description of the test
   *  @param sorted       the data was sorted
   *  @param times        a list of times
   */
  public TestResult(String description, boolean sorted, long... times)
  {
    this.description = description;
    this.sorted = sorted;
    this.times  = times;

    // Sort times
    Arrays.sort(this.times);

    calculateTotalTime();
    calculateAverageTime();
  }

  /**
   *  Calculates average time.
   */
  private void calculateAverageTime()
  {
    int length = times.length;
    averageTime = length > 0 ? totalTime / length : 0;
  }

  /**
   *  Calculates total time.
   */
  private void calculateTotalTime()
  {
    totalTime = 0;
    for (long time : times)
      totalTime += time;
  }

  /**
   *  Returns the average time it took to sort the data.
   *
   *  @return the average time it took to sort the data.
   */
  public long getAverageTime()
  {
    return averageTime;
  }

  /**
   *  Returns the description of the TestResult-object
   *
   *  @return description of the TestResult-object
   */
  public String getDescription()
  {
    return description;
  }

  /**
   *  Returns the max time
   *
   *  @return the max time
   */
  public long getMaxTime()
  {
    return times[times.length - 1];
  }

  /**
   *  Returns the min time
   *
   *  @return the min time
   */
  public long getMinTime()
  {
    return times[0];
  }

  /**
   *  Returns all times
   *
   *  @return all times
   */
  public long[] getSortTimes()
  {
    return times;
  }

  /**
   *  Returns true if data was sorted; otherwise false.
   *
   *  @return true if data was sorted; otherwise false.
   */
  public boolean wasSorted()
  {
    return sorted;
  }

  public String toString()
  {
    return String.format("%s\nWas sorted: %b\nAverage time: %d ns (%f s)", description,
                                                                           sorted,
                                                                           averageTime,
                                                                           (double)averageTime / 1000000000L);
  }
}
