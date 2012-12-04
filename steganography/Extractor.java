package steganography;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import static steganography.Steganography.*;

public class Extractor
{
  private int componentsPerPixel;
  private int imageSize;
  private BufferedImage input;

  private int component   = 0;
  private int pixelOffset = 0;

  public Extractor(BufferedImage input)
  {
    this.input = input;

    componentsPerPixel = Math.min(input.getColorModel().getColorSpace().getNumComponents(), 4);
    imageSize = input.getWidth() * input.getHeight();
  }

   /**
   *  Fills the given byte array with data extracted from the image.
   *
   *  @param pixels             array of pixels
   *  @param data               data array
   */
  private void extractData(int[] pixels, byte[] data)
  {
    int dataLength = data.length;

    for (int i = 0; i < dataLength; i++)
    {
      byte currentByte = 0;

      for (int s = 0; s < 8; s += 2)
      {
        if (component > componentsPerPixel)
        {
          pixelOffset++;
          component = 0;
        }

        currentByte |= ((pixels[pixelOffset] >> (component * 8)) & 3) << s;
        component++;
      }

      data[i] = currentByte;
    }
  }

  /**
   *  Returns the length of the data stored in the image.
   *
   *  @param pixels pixels in image
   *  @return       length of data stored in image
   */
  private int extractDataLength(int[] pixels)
  {
    byte[] dataArray = new byte[4];
    extractData(pixels, dataArray);

    int dataLength = ByteBuffer.allocate(4).put(dataArray).getInt(0);

    return dataLength;
  }

  /**
   *  Extracts data from the image.
   *
   *  @return array containing the data extracted from the image.
   */
  public byte[] extractData()
  {
    component   = 0;
    pixelOffset = 0;

    int[] pixels = getPixels(input);

    int dataLength = extractDataLength(pixels);

    byte[] data = new byte[dataLength];
    extractData(pixels, data);

    return data;
  }

  public static void main(String[] args)
  {
    if (args.length > 1)
    {
      String imageName  = args[0];
      String outputFile = args[1];

      try
      {
        BufferedImage input = ImageIO.read(new File(imageName));

        if (input != null)
        {
          Extractor extractor = new Extractor(input);

          byte[] data = extractor.extractData();

          try (BufferedOutputStream out = new BufferedOutputStream(
                                          new FileOutputStream(new File(outputFile))))
          {
            out.write(data);
          }
        }
      }
      catch (Exception e)
      {
        System.err.println(e);
        e.printStackTrace();
      }
    }
  }
}
