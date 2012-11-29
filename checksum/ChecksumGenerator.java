package checksum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumGenerator
{
  private MessageDigest digester;

  /**
   *  Creates a new ChecksumGenerator-object which uses the given algorithm.
   *
   *  @param algorithm algorithm to use
   */
  public ChecksumGenerator(String algorithm) throws NoSuchAlgorithmException
  {
    digester = MessageDigest.getInstance(algorithm.toUpperCase());
  }

  /**
   *  Returns the checksum for data in given input stream.
   *
   *  @param input  input stream
   *  @param length length of data
   *  @return       checksum for data in given input stream
   */
  public byte[] getChecksum(InputStream in, long length) throws IOException
  {
    DigestInputStream input = new DigestInputStream(in, digester); 

    byte[] buffer = new byte[262144];
    long bytesRead = 0; 

    while (bytesRead < length)
      bytesRead += input.read(buffer);

    return digester.digest();
  }

  /**
   *  Prints help text.
   */
  private static void printHelpText()
  {
    System.out.println("Usage:\n" +
                       "java checksum.ChecksumGenerator ALGORITHM FILE\n" +
                       "\tALGORITHM - The Hash-algorithm to use (\"SHA-1\", \"SHA-256\", \"MD5\")\n" +
                       "\tFILE - The file to hash");
  }

  /**
   *  Returns a String-representation of given checksum.
   *
   *  @param checksum checksum byte-array
   *  @return         String-representation of given checksum
   */
  public static String getChecksumString(byte[] checksum)
  {
    String checksumString = "";
    for (byte b : checksum)
      checksumString += String.format("%x", b);
      
    return checksumString;
  }

  public static void main(String[] args)
  {
    if (args.length > 1)
    {
      String algorithm = args[0],
             fileName  = args[1];

      File file = new File(fileName);

      if (file.exists())
      {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file)))
        {
          ChecksumGenerator generator = new ChecksumGenerator(algorithm);
          String checksum = getChecksumString(generator.getChecksum(in, file.length()));

          System.out.println(checksum);
        }
        catch (Exception e)
        {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
      else
      {
        System.err.printf("The file \"%s\" does not exist!\n", fileName);
      }
    }
    else
      printHelpText();
  }
}
