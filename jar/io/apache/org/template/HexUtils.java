package io.apache.org.template;

import java.io.UnsupportedEncodingException;

public class HexUtils
{
  private static final char[] hexLookUp = { '0', '1', '2', '3', '4', '5', 
    '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  
  public static String bytesToHexStr(byte[] bytes)
  {
    StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++)
    {
      stringBuffer.append(hexLookUp[(bytes[i] >>> 4 & 0xF)]);
      stringBuffer.append(hexLookUp[(bytes[i] & 0xF)]);
    }
    return stringBuffer.toString();
  }
  
  public static byte[] hexStrToBytes(String str)
  {
    byte[] bytes = new byte[str.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = ((byte)Integer.parseInt(str.substring(2 * i, 2 * i + 2), 
        16));
    }
    return bytes;
  }
  
  public static void main(String[] args)
    throws UnsupportedEncodingException
  {}
}
