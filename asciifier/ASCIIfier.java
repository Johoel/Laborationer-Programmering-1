package asciifier;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *  A program that "ASCIIfies" a given input image using a given character/symbol.
 *  It takes an input image and creates a new image where all the pixels from the input
 *  image are drawn as the given character/symbol.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class ASCIIfier
{
  private BufferedImage input;
  private BufferedImage output;
  private Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
  private FontMetrics metrics;
  private HashMap<Integer, Color> colors;

  private char lastChar;

  /**
   *  Creates a new ASCIIfier which uses the given image as input image when ASCIIfying.
   *
   *  @param image the image to ASCIIfy.
   */
  public ASCIIfier(BufferedImage image)
  {
    input = image;
    metrics = input.createGraphics().getFontMetrics(font);

    createColorMap();
  }

  /**
   *  Creates a HashMap of all colors in the input image.
   */
  private void createColorMap()
  {
    colors = new HashMap<Integer, Color>();

    if (input != null)
    {
      int height = input.getHeight(),
          width = input.getWidth();

      for (int y = 0; y < height; y++)
      {
        for (int x = 0; x < width; x++)
        {
          int color = input.getRGB(x, y);

          if (!colors.containsKey(color))
            colors.put(color, new Color(color));
        }
      }
    }
  }

  /**
   *  Creates a new BufferedImage with the right dimensions.
   *
   *  @param c the character to use as reference for image width and height
   */
  private void createOutputImage(char c)
  {
    if (input != null)
    {
      int charWidth = metrics.charWidth(c),
          charHeight = metrics.getAscent() + metrics.getDescent(),
          charSize = Math.max(charWidth, charHeight),
          width = charSize * input.getWidth(),
          height = charSize * input.getHeight();

      output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      System.out.printf("Input (%d, %d); Output (%d, %d)\n", input.getWidth(), input.getHeight(),
                                                             output.getWidth(), output.getHeight());
    }
  }

  /**
   *  Returns the processed image.
   *
   *  @return the processed image
   *  @see    BufferedImage
   */
  public BufferedImage getProcessedImage()
  {
    return output;
  }

  /**
   *  ASCIIfies the input image using the given character.
   *
   *  @param c the character to use to ASCIIfy the input image
   */
  public void process(char c)
  {
    if (output == null || lastChar != c)
    {
      createOutputImage(c);
      lastChar = c;
    }

    Graphics2D g = output.createGraphics();

      int height = input.getHeight(),
          width = input.getWidth(),
          ascent = metrics.getAscent(),
          charWidth = metrics.charWidth(c),
          charHeight = ascent + metrics.getDescent(),
          charSize = Math.max(charWidth, charHeight);

      String s = Character.toString(c);

      g.setFont(font);

      for (int y = 0; y < height; y++)
      {
        for (int x = 0; x < width; x++)
        {
          g.setColor(colors.get(input.getRGB(x, y)));
          g.drawString(s, x * charSize, y * charSize + ascent);
        }
      }

      g.dispose();
  }

  /**
   *  Changes the font to use to create the image.
   *
   *  @param font the font to use
   *  @see        Font
   */
  public void setFont(Font font)
  {
    if (font != null && input != null)
    {
      this.font = font;
      metrics = input.createGraphics().getFontMetrics(font);

      if (lastChar != '\u0000')
        createOutputImage(lastChar);
    }
  }

  /**
   *  Changes the size of the current font.
   *
   *  @param size the new font size
   */
  public void setFontSize(float size)
  {
    setFont(font.deriveFont(size));
  }

  /**
   *  Usage:
   *  java ASCIIfier IMAGE CHARACTER [FONT SIZE]
   *  
   *    IMAGE - The input image.
   *    CHARACTER - The character to use when ASCIIfying.
   *    [FONT SIZE] - Optional, change the font size
   */
  public static void main(String[] args)
  {
    if (args.length > 1)
    {
      try
      {
        BufferedImage image = ImageIO.read(new File(args[0]));

        if (image != null)
        {
          ASCIIfier asciifier = new ASCIIfier(image);

          if (args.length > 2)
          {
            float fontSize = Float.parseFloat(args[2]);
            
            asciifier.setFontSize(fontSize);
          }

          asciifier.process(args[1].charAt(0));

          ImageIO.write(asciifier.getProcessedImage(), "png", new File(args[0] + "_asciified.png"));
        }
      }
      catch (Exception e)
      {
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("Usage:");
      System.out.println("java ASCIIfier IMAGE CHARACTER [FONT SIZE]");
    }
  }
}
