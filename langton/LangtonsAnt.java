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
  private HashMap<Point, Boolean> field;  // The field which the ant moves on

  public LangtonsAnt()
  {
    // Create a new Map to hold coordinates for ant
    field = new HashMap<Point, Boolean>();
  }

  public void step()
  {
    // If the ant is on a black square
    if (field.get(ant))
    {
      direction = (direction - 1) % 4;
      field.put(ant, false);
    }
    else  // If the ant is on a white spot
    {
      direction = (direction + 1) % 4;
      field.put(ant, true);
    }

    ant.translate(directions[direction].x,
                  directions[direction].y);
  }

  public static void main(String[] args)
  {
  }
}
