package caesarxorstreams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CipherInputTest
{
  private static void printHelpText()
  {
    System.out.println("Usage:\n" +
                       "java caesarxorstreams.CipherInputTest OPTION FILE KEY [OUTPUT_FILE]\n" +
                       "\tOPTION: -d decipher file, -e encipher file\n" +
                       "\tFILE: File to encipher/decipher\n" +
                       "\tKEY: Key to use for enciphering/deciphering");
  }

  public static void main(String[] args)
  {
    if (args.length > 2)
    {
      String option   = args[0].toLowerCase();
      String fileName = args[1];

      boolean encrypt = false;
      if (option.equals("-e"))
        encrypt = true;
      else if (!option.equals("-d"))
      {
        System.err.printf("'%s' is not a valid option!\n", args[0]);
        System.exit(0);
      }


      File file = new File(fileName);
      if (file.exists())
      {
        try
        {
          byte[] fileData = new byte[(int)file.length()];
          int key  = Integer.parseInt(args[2]),
              read = -1;

          // Open file
          try (BufferedInputStream in = new BufferedInputStream(
                                        new CaesarInputStream(
                                        new FileInputStream(file), key, encrypt)))
          {
            read = in.read(fileData);
          }

          // Write new file
          if (read > 0)
          {
            File newFile = new File(fileName + (encrypt ? "_enciphered" : "_deciphered"));

            System.out.printf("Writing data to \"%s\"...\n", newFile.getName());
            try (BufferedOutputStream out = new BufferedOutputStream(
                                            new FileOutputStream(newFile)))
            {
              out.write(fileData);
            }
          }
        }
        catch (Exception e)
        {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
      else
        System.err.printf("The file \"%s\" does not exist!\n", fileName);
    }
    else
      printHelpText();
  }
}
