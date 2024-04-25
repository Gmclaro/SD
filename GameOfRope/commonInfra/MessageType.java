package commonInfra;

/**
 *   Type of the exchanged messages.
 *the
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class MessageType
{



  /*
   * Refereree site INPUT/OUTPUT messages
   */


  /**
   * Referee - Operation get announce new game (REQUEST)
   */
   public static final int REQ_ANNOUNCE_NEW_GAME = 1;

  /**
   * Referee - Operation get announce new game (REPLY)
   */

  public static final int REP_ANNOUNCE_NEW_GAME = 2;

  /**
   * Referee - Operation get inform referee (REQUEST)
   */

  public static final int REQ_INFORM_REFEREE = 3;

  /**
   * Referee - Operation get inform referee (REPLY)
   */

  public static final int REP_INFORM_REFEREE = 4;


  /**
   * Referee - Operation get wait for inform referee (REQUEST)
   */

  public static final int REQ_WAIT_FOR_INFORM_REFEREE = 5;

  /**
   * Referee - Operation get wait for inform referee (REPLY)
   */

  public static final int REP_WAIT_FOR_INFORM_REFEREE = 6;
}
