package christmas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import static java.util.Calendar.*;

/**
 *  A program that counts down to Christmas.
 *
 *  @author  Joel Abrahamsson
 *  @version %G%
 */
public class Countdown extends JFrame
{
  private static long MILLIS_PER_DAY    = 86400000L;
  private static long MILLIS_PER_HOUR   = 3600000L;
  private static long MILLIS_PER_MINUTE = 60000L;

  private Calendar christmas;
  private JLabel label;

  /**
   *  Creates a new Coundown-object.
   */
  public Countdown()
  {
    super("Christmas countdown");

    christmas = getChristmasCalendar();

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create label
    label = new JLabel(formatTimeString(getTimeToChristmas()), JLabel.CENTER);

      label.setPreferredSize(new Dimension(320, 64));

    getContentPane().add(label);
    pack();

    // Create and start timer
    Timer timer = new Timer(250, new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        label.setText(formatTimeString(getTimeToChristmas()));
      }
    });
    timer.start();
  }

  /**
   *  Formats a string from the given time.
   *
   *  @param milliseconds time in milliseconds
   *
   *  @return             formatted string
   */
  private String formatTimeString(long milliseconds)
  {
    long days = milliseconds / MILLIS_PER_DAY;
    milliseconds %= MILLIS_PER_DAY;

    long hours = milliseconds / MILLIS_PER_HOUR;
    milliseconds %= MILLIS_PER_HOUR;

    long minutes = milliseconds / MILLIS_PER_MINUTE;
    milliseconds %= MILLIS_PER_MINUTE;

    long seconds = milliseconds / 1000;

    return String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);
  }

  /**
   *  Returns a Calendar representing Christmas (December 24th) the current year.
   *
   *  @return Calendar-object representing Christmas
   */
  private Calendar getChristmasCalendar()
  {
    Calendar christmas = Calendar.getInstance();

    christmas.set(Calendar.MONTH, 12);
    christmas.set(Calendar.DAY_OF_MONTH, 24);

    return christmas;
  }

  /**
   *  Returns the time left to Christmas (in milliseconds).
   *
   *  @return time left to Christmas (in milliseconds)
   */
  private long getTimeToChristmas()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(christmas.getTimeInMillis() - calendar.getTimeInMillis());

    calendar.set(MONTH, 0);

    return calendar.getTimeInMillis();
  }

  public static void main(String[] args)
  {
    Countdown countdown = new Countdown();
    countdown.setVisible(true);
  }
}
