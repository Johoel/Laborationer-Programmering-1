package frequencyanalyzer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Iterator;

/**
 *  A simple frequency analyzer.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class Analyzer
{
  private int bufferSize;
  private long maxValue;
  private long minValue;
  private TreeMap<Byte, Long> table = new TreeMap<Byte, Long>();

  /**
   *  Creates a new Analyzer-object which analyze the given input stream.
   *  The buffer size is specified by bufferSize.
   *
   *  @param in         input stream
   *  @param bufferSize desired buffer size
   */
  public Analyzer(InputStream in, int bufferSize) throws IOException
  {
    this.bufferSize = bufferSize;
    analyze(in);
    setMaxAndMinValues();
  }

  /**
   *  Creates a new Analyzer-object which analyze the given input stream.
   *  Uses the default buffer size of 1kb.
   *
   *  @param in         input stream
   *  @param bufferSize desired buffer size
   */
  public Analyzer(InputStream in) throws IOException
  {
    this(in, 10240);
  }

  /**
   *  Analyze the given input stream.
   *
   *  @param in input stream
   */
  private void analyze(InputStream in) throws IOException
  {
    byte[] buffer = new byte[bufferSize];

    int bytesRead = in.read(buffer);
    for (int i = 0; i < bytesRead; i++)
    {
      long currentValue = 1;

      if (table.containsKey(buffer[i]))
        currentValue = table.get(buffer[i]) + 1;

      table.put(buffer[i], currentValue);
    }
  }

  /**
   *  Creates a string of #-symbols. The number of symbols are
   *  specified by length.
   *
   *  @param length the number of symbols
   *  @return       String of #-symbols
   */
  private String createStringLine(int length)
  {
    StringBuilder line = new StringBuilder(length);
    for (int i = 0; i < length; i++)
      line.append("#");      

    return line.toString();
  }

  /**
   *  Sets the maximum and minimum values.
   */
  private void setMaxAndMinValues()
  {
    Long[] values = new Long[table.size()];
    table.values().toArray(values);
    
    Arrays.sort(values);

    minValue = values[0];
    maxValue = values[table.size() - 1];
  }

  /**
   *  Returns the maximum value.
   *  
   *  @return the maximum value
   */
  public long getMaxValue()
  {
    return maxValue;
  }

  /**
   *  Returns the minimum value.
   *
   *  @return the minimum value
   */
  public long getMinValue()
  {
    return minValue;
  }

  /**
   *  Prints the analysis to given PrintStream.
   *
   *  @param out       output stream
   *  @param maxLength the max length of output
   */
  public void printAnalysis(PrintStream out, int maxLength) throws IOException
  {
    String line = createStringLine(maxLength);

    Iterator<Byte> keys = table.keySet().iterator();
    while (keys.hasNext())
    {
      byte key = keys.next();
      long value = table.get(key);
      int length = (int)(((double)value / (double)maxValue) * (double)maxLength);

      out.printf("[%x] %c: %s (%d)\n", key, key, line.substring(0, length), value);
    }
  }

  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(args[0]))))
      {
        Analyzer analyzer = new Analyzer(in); 
        
        System.out.printf("Max value: %d\nMin value: %d\n", analyzer.getMaxValue(),
                                                            analyzer.getMinValue());

        int length = 20;
        if (args.length > 1)
          length = Integer.parseInt(args[1]);

        analyzer.printAnalysis(System.out, length);
      }
      catch (Exception e)
      {
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
