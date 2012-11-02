package GameOfLife;

import java.util.Random;

public class GameOfLife
{
  private final boolean ALIVE = true;
  private final boolean DEAD = false;

  private int width;
  private int height;
  private boolean[][] grid;

  public GameOfLife(int width, int height)
  {
    // Use default width and height if the specified width and height are less than 0
    if (width > 0 && height > 0)
    {
      this.width = width;
      this.height = height;
    }
    else
    {
      this.width = 256;
      this.height = 256;
    }

    // Create grid with specified width and height
    grid = new boolean[this.width][this.height];
  }

  public GameOfLife(int width, int height, boolean random)
  {
    this(width, height);

    // Create a new random-number generator
    Random random = new Random(System.nanoTime());

    // Amount of cells to create
    int aliveCells = random.nextInt(10);

    // Create new cells at random positions
    for (int i = 0; i < aliveCells; i++)
    {
      int x = random.nextInt(this.width),
          y = random.nextInt(this.height);

      grid[x][y] = ALIVE;
    }
  }
}
