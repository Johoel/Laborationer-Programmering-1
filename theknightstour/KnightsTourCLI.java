package theknightstour;

import java.awt.Point;
import java.util.Scanner;

/**
 *  A simple CLI for the Knight's Tour solver.
 *  (see <a href="http://en.wikipedia.org/wiki/Knight's_tour">Wikipedia</a>)
 *
 *  @author   Joel Abrahamsson
 *  @version  %G%
 */
public class KnightsTourCLI
{
  private KnightsTour knightsTour;

  /**
   *  Creates a new board with given rows and columns, and puts the
   *  knight on given position (startX, startY).
   *
   *  @param rows     board rows
   *  @param columns  board columns
   *  @param startX   the knight's x-coordinate
   *  @param startY   the knight's y-coordinate
   */
  public KnightsTourCLI(int rows, int columns, int startX, int startY)
  {
    knightsTour = new KnightsTour(rows, columns, startX, startY);

    run();
  }

  /**
   *  Prints the board. The squares are represented as follows:
   *    
   *    - - Not visited square
   *    # - Visited square
   *    $ - The knight's position
   */
  private void printBoard()
  {
    if (knightsTour != null)
    {
      boolean[][] board = knightsTour.getBoard();

      int rows = knightsTour.getRows(),
          columns = knightsTour.getColumns();

      Point knight = knightsTour.getKnight();

      for (int y = 0; y < rows; y++)
      {
        for (int x = 0; x < columns; x++)
        {
          if (x == knight.x && y == knight.y)
            System.out.print("$");
          else
            System.out.print(board[y][x] ? "#" : "-");
        }

        System.out.println("");
      }
    }
  }

  /**
   *  Runs the program.
   */
  private void run()
  {
    Scanner scanner = new Scanner(System.in);
    char choice;

    boolean running = true;
    do
    {
      printBoard();

      String input = scanner.nextLine();
      for (int i = 0; i < input.length(); i++)
      {
        choice = input.charAt(i);

        switch (choice)
        {
          case 'n':
            knightsTour.step();
            break;
          case 'q':
            running = false;
            break;
          default:
            System.out.println("n = Step, q = Quit");
        }
      }
    }
    while (running);
  }

  /**
   *  Takes 4 arguments.
   *
   *  Usage:
   *  java theknightstour.KnightsTourCLI ROWS COLUMNS STARTX STARTY
   *    
   *    ROWS    - Number of rows
   *    COLUMNS - Number of columns
   *    STARTX  - Knight's x-coordinate
   *    STARTY  - Knigth's y-coordinate
   *    
   *  @see KnightsTourCLI
   */
  public static void main(String[] args)
  {
    if (args.length > 3)
    {
      try
      {
        int rows = Integer.parseInt(args[0]),
            columns = Integer.parseInt(args[1]),
            startX = Integer.parseInt(args[2]),
            startY = Integer.parseInt(args[3]);

        KnightsTourCLI cli = new KnightsTourCLI(rows, columns, startX, startY);
      }
      catch (NumberFormatException e)
      {
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("Usage:");
      System.out.println("java theknightstour.KnightsTourCLI ROWS COLUMS STARTX STARTY");
    }
  }
}
