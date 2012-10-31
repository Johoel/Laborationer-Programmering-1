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
  private BufferedImage planeImage;
  private Timer timer;
  private long steps = 0;

  public LangtonGUI()
  {
    super("Lagton's Ant");

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 600);
    setResizable(false);

    langton = new LangtonsAnt(800, 600);

    planeImage = createPlane(800, 600);

    timer = new Timer(10, this);
    timer.start();
  }

  private void clearImage(BufferedImage image)
  {
    Graphics g = image.createGraphics();

      g.setColor(Color.WHITE);
      g.fillRect(0, 0, image.getWidth(), image.getHeight());

      g.dispose();
  }

  private BufferedImage createPlane(int width, int height)
  {
    if (width <= 0 && height <= 0)
      return null;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    clearImage(image);

    return image;
  }

  public void actionPerformed(ActionEvent e)
  {
    HashSet<Point> plane = langton.getPlane();
    Point ant = langton.getAnt();

    clearImage(planeImage);

    Iterator<Point> planeIterator = plane.iterator();

    while (planeIterator.hasNext())
    {
      Point currentPoint = planeIterator.next();
      
      if (currentPoint.x > 0 && currentPoint.x < 800 &&
          currentPoint.y > 0 && currentPoint.y < 600)
      {
        planeImage.setRGB(currentPoint.x, currentPoint.y, BLACK);
      }
    }

    planeImage.setRGB(ant.x, ant.y, RED);

    steps++;
    setTitle("Langton's Ant (" + steps + ")");

    if (steps % 100 == 0)
      repaint();

    langton.step();
  }

  public void paint(Graphics g)
  {
    super.paint(g);

    if (planeImage != null)
      g.drawImage(planeImage, 0, 0, null);
  }

  public static void main(String[] args)
  {
    LangtonGUI gui = new LangtonGUI();
    gui.setVisible(true);
  }
}
