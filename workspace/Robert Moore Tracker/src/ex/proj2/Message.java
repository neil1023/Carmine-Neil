
package ex.proj2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a Peer Wire Protocol message, specifically those
 * after the handshake. Also contains methods for encoding and decoding
 * the messages according to the protocol specs.
 * 
 * @author Robert Moore
 */
public class Message {

  /**
   * A keep-alive message.
   */
  public static final Message KEEP_ALIVE = new Message(0, (byte) 0);

  // TODO: Add remaining ids
  public static final byte ID_CHOKE = 0;
  /**
   * Message id value for Unchoke messages.
   */
  public static final byte ID_UNCHOKE = 1;

  private final int length;

  private final byte id;

  protected Message(int length, byte id) {
    this.length = length;
    this.id = id;
  }

  public byte getId() {
    return this.id;
  }

  /**
   * Reads the next Peer message from the provided DataInputStream.
   * 
   * @param din
   *          the input stream to read
   * @return the decoded message to {@code null} if an
   */
  public static Message read(final DataInputStream din) throws IOException {
    int length = din.readInt();
    if (length == 0) {
      return KEEP_ALIVE;
    }
    // FIXME: Implement message reading.
    throw new IOException("Never implemented message reading!");
  }

  /**
   * Writes this message to the provided DataOutputStream.
   * 
   * @param dout
   *          the output stream to write.
   * @throws IOException
   *           if an IOException occurs.
   */
  public void write(final DataOutputStream dout) throws IOException {
    dout.writeInt(this.length);
    if (this.length > 0) {
      dout.writeByte(this.id);
      // TODO: Something here for Have, Bitfield, Request, Piece, Cancel
      // messages
    }

    dout.flush();
  }

}
