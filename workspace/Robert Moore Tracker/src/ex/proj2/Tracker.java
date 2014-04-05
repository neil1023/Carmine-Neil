package ex.proj2;

import java.util.List;

/**
 * @author Robert Moore
 *
 */
public class Tracker {
  private final byte[] infoHash;
  private final byte[] clientId;
  private final String announceUrl;
  private final int port;
  
  private int interval = 60;
  
  /**
   * Creates a new Tracker interface object.
   * @param clientId the local client's peer id
   * @param infoHash the torrent's info hash
   * @param announceUrl the announce URL of the tracker
   * @param port the listen port for the local client.
   */
  public Tracker(final byte[] clientId, final byte[] infoHash, final String announceUrl, final int port){
    this.infoHash = infoHash;
    this.clientId = clientId;
    this.announceUrl = announceUrl;
    this.port = port;
  }
  
  /**
   * Perform a tracker announce with the provided parameters
   * @param downloaded the number of bytes downloaded in this torrent
   * @param uploaded the number of bytes uploaded in this torrent
   * @param left the number of bytes remaining in the file
   * @param event the announce event (optional)
   * @return the returned list of peers, or {@code null} if an error occurred.
   */
  public List<Peer> announce(int downloaded, int uploaded, int left, String event){
    return null;
  }

  /**
   * Get the latest "interval" value from the tracker.
   * @return the latest returned "interval" value
   */
  public int getInterval() {
    return this.interval;
  }
}
