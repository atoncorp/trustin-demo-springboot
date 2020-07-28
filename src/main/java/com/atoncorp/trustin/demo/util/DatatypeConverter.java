package com.atoncorp.trustin.demo.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DatatypeConverter {
  /**
   * HEX 문자열 출력을 위한 변수
   */
  private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
  
  /**
   * Base64 디코딩맵
   */
  private static final byte[] decodeMap = initDecodeMap();
  
  /**
   * Base64 인코딩맵
   */
  private static final char[] encodeMap = initEncodeMap();
  
  /**
   * Base64 패딩
   */
  private static final byte PADDING = 127;
  
  /**
   * HEX -> BIN
   * 
   * @param s HEX문자열
   * @return Binary
   */
  public static byte[] parseHexBinary(String s)
  {
      if (s == null)
          throw new IllegalArgumentException("hexBinary is null");
      
      final int len = s.length();
      
      if (len % 2 != 0)
          throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
      
      byte[] out = new byte[len / 2];
      
      for (int i = 0; i < len; i += 2)
      {
          int h = hexToBin(s.charAt(i));
          int l = hexToBin(s.charAt(i + 1));
          if (h == -1 || l == -1)
              throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
          
          out[i / 2] = (byte) (h * 16 + l);
      }
      
      return out;
  }
  
  /**
   * BIN -> HEX
   * 
   * @param data
   * @return
   */
  public static String printHexBinary(byte[] data)
  {
      StringBuilder r = new StringBuilder(data.length * 2);
      for (byte b : data)
      {
          r.append(hexCode[(b >> 4) & 0xF]);
          r.append(hexCode[(b & 0xF)]);
      }
      return r.toString();
  }
  
  /**
   * Base64 -> BIN
   * 
   * @param lexicalXSDBase64Binary
   * @return
   */
  public static byte[] parseBase64Binary(String lexicalXSDBase64Binary)
  {
      return _parseBase64Binary(lexicalXSDBase64Binary);
  }
  
  /**
   * BIN -> BASE64
   * 
   * @param val
   * @return
   */
  public static String printBase64Binary(byte[] val)
  {
      return _printBase64Binary(val);
  }
  
  public static byte[] longToBytes(long l) {
    ByteBuffer buf = ByteBuffer.allocate(8);
    buf.putLong(l);
    return buf.array();
  }
  
  public static byte[] intToBytes(int i, ByteOrder byteOrder) {
    ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE/8);
    buf.order(byteOrder);
    buf.putInt(i);
    return buf.array();
  }
  
  public static int bytesToInt(byte[] array, ByteOrder byteOrder) {
    int size = Integer.SIZE/8;
//    ByteBuffer buf = ByteBuffer.allocate(size);
    ByteBuffer buf;
    byte[] newBytes = new byte[size];
    for (int i=0; i<size; i++) {
      if (i + array.length < size) {
        newBytes[i] = (byte)0x00;
      } else {
        newBytes[i] = array[i + array.length - size];
      }
    }
    buf = ByteBuffer.wrap(newBytes);
    buf.order(byteOrder);
    return buf.getInt();
  }
  
  private static int hexToBin(char ch)
  {
      if ('0' <= ch && ch <= '9')
          return ch - '0';
      if ('A' <= ch && ch <= 'F')
          return ch - 'A' + 10;
      if ('a' <= ch && ch <= 'f')
          return ch - 'a' + 10;
      return -1;
  }
  
  private static String _printBase64Binary(byte[] input)
  {
      return _printBase64Binary(input, 0, input.length);
  }
  
  private static String _printBase64Binary(byte[] input, int offset, int len)
  {
      char[] buf = new char[((len + 2) / 3) * 4];
      int ptr = _printBase64Binary(input, offset, len, buf, 0);
      assert ptr == buf.length;
      return new String(buf);
  }
  
  /**
   * Encodes a byte array into a char array by doing base64 encoding.
   *
   * The caller must supply a big enough buffer.
   *
   * @return the value of {@code ptr+((len+2)/3)*4}, which is the new offset in
   *         the output buffer where the further bytes should be placed.
   */
  private static int _printBase64Binary(byte[] input, int offset, int len, char[] buf, int ptr)
  {
      for (int i = offset; i < len; i += 3)
      {
          switch (len - i)
          {
              case 1:
                  buf[ptr++] = encode(input[i] >> 2);
                  buf[ptr++] = encode(((input[i]) & 0x3) << 4);
                  buf[ptr++] = '=';
                  buf[ptr++] = '=';
                  break;
              case 2:
                  buf[ptr++] = encode(input[i] >> 2);
                  buf[ptr++] = encode(((input[i] & 0x3) << 4) | ((input[i + 1] >> 4) & 0xF));
                  buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
                  buf[ptr++] = '=';
                  break;
              default:
                  buf[ptr++] = encode(input[i] >> 2);
                  buf[ptr++] = encode(((input[i] & 0x3) << 4) | ((input[i + 1] >> 4) & 0xF));
                  buf[ptr++] = encode(((input[i + 1] & 0xF) << 2) | ((input[i + 2] >> 6) & 0x3));
                  buf[ptr++] = encode(input[i + 2] & 0x3F);
                  break;
          }
      }
      return ptr;
  }
  
  private static char encode(int i)
  {
      return encodeMap[i & 0x3F];
  }
  
  private static byte[] initDecodeMap()
  {
      byte[] map = new byte[128];
      int i;
      for (i = 0; i < 128; i++)
          map[i] = -1;
      
      for (i = 'A'; i <= 'Z'; i++)
          map[i] = (byte) (i - 'A');
      for (i = 'a'; i <= 'z'; i++)
          map[i] = (byte) (i - 'a' + 26);
      for (i = '0'; i <= '9'; i++)
          map[i] = (byte) (i - '0' + 52);
      map['+'] = 62;
      map['/'] = 63;
      map['='] = PADDING;
      
      return map;
  }
  
  private static char[] initEncodeMap()
  {
      char[] map = new char[64];
      int i;
      for (i = 0; i < 26; i++)
          map[i] = (char) ('A' + i);
      for (i = 26; i < 52; i++)
          map[i] = (char) ('a' + (i - 26));
      for (i = 52; i < 62; i++)
          map[i] = (char) ('0' + (i - 52));
      map[62] = '+';
      map[63] = '/';
      
      return map;
  }
  
  /**
   * @param text
   *            base64Binary data is likely to be long, and decoding requires each
   *            character to be accessed twice (once for counting length, another
   *            for decoding.)
   *
   *            A benchmark showed that taking {@link String} is faster,
   *            presumably because JIT can inline a lot of string access (with
   *            data of 1K chars, it was twice as fast)
   */
  private static byte[] _parseBase64Binary(String text)
  {
      final int buflen = guessLength(text);
      final byte[] out = new byte[buflen];
      int o = 0;
      
      final int len = text.length();
      int i;
      
      final byte[] quadruplet = new byte[4];
      int q = 0;
      
      // convert each quadruplet to three bytes.
      for (i = 0; i < len; i++)
      {
          char ch = text.charAt(i);
          byte v = decodeMap[ch];
          
          if (v != -1)
              quadruplet[q++] = v;
          
          if (q == 4)
          {
              // quadruplet is now filled.
              out[o++] = (byte) ((quadruplet[0] << 2) | (quadruplet[1] >> 4));
              if (quadruplet[2] != PADDING)
                  out[o++] = (byte) ((quadruplet[1] << 4) | (quadruplet[2] >> 2));
              if (quadruplet[3] != PADDING)
                  out[o++] = (byte) ((quadruplet[2] << 6) | (quadruplet[3]));
              q = 0;
          }
      }
      
      if (buflen == o) // speculation worked out to be OK
          return out;
      
      // we overestimated, so need to create a new buffer
      byte[] nb = new byte[o];
      System.arraycopy(out, 0, nb, 0, o);
      return nb;
  }
  
  /**
   * computes the length of binary data speculatively.
   *
   * <p>
   * Our requirement is to create byte[] of the exact length to store the binary
   * data. If we do this in a straight-forward way, it takes two passes over the
   * data. Experiments show that this is a non-trivial overhead (35% or so is
   * spent on the first pass in calculating the length.)
   *
   * <p>
   * So the approach here is that we compute the length speculatively, without
   * looking at the whole contents. The obtained speculative value is never less
   * than the actual length of the binary data, but it may be bigger. So if the
   * speculation goes wrong, we'll pay the cost of reallocation and buffer
   * copying.
   *
   * <p>
   * If the base64 text is tightly packed with no indentation nor illegal char
   * (like what most web services produce), then the speculation of this method
   * will be correct, so we get the performance benefit.
   */
  private static int guessLength(String text)
  {
      final int len = text.length();
      
      // compute the tail '=' chars
      int j = len - 1;
      for (; j >= 0; j--)
      {
          byte code = decodeMap[text.charAt(j)];
          if (code == PADDING)
              continue;
          if (code == -1)
              // most likely this base64 text is indented. go with the upper bound
              return text.length() / 4 * 3;
          break;
      }
      
      j++; // text.charAt(j) is now at some base64 char, so +1 to make it the size
      int padSize = len - j;
      if (padSize > 2) // something is wrong with base64. be safe and go with the upper bound
          return text.length() / 4 * 3;
          
      // so far this base64 looks like it's unindented tightly packed base64.
      // take a chance and create an array with the expected size
      return text.length() / 4 * 3 - padSize;
  }
}
