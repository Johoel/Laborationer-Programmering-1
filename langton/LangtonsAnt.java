package langton;

import java.awt.Point;
import java.util.HashSet;

public class LangtonsAnt
{
  private final Point[] directions =
  {
    new Point(0, 1),
    new Point(1, 0),
    new Point(0, -1),
    new Point(-1, 0)
  };

  private Point ant;                      // The ant's position
  private int direction = 0;              // The ant's direction
  private HashSet<Point> plane;           // The plane which the ant moves on

  public LangtonsAnt(int width, int height)
  {
    // Create a new Map to hold coordinates for ant
    plane = new HashSet<Point>();
    ant = new Point(width / 2, height / 2);
  }
  
  public Point getAnt()
  {
    return ant;
  }

  public HashSet<Point> getPlane()
  {
    return plane;
  }

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

    //System.out.printf("Ant (%d, %d), direction[%d] = (%s)\n",
                      //ant.x,
                      //ant.y,
                      //direction,
                      //directions[direction]);
  }
}
