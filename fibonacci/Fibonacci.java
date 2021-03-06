package fibonacci;

public class Fibonacci
{
  /**
   *  Returns the n-th number in the fibonacci sequence.
   *
   *  @param n Sequence number
   *  @return The n-th number in the fibonacci sequence.
   */
  public static long fibonacci(long n)
  {
    if (n < 2)
      return n;

    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try
      {
        long n = Long.parseLong(args[0]);

        System.out.println(fibonacci(n));
      }
      catch (NumberFormatException e)
      {
        System.err.printf("Given argument (%s) is not a number!", args[0]);
      }
    }
  }
}
