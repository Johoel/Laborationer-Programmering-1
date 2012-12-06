package glob;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.regex.*;

/**
 *  Implements a glob-method which returns all the files in a directory matching the given pattern.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class Glob
{
  /**
   *  Finds all files in a directory that matches the given regular expression.
   *
   *  @param directory the directory to search in
   *  @param regex     the regular expression to match against
   *  
   *  @return          an array containing the matching file names
   */
  public static String[] glob(File directory, Pattern regex) throws NotDirectoryException
  {
    if (!directory.isDirectory())
      throw new NotDirectoryException(directory.getName());

    ArrayList<String> matches = new ArrayList<String>();

    String[] fileNames = directory.list();
    for (String file : fileNames)
    {
      if (regex.matcher(file).find())
        matches.add(file);
    }
    
    return matches.toArray(new String[matches.size()]);
  }

  public static void main(String[] args)
  {
    if (args.length > 1)
    {
      try
      {
        File directory = new File(args[0]);
        Pattern regex  = Pattern.compile(args[1]);

        String[] matches = glob(directory, regex);

        System.out.printf("Matched %d files:\n", matches.length);
        for (String match : matches)
          System.out.println(match);
      }
      catch (Exception e)
      {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("Usage:\n" +
                         "java glob.Glob PATH REGEX\n" +
                         "\tPATH - Directory\n" +
                         "\tREGEX - The regular expression to match against");
    }
  }
}
