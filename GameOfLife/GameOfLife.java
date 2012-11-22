package GameOfLife;

import java.util.Random;

/**
 *  Implementation of Conway's Game of Life. Uses a finite board.
 *  (see <a href="http://en.wikipedia.org/wiki/Conway's_Game_of_Life">Wikipedia</a>)
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class GameOfLife
{
  private final boolean ALIVE = true;
  private final boolean DEAD = false;

  /**
   *  Pre-defined patterns
   */
  private final boolean[][][] PATTERNS = 
  {
    {
      { ALIVE, ALIVE },                     //  ##
      { ALIVE, ALIVE }                      //  ##
    },
    {
      { DEAD, ALIVE, DEAD },                //   # 
      { ALIVE, DEAD, ALIVE },               //  # #
      { DEAD, ALIVE, DEAD }                 //   #
    },
    {
      { DEAD, ALIVE, DEAD },                //   #
      { ALIVE, DEAD, ALIVE },               //  # #
      { DEAD, ALIVE, ALIVE }                //   ##
    },
    {
      { ALIVE, DEAD, ALIVE, ALIVE },        //  # ##
      { ALIVE, ALIVE, DEAD, ALIVE }         //  ## #
    },
    {
      { ALIVE, ALIVE, DEAD },               //  ##
      { ALIVE, DEAD, ALIVE },               //  # #
      { DEAD, ALIVE, ALIVE }                //   ##
    },
    {
      { ALIVE, ALIVE, DEAD, DEAD },         //  ##
      { ALIVE, DEAD, DEAD, ALIVE },         //  #  #
      { DEAD, DEAD, ALIVE, ALIVE }          //    ##
    },
    {
      { DEAD, ALIVE, ALIVE, DEAD },         //   ##
      { ALIVE, DEAD, DEAD, ALIVE },         //  #  #
      { DEAD, ALIVE, ALIVE, DEAD }          //   ##
    },
    {
      { DEAD, ALIVE, DEAD, DEAD },          //   #
      { ALIVE, DEAD, ALIVE, DEAD },         //  # #
      { DEAD, ALIVE, DEAD, ALIVE },         //   # #
      { DEAD, DEAD, ALIVE, DEAD }           //    #
    },
    {
      { DEAD, DEAD, DEAD, ALIVE, ALIVE },   //     ##
      { ALIVE, DEAD, ALIVE, DEAD, ALIVE },  //  # # #
      { ALIVE, ALIVE, DEAD, DEAD, DEAD }     //  ##
    },
    {
      { DEAD, ALIVE, DEAD, DEAD },          //   #
      { ALIVE, DEAD, ALIVE, DEAD },         //  # #
      { DEAD, ALIVE, DEAD, ALIVE },         //   # #
      { DEAD, DEAD, ALIVE, ALIVE }          //    ##
    },
    {
      { ALIVE, ALIVE, DEAD, DEAD },         //  ##
      { ALIVE, DEAD, ALIVE, DEAD },         //  # #
      { DEAD, DEAD, ALIVE, DEAD },          //    #
      { DEAD, DEAD, ALIVE, ALIVE }          //    ##
    },
    {
      { DEAD, ALIVE, DEAD, DEAD },          //   #
      { ALIVE, DEAD, ALIVE, DEAD },         //  # #
      { ALIVE, DEAD, DEAD, ALIVE },         //  #  #
      { DEAD, ALIVE, ALIVE, DEAD }          //   ##
    }
  };

  private int width;
  private int height;
  private boolean[][] grid;

  public enum LifeForms
  {
    BLOCK, TUB, BOAT, SNAKE, SHIP, AIRCRAFT_CARRIER,
    BEEHIVE, BARGE, PYTHON, EATER, LOAF
  }

  /**
   *  Create a new Game of Life with a grid of given width and height.
   *
   *  @param width  Width of grid
   *  @param height Height of grid
   */
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
    grid = new boolean[this.height][this.width];
  }

  /**
   *  Create a new Game of Life with a grid of given width and height.
   *  Creates random patterns on grid if random is true.
   *
   *  @param width  Width of grid
   *  @param height Height of grid
   *  @param random Create random patterns on grid if true
   */
  public GameOfLife(int width, int height, boolean random)
  {
    this(width, height);

    if (random)
      createRandomCells();
  }

  /**
   *  Creates random patterns on grid.
   */
  private void createRandomCells()
  {
    // Create a new random-number generator
    Random random = new Random(System.nanoTime());

    // Amount of cells to create
    int aliveCells = 25 + random.nextInt(100);

    // Get LifeForms as an array
    LifeForms lifeForms[] = LifeForms.values();

    // Create new cells at random positions
    for (int i = 0; i < aliveCells; i++)
    {
      int x = random.nextInt(width),
          y = random.nextInt(height),
          lifeForm = random.nextInt(lifeForms.length);

      createLifeForm(x, y, lifeForms[lifeForm]);

      random.setSeed(System.nanoTime());
    }
  }

  /**
   *  Returns the number of alive neigbours for the cell at the given x- and y-position.
   *
   *  @param x X-coordinate
   *  @param y Y-coordinate
   *
   *  @return  Number of neighbours surrounding the cell at the given position.
   */
  private int getAliveNeighbours(int x, int y)
  {
    int startX = x > 0 ? (x - 1) : 0,
        startY = y > 0 ? (y - 1) : 0,
        maxX = (x + 1) < width ? (x + 1) : width - 1,
        maxY = (y + 1) < height ? (y + 1) : height - 1,
        neighbours = 0;

    for (int i = startY; i <= maxY; i++)
    {
      for (int j = startX; j <= maxX; j++)
      {
        if (grid[i][j])
        {
          if (i != y || j != x)
            neighbours++;
        }
      }
    }

    return neighbours;
  }

  /**
   *  Clears the grid.
   */
  public void clearGrid()
  {
    grid = new boolean[height][width];
  }

  /**
   *  Creates the specified LifeForm on the grid, at the given x- and y-position.
   *
   *  @param x        X-coordinate
   *  @param y        Y-coordinate
   *  @param lifeForm LifeForm to create
   */
  public void createLifeForm(int x, int y, LifeForms lifeForm)
  {
    //System.out.println("Creating " + lifeForm.name());
    boolean[][] pattern = PATTERNS[lifeForm.ordinal()];
    int patternHeight = pattern.length,
        patternWidth = pattern[0].length;

    for (int i = 0; i < patternHeight; i++)
    {
      if ((y + i) > 0 && (y + i) < height)
      {
        for (int j = 0; j < patternWidth; j++)
        {
          if ((x + j) > 0 && (x + j) < width && pattern[i][j])
            grid[y + i][x + j] = ALIVE;
        }
      }
    }
  }

  /**
   *  Returns the grid.
   *
   *  @return The grid-array.
   */
  public boolean[][] getGrid()
  {
    return grid;
  }

  /**
   *  Returns the height of the grid.
   *
   *  @return The height of the grid.
   */
  public int getHeight()
  {
    return height;
  }

  /**
   *  Returns the width of the grid.
   *
   *  @return The width of the grid.
   */
  public int getWidth()
  {
    return width;
  }

  /**
   *  Changes the state of the cell at the given x- and y-position.
   *
   *  @param x     X-coordinate
   *  @param y     Y-coordinate
   *  @param alive Cell is alive (true) or dead (false)
   */
  public void setCellState(int x, int y, boolean alive)
  {
    if (x > 0 && x < width && y > 0 && y < height)
      grid[y][x] = alive;
  }

  /**
   *  Step one generation forward.
   */
  public void step()
  {
    boolean[][] newGrid = new boolean[height][width];

    // Iterate through all cells
    for (int y = 0; y < height; y++)
    {
      for (int x = 0; x < width; x++)
      {
        // Implement rules
        boolean cell = grid[y][x];
        int aliveNeighbours = getAliveNeighbours(x, y);

        if (cell)
        {
          if (aliveNeighbours < 2 || aliveNeighbours > 3)
            newGrid[y][x] = DEAD;
          else
            newGrid[y][x] = ALIVE;
        }
        else if (aliveNeighbours == 3)
          newGrid[y][x] = ALIVE;
      }
    }

    grid = newGrid;
  }
}
