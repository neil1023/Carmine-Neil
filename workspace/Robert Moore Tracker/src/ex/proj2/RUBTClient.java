package ex.proj2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.rutgers.cs.cs352.bt.TorrentInfo;

/**
 * Main class for RUBTClient. After starting, spends its time listening on the
 * incoming message queue
 * in order to decide what to do.
 * 
 * @author Robert Moore
 */
public class RUBTClient extends Thread {

  // FIXME: Don't throw Exception from main!
  public static void main(String[] args) throws Exception {
    // FIXME: Check number/type of arguments

    byte[] metaBytes = null;
    { // FIXME: No file checking!
      File metaFile = new File(args[0]);
      DataInputStream metaIn = new DataInputStream(
          new FileInputStream(metaFile));
      metaBytes = new byte[(int) metaFile.length()];
      metaIn.readFully(metaBytes);
      metaIn.close();
    }

    // FIXME: Null check on metaBytes
    TorrentInfo tInfo = new TorrentInfo(metaBytes);

    RUBTClient client = new RUBTClient(tInfo, args[1]);

    // Launches the client as a thread
    client.start();
  }

  private final TorrentInfo tInfo;
  private final String outFileName;
  private RandomAccessFile outFile;
  private final LinkedBlockingQueue<MessageTask> tasks = new LinkedBlockingQueue<MessageTask>();
  // FIXME: Generate a random peer ID value
  private final byte[] peerId = { 'E', 'x', 'a', 'm', 'p', 'l', 'e', ' ', 'C',
      'l', 'i', 'e', 'n', 't', ' ', 'P', 'r', 'o', 'j', '2' };
  // TODO: Actually bind a port!
  private int port = 12345;
  
  /**
   * List of peers currently connected to the client.
   */
  private final List<Peer> peers = Collections.synchronizedList(new LinkedList<Peer>());

  /**
   * A timer for scheduling tracker announces.
   */
  private final Timer trackerTimer = new Timer();

  /**
   * Tracker interface.
   */
  final Tracker tracker;
  /**
   * Flag to keep the main loop running. Once false, the client *should* exit.
   */
  private volatile boolean keepRunning = true;

  private static class TrackerAnnounceTask extends TimerTask {
    private final RUBTClient client;

    public TrackerAnnounceTask(final RUBTClient client) {
      this.client = client;
    }

    public void run() {
      // FIXME: Actual parameters for regular tracker announces
      List<Peer> peers = this.client.tracker.announce(0, 0, 0, null);
      if (peers != null && !peers.isEmpty()) {
        this.client.addPeers(peers);
      }
      this.client.trackerTimer.schedule(this,
          this.client.tracker.getInterval() * 1000);
    }
  }

  public RUBTClient(final TorrentInfo tInfo, final String outFile) {
    this.tInfo = tInfo;
    this.outFileName = outFile;
    this.tracker = new Tracker(this.peerId, this.tInfo.info_hash.array(),
        this.tInfo.announce_url.toString(), this.port);
  }

  @Override
  public void run() {
    
    try {
      this.outFile = new RandomAccessFile(this.outFileName, "rw");
    } catch (FileNotFoundException e) {
      System.err.println("Unable to open output file for writing!");
      e.printStackTrace();
      // Exit right now, since nothing else was started yet
      return;
    }

    // FIXME: Parameters for "started" announce
    List<Peer> peers = this.tracker.announce(0, 0, 0, "started");
    this.addPeers(peers);
    {
      // Schedule the first "regular" announce - the rest are schedule by the
      // task itself
      int interval = this.tracker.getInterval();
      this.trackerTimer
          .schedule(new TrackerAnnounceTask(this), interval * 1000);
    }

    // Main loop:
    while (this.keepRunning) {
      try {
        MessageTask task = this.tasks.take();
        // TODO: Process the task
        Message msg = task.getMessage();
        Peer peer = task.getPeer();
        switch (msg.getId()) {
        case Message.ID_UNCHOKE:
          if (!peer.amChoked() && peer.amInterested()) {
            this.chooseAndRequestPiece(peer);
          }
          break;
        }
      } catch (InterruptedException ie) {
        // This can happen either "randomly" or due to a shutdown - just
        // continue the loop.
        continue;
      }
    }

    this.shutdown();
  }

  void addPeers(final List<Peer> newPeers) {
    // TODO: Check which of newPeers are not already connected (peer ID) and try
    // to connect to those
  }

  private void chooseAndRequestPiece(final Peer peer) {
    // TODO: Determine which piece to request from the remote peer, and tell the
    // peer to "download" it.
  }

  private void shutdown() {
    // Cancel any upcoming tracker announces
    this.trackerTimer.cancel();
    // Disconnect all peers
    for(Peer peer : this.peers){
      peer.disconnect();
    }
    
    // FIXME: Tracker parameters for "stopped"
    this.tracker.announce(0, 0, 0, "stopped");
    // TODO: make sure all data is written to disk, all threads done
  }
}
