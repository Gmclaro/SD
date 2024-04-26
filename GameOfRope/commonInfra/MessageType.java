package commonInfra;

/**
 * Type of the exchanged messages.
 * the
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */

public class MessageType {

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

  /*
   * General Repository site INPUT/OUTPUT messages
   */

  /**
   * General Repository - set referee state (REQUEST)
   */
  public static final int REQ_LOG_SET_REFEREE_STATE = 7;

  /**
   * General Repository - set referee state (REPLY)
   */
  public static final int REP_LOG_SET_REFEREE_STATE = 8;

  /**
   * General Repository - set coach state (REQUEST)
   */
  public static final int REQ_LOG_SET_COACH_STATE = 9;

  /**
   * General Repository - set coach state (REPLY)
   */
  public static final int REP_LOG_SET_COACH_STATE = 10;

  /**
   * General Repository - set contestant state (REQUEST)
   */
  public static final int REQ_LOG_SET_CONTESTANT_STATE = 11;

  /**
   * General Repository - set contestant state (REPLY)
   */
  public static final int REP_LOG_SET_CONTESTANT_STATE = 12;

  /**
   * General Repository - set contestant strength (REQUEST)
   */
  public static final int REQ_LOG_SET_CONTESTANT_STRENGTH = 13;

  /**
   * General Repository - set contestant strength (REPLY)
   */
  public static final int REP_LOG_SET_CONTESTANT_STRENGTH = 14;

  /**
   * General Repository - set active contestant (REQUEST)
   */
  public static final int REQ_LOG_SET_ACTIVE_CONTESTANT = 15;

  /**
   * General Repository - set active contestant (REPLY)
   */
  public static final int REP_LOG_SET_ACTIVE_CONTESTANT = 16;

  /**
   * General Repository - set remove contestant (REQUEST)
   */
  public static final int REQ_LOG_SET_REMOVE_CONTESTANT = 17;

  /**
   * General Repository - set remove contestant (REPLY)
   */
  public static final int REP_LOG_SET_REMOVE_CONTESTANT = 18;

  /**
   * General Repository - Operation announce new game (REQUEST)
   */
  public static final int REQ_NEW_GAME_STARTED = 19;

  /**
   * General Repository - Operation announce new game (REPLY)
   */
  public static final int REP_NEW_GAME_STARTED = 20;

  /**
   * General Repository - Operation set new trial (REQUEST)
   */
  public static final int REQ_SET_NEW_TRIAL = 21;

  /**
   * General Repository - Operation set new trial (REPLY)
   */
  public static final int REP_SET_NEW_TRIAL = 22;

  /**
   * General Repository - Operation set rope position (REQUEST)
   */
  public static final int REQ_SET_ROPE_POSITION = 23;

  /**
   * General Repository - Operation set rope position (REPLY)
   */
  public static final int REP_SET_ROPE_POSITION = 24;

  /**
   * General Repository - Operation set end of game (REQUEST)
   */
  public static final int REQ_SET_END_OF_GAME = 25;

  /**
   * General Repository - Operation set end of game (REPLY)
   */
  public static final int REP_SET_END_OF_GAME = 26;

  // Do int for showGameResult,setmatchwinner

  /**
   * General Repository - Operation show game result (REQUEST)
   */
  public static final int REQ_SHOW_GAME_RESULT = 27;

  /**
   * General Repository - Operation set match winner (REPLY)
   */

  public static final int REP_SHOW_GAME_RESULT = 28;

  /**
   * General Repository - Operation set match winner (REQUEST)
   */

  public static final int REQ_SET_MATCH_WINNER = 29;

  //////////////////////////////////////////////

  public static final int REQ_GENERAL_REPOSITORY_SHUTDOWN = 90;

  public static final int REP_GENERAL_REPOSITORY_SHUTDOWN = 91;

  public static final int REQ_REFEREE_SITE_SHUTDOWN = 92;

  public static final int REP_REFEREE_SITE_SHUTDOWN = 93;

  public static final int REQ_PLAYGROUND_SHUTDOWN = 94;

  public static final int REP_PLAYGROUND_SHUTDOWN = 95;

  public static final int REQ_CONTESTANT_BENCH_SHUTDOWN = 96;

  public static final int REP_CONTESTANT_BENCH_SHUTDOWN = 97;
}
