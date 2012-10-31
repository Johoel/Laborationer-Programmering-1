package langton;

import java.awt.Point;
import java.util.HashMap;

public class LangtonsAnt
{
  private final Point[] directions =
  {
    new Point(1, 0),
    new Point(0, 1),
    new Point(-1, 0),
    new Point(0, -1)
  };

  private Point ant;                      // The ant's position
  private int direction = 0;              // The ant's direction
  private HashMap<Point, Boolean> plane;  // The plane which the ant moves on

  public LangtonsAnt()
  {
    // Create a new Map to hold coordinates for ant
    plane = new HashMap<Point, Boolean>();
  }
  
  public Point getAnt()
  {
    return ant;
  }

  public HashMap<Point, Boolean> getPlane()
  {
    return plane;
  }

  public void step()
  {
    // If the ant is on a black square
    if (plane.get(ant))
    {
      direction = (direction - 1) % 4;
      plane.put(ant, false);
    }
    else  // If the ant is on a white spot
    {
      direction = (direction + 1) % 4;
      plane.put(ant, true);
    }

    ant.translate(directions[direction].x,
                  directions[direction].y);
  }
}
