package steganography;

public class ImageTooSmallException extends Exception
{
  public ImageTooSmallException()
  {
    super("The input image is too small to fit data!");
  }
}
