package theknightstour;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *  A simple solver for Knight's Tour using Warndorff's rule.
 *  (see <a href="http://en.wikipedia.org/wiki/Knight's_tour">Wikipedia</a>)
 *
 *  @author   Joel Abrahamsson
 *  @version  %G%
 */
public class KnightsTour
{
  private final Point[] moveRules =
  {
    new Point(2, -1),
    new Point(2, 1),
    new Point(-1, -2),
    new Point(1, -2),
    new Point(-2, -1),
    new Point(-2, 1),
    new Point(-1, 2),
    new Point(1, 2)
  };

  private final boolean VISITED = true;

  private int squaresVisited;
  private int rows;
  private int columns;
  private boolean[][] board;
  private Point knight;
  /**
   *  Creates a board with given rows and columns, and puts the knight on given position.
   *
   *  @param row      number of rows
   *  @param columns  number of columns
   *  @param startX   x-coordinate of knight
   *  @param startY   y-coordinate of knight
   */
  public KnightsTour(int rows, int columns, int startX, int startY)
  {
    // Make sure columns and rows are valid values
    this.rows = rows > 0 ? rows: 8;
    this.columns = columns > 0 ? columns : 8;

    board = new boolean[this.rows][this.columns];

    // Make sure knight is on the board
    knight = new Point(startX % this.columns, startY & this.rows);
  }

  /**
   *  Returns the possible positions the knight can move to from the given position.
   *
   *  @param x  x-coordinate of position
   *  @param y  y-coordinate of position
   *  @return   ArrayList with all possible positions
   *  @see ArrayList
   *  @see Point
   */
  private Point[] getPossibleMoves(int x, int y)
  {
    ArrayList<Point> moves = new ArrayList<Point>();
    for (Point p : moveRules)
    {
      int newX = x + p.x,
          newY = y + p.y;

      if (newX >= 0 && newX < columns && newY >= 0 && newY < rows && !board[newY][newX])
        moves.add(new Point(newX, newY));
    }

    return moves.toArray(new Point[moves.size()]);
  }

  /**
   *  Returns the number of positions the knight can move to from the given position.
   *
   *  @param x  x-coordinate of position
   *  @param y  y-coordinate of position
   *  @return   number of possible moves
   */
  private int getNumberOfPossibleMoves(int x, int y)
  {
    int moves = 0;

    for (Point p : moveRules)
    {
      int newX = x + p.x,
          newY = y + p.y;

      if (newX >= 0 && newX < columns && newY >= 0 && newY < rows && !board[newY][newX])
        moves++;
    }

    return moves;
  }

  /**
   *  Returns the board.
   *
   *  @return the board
   */
  public boolean[][] getBoard()
  {
    return board;
  }

  /**
   *  Returns the number of columns on the board.
   *
   *  @return number of columns
   */
  public int getColumns()
  {
    return columns;
  }

  /**
   *  Returns the knight's position.
   *
   *  @return the position of the knight
   *  @see Point
   */
  public Point getKnight()
  {
    return knight;
  }

  /**
   *  Returns the number of rows on the board.
   *
   *  @return number of rows
   */
  public int getRows()
  {
    return rows;
  }

  public int getSquaresVisited()
  {
    return squaresVisited;
  }

  /**
   *  Moves the knight to the square where the knight can move to as few
   *  squares as possible.
   */
  public boolean step()
  {
    board[knight.y][knight.x] = VISITED;

    Point[] possibleMoves = getPossibleMoves(knight.x, knight.y);
    Random random = new Random(System.nanoTime());

    if (possibleMoves.length > 0)
    {
      Point nextMove = possibleMoves[0];
      int numOfPossibleMoves = getNumberOfPossibleMoves(nextMove.x, nextMove.y);

      for (int i = 1, length = possibleMoves.length; i < length; i++)
      {
        int nextNumOfPossibleMoves = getNumberOfPossibleMoves(possibleMoves[i].x, possibleMoves[i].y);

        if (nextNumOfPossibleMoves < numOfPossibleMoves)
        {
          nextMove = possibleMoves[i];
          numOfPossibleMoves = nextNumOfPossibleMoves;
        }
        else if (nextNumOfPossibleMoves == numOfPossibleMoves)
        {
          nextMove = random.nextBoolean() ? possibleMoves[i] : nextMove;

          random.setSeed(System.nanoTime());
        }
      }

      knight.setLocation(nextMove);
      squaresVisited++;

      return true;
    }
    else
      return false;
  }
}
