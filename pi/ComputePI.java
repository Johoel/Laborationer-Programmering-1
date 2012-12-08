package pi;

/**
 *  An implementation of Gauss-Legendre's algorithm for computing Pi.
 *  It's not very useful, since this implementation uses doubles.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class ComputePI
{
  private static double GAUSS_B = 0.7071067811865475244;

  /**
   *  Computes Pi using Gauss-Legendre's algorithm. Parameter n
   *  specifies how many times to run the algorithm. The accuracy of
   *  the computed value of Pi is proportional to n.
   *
   *  @param n number of times to run the algorithm
   *  
   *  @return  the computed value of Pi
   */
  public static double gaussPI(int n)
  {
    double a     = 1.0,
           aprev = a,
           b     = GAUSS_B,
           t     = 0.25,
           p     = 1.0;

    for (int i = 0; i < n; i++)
    {
      aprev = a;
      a     = (a + b) / 2;
      b     = Math.sqrt(aprev * b);
      t     = t - (p * Math.pow(aprev - a, 2));
      p     = 2.0 * p;
    }

    return (Math.pow(a + b, 2) / (4 * t));
  }

  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try
      {
        int n = Math.abs(Integer.parseInt(args[0]));

        System.out.println(gaussPI(n));
      }
      catch (Exception e)
      {
        System.err.println(e);
        e.printStackTrace();
      }
    }
  }
}
