package GameOfLife.gui;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.Timer;

import GameOfLife.GameOfLife;

public class GameOfLifeGUI extends JFrame implements ActionListener
{
  private int width, height;
  private GameOfLife life;

  private LifePanel lifePanel;
  private Timer timer;

  /**
   *  Creates a window of given width and height.
   *
   *  @param width  window width
   *  @param height window height
   */
  public GameOfLifeGUI(int width, int height)
  {
    super("Conway's Game of Life");

    this.width = width;
    this.height = height;

    // Change window settings
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);

    life = new GameOfLife(width, height, true);

    initComponents(getContentPane());
    pack();

    lifePanel.requestFocus();

    timer = new Timer(250, this);
  }

  /**
   *  Initialize window components.
   *
   *  @param pane The Container in which all the components will be added to.
   */
  private void initComponents(Container pane)
  {
    lifePanel = new LifePanel(width, height);

      lifePanel.addKeyListener(new KeyHandler());
      lifePanel.addMouseListener(new MouseHandler());
      lifePanel.updateGrid(life.getGrid());

    pane.add(lifePanel);
  }

  /**
   *  Update and repaint grid.
   */
  private void updateLifePanel()
  {
    lifePanel.updateGrid(life.getGrid());
    lifePanel.repaint();
  }

  /**
   *  Execute next step in the game.
   */
  public void actionPerformed(ActionEvent e)
  {
    life.step();
    updateLifePanel();
  }

  public static void main(String[] args)
  {
    int gridWidth = 128,
        gridHeight = 128;

    //  Check arguments passed into the program.
    try
    {
      if (args.length > 1)
      {
        gridWidth = Integer.parseInt(args[0]);
        gridHeight = Integer.parseInt(args[1]);
      }
      else if (args.length > 0)
      {
        gridWidth = Integer.parseInt(args[0]);
        gridHeight = gridWidth;
      }
    }
    catch (NumberFormatException e)
    {
      System.err.println("Coulnd't parse argument! Only numbers allowed!");
      e.printStackTrace();
    }

    // Create GUI
    GameOfLifeGUI gui = new GameOfLifeGUI(gridWidth, gridHeight);
    gui.setVisible(true);
  }

  /**
   *  Takes care of MouseEvents.
   */
  private class MouseHandler extends MouseAdapter
  {
    public void mousePressed(MouseEvent e)
    {
      if (e.getButton() == MouseEvent.BUTTON1)
        life.setCellState(e.getX() / 4, e.getY() / 4, true);
      else if (e.getButton() == MouseEvent.BUTTON3)
        life.setCellState(e.getX() / 4, e.getY() / 4, false);

      updateLifePanel();
    }
  }

  /**
   *  Takes care of KeyEvents.
   */
  private class KeyHandler extends KeyAdapter
  {
    public void keyReleased(KeyEvent e)
    {
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_SPACE:   // Start/Stop game
          if (timer.isRunning())
            timer.stop();
          else
            timer.start();
          break;
        case KeyEvent.VK_C:       // Clear grid
          life.clearGrid();
          
          updateLifePanel();
          break;
        case KeyEvent.VK_R:       // Create random grid
          life = new GameOfLife(width, height, true);

          updateLifePanel();
          break;
        case KeyEvent.VK_PLUS:    // Increase speed
          if (timer.getDelay() > 50)
            timer.setDelay(timer.getDelay() - 50);
          break;
        case KeyEvent.VK_MINUS:   // Decrease speed
          if (timer.getDelay() < 1000)
            timer.setDelay(timer.getDelay() + 50);
          break;
      }
    }
  }
}
