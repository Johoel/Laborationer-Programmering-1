package caesarxorstreams;

import java.io.OutputStream;
import java.io.IOException;

import static caesarxorstreams.CaesarCipher.*;

/**
 *  An output stream for encrypting/decrypting using Caesar cipher.
 */
public class CaesarOutputStream extends OutputStream
{
  private OutputStream out;
  private int key;
  private boolean encrypt;

  /**
   *  Creates a new CaesarOutputStream which encrypts/decrypts given OutputStream
   *  using the given key.
   *
   *  @param out     OutputStream to encrypt/decrypt
   *  @param key     encryption/decryption key
   *  @param encrypt encrypts output if true; decrypts output if false
   */
  public CaesarOutputStream(OutputStream out, int key, boolean encrypt) throws IOException
  {
    this.out     = out;
    this.key     = key;
    this.encrypt = encrypt;
  }

  /**
   *  Creates a new CaesarOutputStream which decrypts given OutputStream
   *  using the given key.
   *
   *  @param out     OutputStream to encrypt/decrypt
   *  @param key     decryption key
   */
  public CaesarOutputStream(OutputStream out, int key) throws IOException
  {
    this(out, key, false);
  }

  @Override
  /**
   *  Enciphers and writes the specified byte to this output stream.
   *
   *  @param b the byte
   */
  public void write(int b) throws IOException
  {
    if (encrypt)
      out.write(encipher(b, key));
    else
      out.write(decipher(b, key));
  }

  @Override
  /**
   *  Writes b.length bytes from the specified byte array to this output stream.
   *
   *  @param b the data 
   */
  public void write(byte[] b) throws IOException
  {
    int length = b.length;

    if (length > 0)
    {
      if (encrypt)
        encipher(b, length, key);
      else
        decipher(b, length, key);
    }

    out.write(b);
  }

  @Override
  /**
   *  Writes len bytes from the specified byte array starting at offset off to this output stream.
   *
   *  @param b   the data.
   *  @param off the start offset in the data.
   *  @param len the number of bytes to write.
   */
  public void write(byte[] b, int off, int len) throws IOException
  {
    int length = Math.min(b.length, len);

    if (length > 0)
    {
      if (encrypt)
        encipher(b, length, key);
      else
        decipher(b, length, key);
    }

    out.write(b, off, len);
  }

  /**
   *  Sets whether the stream should be encrypted or decrypted.
   *
   *  @param encrypt encrypts output if true; decrypts output if false
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
