package theknightstour;

import java.awt.Point;
import java.util.Scanner;

public class KnightsTourCLI
{
  private KnightsTour knightsTour;

  public KnightsTourCLI(int rows, int columns, int startX, int startY)
  {
    knightsTour = new KnightsTour(rows, columns, startX, startY);

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
