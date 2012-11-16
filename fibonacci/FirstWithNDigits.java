package fibonacci;

public class FirstWithNDigits
{
  /**
   *  Finds the first number in the fibonacci sequence with the given number of digits.
   *  Note: This is a brute force-method, which means that it takes long time to run the method
   *  for n > 9.
   *
   *  @param n Number of digits
   *  @return The first number with n digits.
   */
  public static long firstWithNDigits(long n) throws IllegalArgumentException
  {
    if (n <= 0)
      throw new IllegalArgumentException("Must use a positive integer greater than 0");

    long x = 0,
         result = 0,
         digits = 1;

    while (digits < n)
    {
      result = Fibonacci.fibonacci(++x);
      digits = (long)Math.log10(result) + 1;
    }

    return result;
  }

  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try
      {
        long n = Long.parseLong(args[0]);
        System.out.printf("First with %d digits: %d\n", n, firstWithNDigits(n));
      }
      catch (NumberFormatException e)
      {
        System.err.println("Arguments must be numbers!");
      }
      catch (IllegalArgumentException e)
      {
        System.err.println(e.getMessage());
      }
    }
  }
}
