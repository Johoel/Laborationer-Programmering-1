package langton;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

public class LangtonsAnt
{
  /*
   *  The possible directions the ant can move in.
   */
  private final Point[] directions =
  {
    new Point(-1, 0),
    new Point(0, -1),
    new Point(1, 0),
    new Point(0, 1)
  };

  private Point ant;                      // The ant's position
  private int direction = 0;              // The ant's direction
  private HashSet<Point> plane;           // The plane which the ant moves on

  /**
   *  Creates an empty plane and puts the ant in the middle.
   *
   *  @param width  the width of the plane
   *  @param height the height of the plane
   */
  public LangtonsAnt(int width, int height)
  {
    plane = new HashSet<Point>();
    ant = new Point(width / 2, height / 2);
  }

  /**
   *  Creates a plane. If randomizePlane is true, cells will be
   *  set at random to either black or white. If randomStart is true, the ant's starting
   *  position will be random.
   *
   *  @param width          the width of the plane
   *  @param height         the height of the plane
   *  @param randomizePlane if true, randomly set the color of all cells on the plane
   *  @param randomStart    if true, the ant will be put on a random cell on the plane
   */
  public LangtonsAnt(int width, int height, boolean randomizeBoard, boolean randomStart)
  {
    this(width, height);

    if (randomizeBoard)
      randomizeBoard(width, height);

    if (randomStart)
      ant = new Point((int)(Math.random() * width),
                      (int)(Math.random() * height));
  }

  /**
   *  Sets a random number of cells to black to create noise on the plane.
   *
   *  @param width  the widht of the plane
   *  @param height the height of the plane
   */
  private void randomizeBoard(int width, int height)
  {
    Random random = new Random(System.nanoTime());

    int numberOfCells = random.nextInt((width * height) / 10);
    for (int i = 0; i < numberOfCells; i++)
    {
      plane.add(new Point(random.nextInt(width), random.nextInt(height)));

      random.setSeed(System.nanoTime());
    }
  }
  
  /**
   *  Returns the position of the ant.
   *
   *  @return the position of the ant
   *  @see    Point
   */
  public Point getAnt()
  {
    return ant;
  }

  /**
   *  Returns the plane.
   *
   *  @return the plane
   *  @see    HashMap
   */
  public HashSet<Point> getPlane()
  {
    return plane;
  }

  /**
   *  Make the ant move
   */
  public void step()
  {
    // If the ant is on a black square
    if (plane.contains(ant))
    {
      direction = (4 + (direction - 1)) % 4;
      plane.remove(ant);
    }
    else  // If the ant is on a white spot
    {
      direction = (direction + 1) % 4;
      plane.add((Point)ant.clone());
    }

    ant.translate(directions[direction].x,
                  directions[direction].y);
  }
}
