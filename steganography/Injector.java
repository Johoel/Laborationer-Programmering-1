package steganography;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import static steganography.Steganography.*;

/**
 *  Injects data into an image.
 */
public class Injector
{
  private int componentsPerPixel;
  private int imageSize;
  private int maxDataLength;
  private BufferedImage destination;

  private int component   = 0;
  private int pixelOffset = 0;

  /**
   *  Creates a new Injector-object. The given BufferedImage is the image
   *  which will be used for the data injection. Stores 2 bits of data per
   *  color component.
   *
   *  @throws ImageTooSmallException if the image is too small to hold any data
   *
   *  @param destination the destination image
   */
  public Injector(BufferedImage destination) throws ImageTooSmallException
  {
    this.destination = destination;

    componentsPerPixel = Math.min(destination.getColorModel().getNumComponents(), 4);
    imageSize = destination.getWidth() * destination.getHeight();

    maxDataLength = (int)Math.floor((imageSize * componentsPerPixel) / 4) - 4;

    if (maxDataLength < 1)
      throw new ImageTooSmallException();
  }

  /**
   *  Injects given byte array into the image.
   *
   *  @param pixels             array of pixels
   *  @param data               the data to inject
   */
  private void injectData(int[] pixels, byte[] data)
  {
    int dataLength = data.length;

    for (int i = 0; i < dataLength; i++)
    {
      int currentByte = data[i] & 0xFF;

      for (int s = 0; s < 8; s += 2)
      {
        if (component >= componentsPerPixel)
        {
          pixelOffset++;
          component = 0;
        }

        // Clear and set bits
        pixels[pixelOffset] &= ~(3 << (component * 8));
        pixels[pixelOffset] |= ((currentByte >> s) & 3) << (component * 8);

        component++;
      }
    }
  }

  /**
   *  Injects the length of the injected data in the first pixels in the image.
   *
   *  @param pixels     the pixel array
   *  @param dataLength length of data to inject
   */
  private void injectDataLength(int[] pixels, int dataLength)
  {
    byte[] data = ByteBuffer.allocate(4).putInt(dataLength).array();

    injectData(pixels, data);
  }

  /**
   *  Returns the maximum number of bytes that can be stored in the image.
   *
   *  @return maximum number of bytes that can be stored in the image
   */
  public int getMaxDataLength()
  {
    return maxDataLength;
  }

  /**
   *  Injects the given data, if it fits in the image.
   *
   *  @throws ImageTooSmallException if the image is too small to hold the data
   *
   *  @param data data to inject
   *  @return     image with data injected into it
   */
  public BufferedImage injectData(byte[] data) throws ImageTooSmallException
  {
    int dataLength = data.length;

    component   = 0;
    pixelOffset = 0;

    if (dataLength > maxDataLength)
      throw new ImageTooSmallException(); 

    int[] pixels = getPixels(destination);

    injectDataLength(pixels, dataLength);
    injectData(pixels, data);

    destination.setRGB(0, 0, destination.getWidth(), destination.getHeight(),
                       pixels, 0, destination.getWidth());

   return destination;
 }

 public static void main(String[] args)
 {
    if (args.length > 2)
    {
      String imageName  = args[0],
             dataName   = args[1],
             outputName = args[2];

      try
      {
        BufferedImage input = ImageIO.read(new File(imageName));

        if (input != null)
        {
          Injector injector = new Injector(input);
          System.out.printf("Maximum data length: %d bytes\n", injector.getMaxDataLength());

          File dataFile = new File(dataName);
          System.out.printf("File length: %d bytes\n", dataFile.length());

          byte[] data = new byte[(int)dataFile.length()];
          try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(dataFile)))
          {
            in.read(data, 0, (int)dataFile.length());
          }

          BufferedImage output = injector.injectData(data);

          String extension = outputName.substring(outputName.lastIndexOf(".") + 1);
          File outputFile = new File(outputName);
          outputFile.createNewFile();

          ImageIO.write(input, extension, outputFile);
        }
        else
          System.out.printf("Couldn't open image \"%s\"\n", imageName);
      }
      catch (Exception e)
      {
        System.err.println(e);
        e.printStackTrace();
      }
    }
 }
}
