package GameOfLife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import GameOfLife.GameOfLife;

public class LifePanel extends JPanel
{
  private final int BLACK = Color.BLACK.getRGB();

  private int width;
  private int height;
  private BufferedImage lifeGrid;

  public LifePanel(int width, int height)
  {
    this.width = width;
    this.height = height;

    setPreferredSize(new Dimension(4 * width, 4 * height));

    lifeGrid = createGridImage();
  }

  private BufferedImage createGridImage()
  {
    if (width > 0 && height > 0)
    {
      return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    else
      return null;
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    if (lifeGrid != null)
      g.drawImage(lifeGrid, 0, 0, width * 4, height * 4, null);

    g.dispose();
  }

  public void updateGrid(final boolean[][] grid)
  {
    if (lifeGrid != null)
    {
      Graphics g = lifeGrid.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
      g.dispose();

      for (int y = 0; y < height; y++)
      {
        for (int x = 0; x < width; x++)
        {
          if (grid[y][x])
            lifeGrid.setRGB(x, y, BLACK);
        }
      }
    }
  }
}
