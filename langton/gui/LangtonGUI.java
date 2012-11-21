package langton.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.Timer;

import langton.LangtonsAnt;

public class LangtonGUI extends JFrame implements ActionListener
{
  private final int BLACK = Color.BLACK.getRGB();
  private final int RED = Color.RED.getRGB();

  private LangtonsAnt langton;
  private int width;
  private int height;
  private BufferedImage planeImage;
  private Timer timer;
  private long steps = 0;


  /**
   *  Creates a window of given width and height. The width and height
   *  is used for creating the plane as well.
   *
   *  @param width          window width
   *  @param height         window height
   *  @param randomizePlane set random cells on the plane to black
   *  @param randomStart    put the ant on a random cell
   *  @see                  langton.LangtonsAnt;
   */
  public LangtonGUI(int width, int height, boolean randomizePlane, boolean randomStart)
  {
    super("Langton's Ant");

    this.width = width;
    this.height = height;

    // Change window settings
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(width, height);
    setResizable(false);

    langton = new LangtonsAnt(width, height, randomizePlane, randomStart);

    planeImage = createPlane(width, height);

    timer = new Timer(10, this);
    timer.start();
  }

  /**
   *  Clears the given image. Draws a solid white rectangle over the whole image.
   *
   *  @param image image to clear
   *  @see         BufferedImage
   */
  private void clearImage(BufferedImage image)
  {
    Graphics g = image.createGraphics();

      g.setColor(Color.WHITE);
      g.fillRect(0, 0, image.getWidth(), image.getHeight());

      g.dispose();
  }

  /**
   *  Creates a new image of given width and height and clears it.
   *
   *  @param width  image width
   *  @param hegiht image height
   *  @see          BufferedImage
   */
  private BufferedImage createPlane(int width, int height)
  {
    if (width <= 0 && height <= 0)
      return null;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    clearImage(image);

    return image;
  }

  /**
   *  Move the ant and update the image.
   */
  public void actionPerformed(ActionEvent e)
  {
    HashSet<Point> plane = langton.getPlane();
    Point ant = langton.getAnt();

    clearImage(planeImage);

    Iterator<Point> planeIterator = plane.iterator();

    while (planeIterator.hasNext())
    {
      Point currentPoint = planeIterator.next();
      
      if (currentPoint.x > 0 && currentPoint.x < width &&
          currentPoint.y > 0 && currentPoint.y < height)
      {
        planeImage.setRGB(currentPoint.x, currentPoint.y, BLACK);
      }
    }

    if (ant.x > 0 && ant.x < width &&
        ant.y > 0 && ant.y < height)
    {
      planeImage.setRGB(ant.x, ant.y, RED);
    }

    steps++;
    setTitle("Langton's Ant (" + steps + ")");

    if (steps % 100 == 0)
      repaint();

    langton.step();
  }

  /**
   *  Draw the image in the window.
   *
   *  @see Graphics
   */
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);

    if (planeImage != null)
      g.drawImage(planeImage, 0, 0, null);
  }

  /**
   *  Takes 4 arguments, all optinal.
   *
   *  Usage:
   *  java langton.gui.LangtonGUI [WIDTH] [HEIGHT] [RANDOMIZE_PLANE] [RANDOM_START]
   *
   *    [WIDTH]           - The width of the plane/window
   *    [HEIGHT]          - The height of the plane/window
   *    [RANDOMIZE_PLANE] - Create noise on plane
   *    [RANDOM_START]    - Put the ant on a random cell
   */
  public static void main(String[] args)
  {
    int width = 800,
        height = 600;

    boolean randomizePlane = false,
            randomStart = false;

    try
    {
      if (args.length > 1)
      {
        width = Integer.parseInt(args[0]);
        height = Integer.parseInt(args[1]);
      }

      if (args.length > 3)
      {
        randomizePlane = Integer.parseInt(args[2]) > 0 ? true : false;
        randomStart = Integer.parseInt(args[3]) > 0 ? true : false;
      }
    }
    catch (NumberFormatException e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    LangtonGUI gui = new LangtonGUI(width, height, randomizePlane, randomStart);
    gui.setVisible(true);
  }
}
