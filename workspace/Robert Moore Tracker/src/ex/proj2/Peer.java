package ex.proj2;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Robert Moore
 *
 */
public class Peer {

  /**
   * True if the LOCAL client is interested in the REMOTE peer's pieces.
   */
  private boolean localInterested = false;
  /**
   * True if the REMOET peer is interested in the LOCAL client's pieces.
   */
  private boolean remoteInterested = false;
  
  /**
   * True if the LOCAL client is choked by the REMOTE peer.
   */
  private boolean localChoked = true;
  
  /**
   * True if the REMOET peer is choked by the LOCAL client.
   */
  private boolean remoteChoked=true;
  
  private DataOutputStream out = null;
  
  /**
   * Returns the current Interested state of the LOCAL CLIENT.
   * @return {@code true} if the LOCAL CLIENT is interested in the REMOTE PEER's pieces
   */
  public boolean amInterested(){
    return this.localInterested;
  }
  
  /**
   * Returns the current Choked state of the LOCAL CLIENT.
   * @return {@code true} if the LOCAL CLIENT is choked by the REMOTE PEER.
   */
  public boolean amChoked(){
    return this.localChoked;
  }
  
  /**
   * Sends the provided message to this remote peer.
   * @param msg the Peer message to send
   * @throws IOException if an Exception is thrown by the underlying write operation.
   */
  public synchronized void sendMessage(final Message msg) throws IOException{
    if(this.out == null){
      throw new IOException("Output stream is null, cannot write message to " + this);
    }
    msg.write(this.out);
  }
  
  /**
   * Disconnects this peer.
   */
  public void disconnect(){
    // TODO: Disconnect the socket, catch all exceptions
  }
}
