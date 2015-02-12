package trident.kafka;

import java.nio.ByteBuffer;
import kafka.message.Message;

public class ExampleUtil
{
  public static String getMessage(Message message)
  {
    ByteBuffer buffer = message.payload();
    byte [] bytes = new byte[buffer.remaining()];
    buffer.get(bytes);
    return new String(bytes);
  }
}
