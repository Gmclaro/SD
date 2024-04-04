package sharedRegions;

import entities.*;
import main.*;

import genclass.GenericIO;
import genclass.TextFile;

/**
 * General Repository
 * 
 * It is responsible for logging the internal state of the problem in a file.
 * 
 * It is a shared region.
 */
public class GeneralRepository {
  /**
   * Name of the log file
   */
  private String logFileName;

  /**
   * Current state of the game
   */
  private int currentGame;

  /**
   * Current trial of the game
   */
  private int currentTrial;

  /**
   * Position of the rope
   */
  private int positionOfRope;

  /**
   * Current state of the entities
   */
  private int refereeState;

  /**
   * Current state of the entities
   */
  private int[] coachState;

  /**
   * Current state of the entities
   */
  private int[][] contestantState;

  /**
   * Strength of each Contestants
   */
  private int[][] contestantStrength;

  /**
   * Contestants in the playground that will play or are playing
   */
  private int[][] activeContestants;

  /**
   * Match team winner
   */
  private int matchWinner;

  /**
   * Game team winner
   */
  private int gameWinner;

  /**
   * Flag to indicate the end of the game
   */
  private Boolean endOfGame;

  /**
   * Flag to indicate the end of the match
   */
  private Boolean endOfMatch;

  /**
   * General Repository
   * 
   * It is responsible for logging the internal state of the problem in a file.
   * 
   * It is a shared region.
   * 
   * @param logFileName        Name of the log file
   * @param contestantStrength Initial strength of the Contestants
   */

