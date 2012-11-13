package fibonacci;

public class Fibonacci
{
  public static int fibonacci(int n)
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
        int n = Integer.parseInt(args[0]);

        System.out.println(fibonacci(n));
      }
      catch (NumberFormatException e)
      {
        System.err.printf("Given argument (%s) is not a number!", args[0]);
      }
    }
  }
}
