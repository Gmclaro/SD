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
   * Referee - Operation announce new game (REQUEST)
   */
  public static final int REQ_ANNOUNCE_NEW_GAME = 0;

  /**
   * Referee - Operation announce new game (REPLY)
   */

  public static final int REP_ANNOUNCE_NEW_GAME = 1;

  /**
   * Referee - Operation inform referee (REQUEST)
   */

  public static final int REQ_INFORM_REFEREE = 2;

  /**
   * Referee - Operation inform referee (REPLY)
   */

  public static final int REP_INFORM_REFEREE = 3;

  /**
   * Referee - Operation wait for inform referee (REQUEST)
   */

  public static final int REQ_WAIT_FOR_INFORM_REFEREE = 4;

  /**
   * Referee - Operation wait for inform referee (REPLY)
   */

  public static final int REP_WAIT_FOR_INFORM_REFEREE = 5;

  /**
   * Referee - Operation assert trial decision (REQUEST)
   */
  public static final int REQ_ASSERT_TRIAL_DECISION = 80;

  /**
   * Referee - Operation assert trial decision (REPLY)
   */
  public static final int REP_ASSERT_TRIAL_DECISION = 81;

  /**
   * Referee - Operation declare game winner (REQUEST)
   */
  public static final int REQ_DECLARE_GAME_WINNER = 82;

  /**
   * Referee - Operation declare game winner (REPLY)
   */
  public static final int REP_DECLARE_GAME_WINNER = 83;

  /*
   * General Repository site INPUT/OUTPUT messages
   */

  /**
   * General Repository - set referee state (REQUEST)
   */
  public static final int REQ_LOG_SET_REFEREE_STATE = 6;

  /**
   * General Repository - set referee state (REPLY)
   */
  public static final int REP_LOG_SET_REFEREE_STATE = 7;

  /**
   * General Repository - set coach state (REQUEST)
   */
  public static final int REQ_LOG_SET_COACH_STATE = 8;

  /**
   * General Repository - set coach state (REPLY)
   */
  public static final int REP_LOG_SET_COACH_STATE = 9;

  /**
   * General Repository - set contestant state (REQUEST)
   */
  public static final int REQ_LOG_SET_CONTESTANT_STATE = 10;

  /**
   * General Repository - set contestant state (REPLY)
   */
  public static final int REP_LOG_SET_CONTESTANT_STATE = 11;

  /**
   * General Repository - set contestant strength (REQUEST)
   */
  public static final int REQ_LOG_SET_CONTESTANT_STRENGTH = 12;

  /**
   * General Repository - set contestant strength (REPLY)
   */
  public static final int REP_LOG_SET_CONTESTANT_STRENGTH = 13;

  /**
   * General Repository - set active contestant (REQUEST)
   */
  public static final int REQ_LOG_SET_ACTIVE_CONTESTANT = 14;

  /**
   * General Repository - set active contestant (REPLY)
   */
  public static final int REP_LOG_SET_ACTIVE_CONTESTANT = 15;

  /**
   * General Repository - set remove contestant (REQUEST)
   */
  public static final int REQ_LOG_SET_REMOVE_CONTESTANT = 16;

  /**
   * General Repository - set remove contestant (REPLY)
   */
  public static final int REP_LOG_SET_REMOVE_CONTESTANT = 17;

  /**
   * General Repository - Operation announce new game (REQUEST)
   */
  public static final int REQ_NEW_GAME_STARTED = 18;

  /**
   * General Repository - Operation announce new game (REPLY)
   */
  public static final int REP_NEW_GAME_STARTED = 19;

  /**
   * General Repository - Operation set new trial (REQUEST)
   */
  public static final int REQ_SET_NEW_TRIAL = 20;

  /**
   * General Repository - Operation set new trial (REPLY)
   */
  public static final int REP_SET_NEW_TRIAL = 21;

  /**
   * General Repository - Operation set rope position (REQUEST)
   */
  public static final int REQ_SET_ROPE_POSITION = 22;

  /**
   * General Repository - Operation set rope position (REPLY)
   */
  public static final int REP_SET_ROPE_POSITION = 23;

  /**
   * General Repository - Operation set end of game (REQUEST)
   */
  public static final int REQ_SET_END_OF_GAME = 24;

  /**
   * General Repository - Operation set end of game (REPLY)
   */
  public static final int REP_SET_END_OF_GAME = 25;

  // Do int for showGameResult,setmatchwinner

  /**
   * General Repository - Operation show game result (REQUEST)
   */
  public static final int REQ_SHOW_GAME_RESULT = 26;

  /**
   * General Repository - Operation set match winner (REPLY)
   */

  public static final int REP_SHOW_GAME_RESULT = 27;

  /**
   * General Repository - Operation set match winner (REQUEST)
   */

  public static final int REQ_SET_MATCH_WINNER = 70;

  /**
   * General Repository - Operation set match winner (REPLY)
   */
  public static final int REP_SET_MATCH_WINNER = 71;

  /*
   * Contestant Bench INPUT/OUTPUT messages
   */

  /**
   * Contestant Bench - Operation review notes (REQUEST)
   */
  public static final int REQ_CALL_TRIAL = 29;

  /**
   * Contestant Bench - Operation review notes (REPLY)
   */
  public static final int REP_CALL_TRIAL = 30;

  /**
   * Contestant Bench - Operation review notes (REQUEST)
   */
  public static final int REQ_SEAT_DOWN = 31;

  /**
   * Contestant Bench - Operation review notes (REPLY)
   */
  public static final int REP_SEAT_DOWN = 32;

  /**
   * Contestant Bench - Operation review notes (REQUEST)
   */
  public static final int REQ_REVIEW_NOTES = 33;

  /**
   * Contestant Bench - Operation review notes (REPLY)
   */

  public static final int REP_REVIEW_NOTES = 34;

  /**
   * Contestant Bench - Operation wait for call trial (REQUEST)
   */
  public static final int REQ_WAIT_FOR_CALL_TRIAL = 35;

  /**
   * Contestant Bench - Operation wait for call trial (REPLY)
   */

  public static final int REP_WAIT_FOR_CALL_TRIAL = 36;

  /**
   * Contestant Bench - Operation call contestants (REQUEST)
   */
  public static final int REQ_CALL_CONTESTANTS = 37;

  /**
   * Contestant Bench - Operation call contestants (REPLY)
   */
  public static final int REP_CALL_CONTESTANTS = 38;

  /**
   * Contestant Bench - Operation wait for call contestants (REQUEST)
   */
  public static final int REQ_WAIT_FOR_CALL_CONTESTANTS = 39;

  /**
   * Contestant Bench - Operation wait for call contestants (REPLY)
   */

  public static final int REP_WAIT_FOR_CALL_CONTESTANTS = 40;

  /**
   * Contestant Bench - Operation declare match winner (REQUEST)
   */
  public static final int REQ_DECLARE_MATCH_WINNER = 41;

  /**
   * Contestant Bench - Operation declare match winner (REPLY)
   */
  public static final int REP_DECLARE_MATCH_WINNER = 42;

  /**
   * Contestant Bench - Operation wait for seat at bench (REQUEST)
   */
  public static final int REQ_WAIT_FOR_SEAT_AT_BENCH = 43;

  /**
   * Contestant Bench - Operation wait for seat at bench (REPLY)
   */
  public static final int REP_WAIT_FOR_SEAT_AT_BENCH = 44;

  /*
   * Playground INPUT/OUTPUT messages
   */

  /**
   * Playground - Operation follow coach advice (REQUEST)
   */
  public static final int REQ_FOLLOW_COACH_ADVICE = 45;

  /**
   * Playground - Operation follow coach advice (REPLY)
   */
  public static final int REP_FOLLOW_COACH_ADVICE = 46;

  /**
   * Playground - Operation wait for follow coach advice (REQUEST)
   */
  public static final int REQ_WAIT_FOR_FOLLOW_COACH_ADVICE = 47;

  /**
   * Playground - Operation wait for follow coach advice (REPLY)
   */
  public static final int REP_WAIT_FOR_FOLLOW_COACH_ADVICE = 48;

  /**
   * Playground - Operation start trial (REQUEST)
   */
  public static final int REQ_START_TRIAL = 49;

  /**
   * Playground - Operation start trial (REPLY)
   */
  public static final int REP_START_TRIAL = 50;

  /**
   * Playground - Operation wait for start trial (REQUEST)
   */
  public static final int REQ_WAIT_FOR_START_TRIAL = 51;

  /**
   * Playground - Operation wait for start trial (REPLY)
   */
  public static final int REP_WAIT_FOR_START_TRIAL = 52;

  /**
   * Playground - Operation get ready (REQUEST)
   */
  public static final int REQ_GET_READY = 53;

  /**
   * Playground - Operation get ready (REPLY)
   */
  public static final int REP_GET_READY = 54;

  /**
   * Playground - Operation wait for am done (REQUEST)
   */
  public static final int REQ_WAIT_FOR_AM_DONE = 55;

  /**
   * Playground - Operation wait for am done (REPLY)
   */
  public static final int REP_WAIT_FOR_AM_DONE = 56;

  /**
   * Playground - Operation wait for assert trial decision contestant (REQUEST)
   */
  public static final int REQ_WAIT_FOR_ASSERT_TRIAL_DECISION_CONTESTANT = 57;

  /**
   * Playground - Operation wait for assert trial decision contestant (REPLY)
   */
  public static final int REP_WAIT_FOR_ASSERT_TRIAL_DECISION_CONTESTANT = 58;

  /**
   * Playground - Operation wait for assert trial decision coach (REQUEST)
   */
  public static final int REQ_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH = 59;

  /**
   * Playground - Operation wait for assert trial decision coach (REPLY)
   */
  public static final int REP_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH = 60;

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
