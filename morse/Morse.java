package morse;

import java.text.StringCharacterIterator;
import java.util.HashMap;

/**
 *  A text-to-morse (and vice-versa) converter.
 */
public class Morse
{
  private final int FIRST_CHAR_CODE     = 0x21;     // !
  private final int LESS_THAN_CHAR      = 0x3C;     // <
  private final int GREATER_THAN_CHAR   = 0x3E;     // >
  private static String[] CHAR_TO_MORSE =
  {
    "..--.", ".-..-.", ".-..-", "...-..-", ".--..", // ! " # $ %
    ".-...", ".----.", "-.--.", "-.--.-", ".._..",  // & ' ( ) *
    ".-.-.", "--..--", "-....-", ".-.-.-", "-..-.", // + , - . /
    "-----", ".----", "..---", "...--", "....-",    // 0 1 2 3 4
    ".....", "-....", "--...", "---..", "----.",    // 5 6 7 8 9
    "---...", "-.-.-.", "", "-...-", "",            // : ;   =  
    "..--..", ".--.-.", ".-", "-...", "-.-.",       // ? @ A B C
    "-..", ".", "..-.", "--.", "....",              // D E F G H
    "..", ".---", "-.-", ".-..", "--",              // I J K L M
    "-.", "---", ".--.", "--.-", ".-.",             // N O P Q R
    "...", "-", "..-", "...-", ".--",               // S T U V W
    "-..-", "-.--", "--.."                          // X Y Z
  };

  private HashMap<String, String> morseToChar;

  public static Morse morse = new Morse();

  /**
   *  Creates a new Morse-object.
   */
  protected Morse()
  {
    setupMorseCharMap();
  }

  /**
   *  Sets up the Morse-to-Char Map. It maps all used morse codes to
   *  their corresponding character code.
   */
  private void setupMorseCharMap()
  {
    int length = CHAR_TO_MORSE.length;
    morseToChar = new HashMap<String, String>(length);

    for (int i = 0; i < length; i++)
    {
      String character = Character.toString((char)(FIRST_CHAR_CODE + i));

      if (CHAR_TO_MORSE[i].length() > 0)
        morseToChar.put(CHAR_TO_MORSE[i], character);
    }
  }

  /**
   *  Converts the given string of text into morse code.
   *
   *  @param text the text to convert
   *  @return     a String of morse codes
   */
  public String toMorse(String text)
  {
    String result = "";
    int charToMorseLength = CHAR_TO_MORSE.length;

    StringCharacterIterator iterator = new StringCharacterIterator(text);

    char character = Character.toUpperCase(iterator.first());
    while (character != StringCharacterIterator.DONE)
    {
      int index = (int)character - FIRST_CHAR_CODE;

      if (index >= 0 && index < charToMorseLength)
        result += CHAR_TO_MORSE[index];

      character = Character.toUpperCase(iterator.next());

      if (character != StringCharacterIterator.DONE)
        result += " ";
    }

    return result;
  }

  /**
   *  Converts the given string of morse codes into text.
   *
   *  @param morse the morse code to convert
   *  @return      a String of text
   */
  public String toText(String morse)
  {
    String result = "";

    String[] morseCodes = morse.split("\\s+");

    for (String morseCode : morseCodes)
    {
      morseCode = morseCode.toUpperCase();

      if (morseToChar.containsKey(morseCode))
        result += morseToChar.get(morseCode) + " ";
    }

    return result;
  }

  /**
   *  Prints help text.
   */
  private static void printHelpText()
  {
    System.out.println("Usage:\n" +
                       "java morse.Morse OPTION TEXT\n" +
                       "\tOPTION - To morse = -m, to text = -t)\n" +
                       "\tTEXT   - Text/Morse code");
  }

  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      String option = args[0].toLowerCase();
      
      String result = "";
      if (option.equals("-m"))
        result = morse.toMorse(args[1]);
      else if (option.equals("-t"))
        result = morse.toText(args[1]);
      else
      {
        printHelpText();
        System.exit(0);
      }

      System.out.println(result);
    }
  }
}
