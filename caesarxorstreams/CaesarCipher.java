package caesarxorstreams;

public class CaesarCipher
{
  /**
   *  Deciphers the input using the given key.
   *
   *  @param input the data to decipher
   *  @return      the deciphered data
   */
  public static int decipher(int input, int key)
  {
    return (input - key) % 0xFF;
  }

  /**
   *  Deciphers the byte array using the given key.
   *
   *  @param b array to decipher
   */
  public static void decipher(byte[] b, int length, int key)
  {
    for (int i = 0; i < length; i++)
      b[i] = (byte)decipher(b[i], key);
  }

  /**
   *  Enciphers the input using the given key.
   *
   *  @param input the data to encipher
   *  @return      the enciphered data
   */
  public static int encipher(int input, int key)
  {
    return (input + key) % 0xFF;
  }

  /**
   *  Enciphers the byte array using the given key.
   *
   *  @param b array to encipher
   */
  public static void encipher(byte[] b, int length, int key)
  {
    for (int i = 0; i < length; i++)
      b[i] = (byte)encipher(b[i], key);
  }
}
