package caesarxorstreams;

import java.io.InputStream;
import java.io.IOException;

import static caesarxorstreams.CaesarCipher.*;

/**
 *  An input stream for encrypting/decrypting using Caesar cipher.
 */
public class CaesarInputStream extends InputStream
{
  private InputStream in;
  private int key;
  private boolean encrypt;

  /**
   *  Creates a new CaesarInputStream which encrypts/decrypts given InputStream
   *  using the given key.
   *
   *  @param in      InputStream to encrypt/decrypt
   *  @param key     encryption/decryption key
   *  @param encrypt encrypts input if true; decrypts input if false
   */
  public CaesarInputStream(InputStream in, int key, boolean encrypt) throws IOException
  {
    this.in      = in;
    this.key     = key;
    this.encrypt = encrypt;
  }

  /**
   *  Creates a new CaesarInputStream which decrypts given InputStream
   *  using the given key.
   *
   *  @param in      InputStream to encrypt/decrypt
   *  @param key     decryption key
   */
  public CaesarInputStream(InputStream in, int key) throws IOException
  {
    this(in, key, false);
  }

  @Override
  /**
   *  Reads the next byte of data from the input stream and encrypts/decrypts it.
   *
   *  @return the next byte of data, or -1 if the end of stream is reached.
   */
  public int read() throws IOException
  {
    int input = in.read();

    if (input == -1)
      return input;

    if (encrypt)
      return encipher(input, key);
    else
      return decipher(input, key);
  }

  @Override
  /**
   *  Reads some number of bytes from input stream, encrypts/decrypts them and stores them into
   *  the buffer array b. The number of bytes actually read is returned as an integer.
   *
   *  @param b the buffer into which the data is read.
   *  @return  the total number of bytes into the buffer, or -1 if there is no more data because
   *           the end of the stream has been reached.
   */
  public int read(byte[] b) throws IOException
  {
    int length = in.read(b);

    if (length > 0)
    {
      if (encrypt)
        encipher(b, length, key);
      else
        decipher(b, length, key);
    }

    return length;
  }

  @Override
  /**
   *  Reads up to len bytes of data from the input stream into an array of bytes and
   *  encrypts/decrypts the data.
   *
   *  @param b   the buffer into which the data is read.
   *  @param off the start offset in array b at which the data is written.
   *  @param len the maximum number of bytes to read.
   */
  public int read(byte[] b, int off, int len) throws IOException
  {
    int length = in.read(b, off, len);

    if (length > 0)
    {
      if (encrypt)
        encipher(b, length, key);
      else
        decipher(b, length, key);
    }

    return length;
  }

  /**
   *  Sets whether the stream should be encrypted or decrypted.
   *
   *  @param encrypt encrypts input if true; decrypts input if false
   */
  public void setEncrypt(boolean encrypt)
  {
    this.encrypt = encrypt;
  }

  /**
   *  Sets the encrytion/decryption key to the given key.
   *
   *  @param key encryption/decryption key
   */
  public void setKey(int key)
  {
    this.key = key;
  }
}
