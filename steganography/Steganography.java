package steganography;

import java.awt.image.BufferedImage;

public class Steganography
{
  /**
   *  Returns all the pixels in the given image.
   *
   *  @param image the image
   *  @return      the pixels in the image
   */
  public static int[] getPixels(BufferedImage image)
  {
    int width  = image.getWidth(),
        height = image.getHeight();

    return image.getRGB(0, 0, width, height, new int[width * height], 0, width);
  }


}
