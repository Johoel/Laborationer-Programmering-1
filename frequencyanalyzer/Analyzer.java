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

public class Analyzer
{
  private int bufferSize;
  private long maxValue;
  private long minValue;
  private TreeMap<Byte, Long> table = new TreeMap<Byte, Long>();

  public Analyzer(InputStream in, int bufferSize) throws IOException
  {
    this.bufferSize = bufferSize;
    analyze(in);
    setMaxAndMinValues();
  }

  public Analyzer(InputStream in) throws IOException
  {
    this(in, 10240);
  }

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

  private String createStringLine(int length)
  {
    StringBuilder line = new StringBuilder(length);
    for (int i = 0; i < length; i++)
      line.append("#");      

    return line.toString();
  }

  private void setMaxAndMinValues()
  {
    Long[] values = new Long[table.size()];
    table.values().toArray(values);
    
    Arrays.sort(values);

    minValue = values[0];
    maxValue = values[table.size() - 1];
  }

  public long getMaxValue()
  {
    return maxValue;
  }

  public long getMinValue()
  {
    return minValue;
  }

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