  public GeneralRepository(String logFileName, int[][] contestantStrength) {
    if (logFileName == null) {
      this.logFileName = "logger";
    } else {
      this.logFileName = logFileName;
    }

    /*
     * Inital Strength of the Contestants
     */
    this.contestantStrength = contestantStrength;

    /*
     * Initial state of the game
     */
    this.currentGame = 0;
    this.currentTrial = 0;
    this.positionOfRope = 0;
    this.endOfGame = false;
    this.endOfMatch = false;

    this.refereeState = RefereeState.START_OF_THE_MATCH;

    this.coachState = new int[SimulParse.COACH];
    for (int i = 0; i < SimulParse.COACH; i++) {
      this.coachState[i] = CoachState.WAIT_FOR_REFEREE_COMMAND;
    }

    this.contestantState = new int[2][SimulParse.CONTESTANT_PER_TEAM];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        this.contestantState[i][j] = ContestantState.SEAT_AT_THE_BENCH;
      }
    }

    this.activeContestants = new int[2][SimulParse.CONTESTANT_PER_TEAM];

    /*
     * Writing the header of the log file
     */
    this.header();
    this.updateInfoTemplate(true);
  }

  /**
   * Write the header of the log file
   */
  public synchronized void header() {
    TextFile log = new TextFile();

    if (!log.openForWriting(".", logFileName)) {
      GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
      System.exit(1);
    }

    String str = "";
    str += "                               Game of the Rope - Description of the internal state\n";
    str += "Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial\n";
    str += "Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";

    log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Write the result of each game in the log file
   */
  public synchronized void showGameResult(int difference) {
    if (difference > 0) {
      gameWinner = 0;
    } else if (difference < 0) {
      gameWinner = 1;
    } else {
      gameWinner = 2;
    }

    TextFile log = new TextFile();

    // Open the file
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of opening the file " + logFileName +
          " failed!");
      System.exit(1);
    }

    String str = "";
    str += "Game " + currentGame;

    if (gameWinner == 0 && (Math.abs(positionOfRope) >= SimulParse.KNOCKOUT)) {
      str += " was won by team 0 by knock out in " + currentTrial + " trials.";
    } else if (gameWinner == 1 && (Math.abs(positionOfRope) >= SimulParse.KNOCKOUT)) {
      str += " was won by team 1 by knock out in " + currentTrial + " trials.";
    } else if (gameWinner == 0 && currentTrial >= SimulParse.TRIALS) {
      str += " was won by team 0 by points.";
    } else if (gameWinner == 1 && currentTrial >= SimulParse.TRIALS) {
      str += " was won by team 1 by points.";
    } else {
      str += " was a draw.";
    }

    log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Write the legend of the log file
   */
  public synchronized void legend() {
    TextFile log = new TextFile();
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of opening the file " + logFileName + " failed!");
      System.exit(1);
    }

    String str = "";
    str += "\nLegend:\n";
    str += "Ref Sta    – state of the referee\n";
    str += "Coa # Stat - state of the coach of team # (# - 1 .. 2)\n";
    str += "Cont # Sta – state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\n";
    str += "Cont # SG  – strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\n";
    str += "TRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)\n";
    str += "TRIAL – NB – trial number\n";
    str += "TRIAL – PS – position of the centre of the rope at the beginning of the trial";

    log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Update the internal state of the entities and write it in the log file the
   * entity state changes.
   * 
   * @param startOfMatch Flag to indicate the start of the match
   */
  public synchronized void updateInfoTemplate(boolean startOfMatch) {
    TextFile log = new TextFile();
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
      System.exit(1);
    }

    String refereeState = "";

    switch (this.refereeState) {
      case RefereeState.START_OF_THE_MATCH:
        refereeState = "SOM";
        break;
      case RefereeState.START_OF_A_GAME:
        refereeState = "SOG";
        break;
      case RefereeState.TEAMS_READY:
        refereeState = "TRY";
        break;
      case RefereeState.WAIT_FOR_TRIAL_CONCLUSION:
        refereeState = "WTC";
        break;
      case RefereeState.END_OF_A_GAME:
        refereeState = "EOG";
        break;
      case RefereeState.END_OF_THE_MATCH:
        refereeState = "EOM";
        break;
      default:
        break;
    }
    String[] coachState = new String[SimulParse.COACH];

    for (int i = 0; i < SimulParse.COACH; i++) {
      switch (this.coachState[i]) {
        case CoachState.WAIT_FOR_REFEREE_COMMAND:
          coachState[i] = "WFRC";
          break;
        case CoachState.ASSEMBLE_TEAM:
          coachState[i] = "ASTM";
          break;
        case CoachState.WATCH_TRIAL:
          coachState[i] = "WATL";
          break;
        default:
          break;
      }
    }
    String[][] contestantState = new String[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
    for (int i = 0; i < SimulParse.COACH; i++) {
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        switch (this.contestantState[i][j]) {
          case ContestantState.SEAT_AT_THE_BENCH:
            contestantState[i][j] = "STB";
            break;
          case ContestantState.STAND_IN_POSITION:
            contestantState[i][j] = "SIP";
            break;
          case ContestantState.DO_YOUR_BEST:
            contestantState[i][j] = "DYB";
            break;
          default:
            break;
        }
      }
    }

    String str = refereeState + "  ";

    for (int i = 0; i < SimulParse.COACH; i++) {
      str += coachState[i] + " ";
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        str += contestantState[i][j] + " " + String.format("%2d", contestantStrength[i][j]) + " ";
      }
      if (i == 0) {
        str += " ";
      }
    }

    if (startOfMatch) {
      str += "- - - . - - - -- --";

    } else {
      String aux[] = new String[] { "", "" };
      for (int i = 0; i < SimulParse.COACH; i++) {
        int active = 0;
        for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
          if (activeContestants[i][j] == 1) {
            aux[i] += String.format("%1d", j) + " ";
            active++;
          }
        }
        if (active != SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM) {
          for (int j = 0; j < (SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM - active); j++) {
            aux[i] += "- ";
          }
        }

      }
      str += String.format("%6s. %6s", aux[0], aux[1]);

      str += String.format("%2d %2d", currentTrial, positionOfRope);
    }

    if (!endOfMatch)
      log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Write the header of a new game in the log file
   */
  public synchronized void newGameStarted() {
    currentGame++;
    currentTrial = 0;
    positionOfRope = 0;
    endOfGame = false;

    TextFile log = new TextFile();
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of opening the file " + logFileName + " failed!");
      System.exit(1);
    }

    String str = "Game " + currentGame + "\n";
    str += "Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial\n";
    str += "Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";

    log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Update the internal state of the Referee and call the method to write the
   * change of state in the log file.
   *
   * @param refereeState
   */
  public synchronized void setRefereeState(int refereeState) {
    this.refereeState = refereeState;
    this.updateInfoTemplate(false);
  }

  /**
   * Update the internal state of one of the Coaches and call the method to write
   * the change of state in the log file.
   * 
   * @param coachID    Team of the Coach
   * @param coachState State of the Coach
   */
  public synchronized void setCoachState(int coachID, int coachState) {
    this.coachState[coachID] = coachState;
    this.updateInfoTemplate(false);
  }

  /**
   * Update the internal state of one of the Contestants and call the method to
   * write the change of state in the log file.
   * 
   * @param team  Team of the Contestant
   * @param id    ID of the Contestant
   * @param state State of the Contestant
   */
  public synchronized void setContestantState(int team, int id, int state) {
    contestantState[team][id] = state;
    this.updateInfoTemplate(false);
  }

  /**
   * Set the strength of one of the Contestants.
   * 
   * @param team     Team of the Contestant
   * @param id       ID of the Contestant
   * @param strength Strength of the Contestant
   */
  public synchronized void setContestantStrength(int team, int id, int strength) {
    contestantStrength[team][id] = strength;
  }

  /**
   * Set the active Contestants in the playground.
   * 
   * @param team Team of the Contestant
   * @param id   ID of the Contestant
   */
  public synchronized void setActiveContestant(int team, int id) {
    activeContestants[team][id] = 1;
  }

  /**
   * Remove a Contestant from the playground.
   * 
   * @param team Team of the Contestant
   * @param id   ID of the Contestant
   */
  public synchronized void setRemoveContestant(int team, int id) {
    activeContestants[team][id] = 0;
  }

  /**
   * Set the position of the rope.
   * 
   * @param positionOfRope Position of the rope
   */
  public synchronized void setRopePosition(int positionOfRope) {
    this.positionOfRope = positionOfRope;
  }

  /**
   * Add a new trial to the game.
   */
  public synchronized void setNewTrial() {
    currentTrial++;
  }

  /**
   * Set the match winner.
   * 
   * matchWinner == 0 -> team 0 wins.
   * matchWinner == 1 -> team 1 wins.
   * matchWinner == 2 -> draw.
   * 
   * @param scores Scores of the match
   */

  public synchronized void setMatchWinner(int[] scores) {
    if (scores[0] > scores[1]) {
      matchWinner = 0;
    } else if (scores[0] < scores[1]) {
      matchWinner = 1;
    } else {
      matchWinner = 2;
    }
    endOfMatch = true;

    TextFile log = new TextFile();

    // Open the file
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of opening the file " + logFileName +
          " failed!");
      System.exit(1);
    }

    String str = "";
    str += "Match";

    if (matchWinner == 0) {
      str += " was won by team 0 (" + scores[0] + "-" + scores[1] + ")";
    } else if (matchWinner == 1) {
      str += " was won by team 1 (" + scores[0] + "-" + scores[1] + ")";
    } else {
      str += " was a draw.";
    }

    log.writelnString(str);

    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /**
   * Set the end of the game.
   */
  public synchronized void setEndOfGame() {
    this.endOfGame = true;
  }

}
