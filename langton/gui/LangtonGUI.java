package langton.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class LangtonGUI extends JFrame
{
  private BufferedImage planeImage;

  public LangtonGUI()
  {
    super("Lagton's Ant");

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 600);
    setResizable(false);

    planeImage = createPlane(800, 600);
  }

  private BufferedImage createPlane(int width, int height)
  {
    if (width <= 0 && height <= 0)
      return null;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics g = image.createGraphics();
      
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);

      g.dispose();

    return image;
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
