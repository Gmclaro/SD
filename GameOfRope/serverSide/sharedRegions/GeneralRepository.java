package serverSide.sharedRegions;

import clientSide.entities.CoachState;
import clientSide.entities.ContestantState;
import clientSide.entities.RefereeState;
import commonInfra.View;
import genclass.GenericIO;
import genclass.TextFile;
import serverSide.main.ServerGameOfRopeGeneralRepository;
import serverSide.main.SimulParse;

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
   * Number of contestants in the playground per team
   */
  private int[] nActiveContestants;

  /**
   * Contestants in the playground that will play or are playing
   */
  private View[][] activeContestants;

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

  private int nEntities = 0;

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

  public GeneralRepository(String logFileName) {
    if (logFileName == null) {
      this.logFileName = "logger";
    } else {
      this.logFileName = logFileName;
    }

  }

  /**
   * Initialize the simulation
   * 
   * @param contestantStrength Initial strength of the Contestants
   */

  public void initSimul(int[][] contestantStrength) {

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
      this.coachState[i] = -1;
    }

    this.contestantState = new int[2][SimulParse.CONTESTANT_PER_TEAM];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        this.contestantState[i][j] = -1;
      }
    }

    this.nActiveContestants = new int[SimulParse.COACH];
    for (int i = 0; i < SimulParse.COACH; i++) {
      this.nActiveContestants[i] = 5;
    }

    this.activeContestants = new View[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
    for (int i = 0; i < SimulParse.COACH; i++) {
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        this.activeContestants[i][j] = new View(-1, -1);
      }
    }

    this.contestantStrength = new int[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
    for (int i = 0; i < SimulParse.COACH; i++) {
      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        this.contestantStrength[i][j] = -1;
      }
    }

    /*
     * Writing the header of the log file
     */
    this.header();
    this.updateInfoTemplate();

    this.contestantStrength = contestantStrength;

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
  }

  /**
   * Write the header of the log file
   */
  private void header() {
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
   * 
   * @param difference Difference of the scores of the teams
   */
  public void showGameResult(int difference) {
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
    positionOfRope = 0;
  }

  /**
   * Write the legend of the log file
   */
  private void legend() {
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
   */
  private void updateInfoTemplate() {
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
        case -1:
          coachState[i] = "----";
          break;
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
          case -1:
            contestantState[i][j] = "---";
            break;
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
        str += contestantState[i][j] + " " + String.format("%2s",
            (contestantStrength[i][j] == -1 ? "--" : (Integer.toString(contestantStrength[i][j])))) + " ";
      }
      if (i == 0) {
        str += " ";
      }
    }

    String aux[] = new String[] { " ", " " };

    for (int i = 0; i < SimulParse.COACH; i++) {
      int idPerTeam[] = new int[SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM];
      for (int j = 0; j < idPerTeam.length; j++) {
        idPerTeam[j] = -1;
      }

      for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
        if (activeContestants[i][j].getKey() == -1)
          continue;

        idPerTeam[activeContestants[i][j].getKey()] = activeContestants[i][j].getValue();
      }

      aux[i] = String.format(
          "%1s %1s %1s ",
          idPerTeam[0] == -1 ? "-" : Integer.toString(idPerTeam[0]),
          idPerTeam[1] == -1 ? "-" : Integer.toString(idPerTeam[1]),
          idPerTeam[2] == -1 ? "-" : Integer.toString(idPerTeam[2]));

    }
    str += String.format("%6s. %6s", aux[0], aux[1]);

    str += String.format("%2d %2d", currentTrial, Math.abs(positionOfRope));

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
  public void newGameStarted() {
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
   * @param refereeState State of the Referee
   */
  public void setRefereeState(int refereeState) {
    this.refereeState = refereeState;
    this.updateInfoTemplate();
  }

  /**
   * Update the internal state of one of the Coaches and call the method to write
   * the change of state in the log file.
   * 
   * @param coachID    Team of the Coach
   * @param coachState State of the Coach
   */
  public void setCoachState(int team, int state) {
    this.coachState[team] = state;
    this.updateInfoTemplate();
  }

  /**
   * Update the internal state of one of the Contestants and call the method to
   * write the change of state in the log file.
   * 
   * @param team  Team of the Contestant
   * @param id    ID of the Contestant
   * @param state State of the Contestant
   */
  public void setContestantState(int team, int id, int state) {
    contestantState[team][id] = state;
    this.updateInfoTemplate();
  }

  /**
   * Set the strength of one of the Contestants.
   * 
   * @param team     Team of the Contestant
   * @param id       ID of the Contestant
   * @param strength Strength of the Contestant
   */
  public void setContestantStrength(int team, int id, int strength) {
    contestantStrength[team][id] = strength;
  }

  /**
   * Set the active Contestants in the playground.
   * 
   * @param team Team of the Contestant
   * @param id   ID of the Contestant
   */
  public void setActiveContestant(int team, int id) {
    activeContestants[team][id].setKey(nActiveContestants[team]++);
    activeContestants[team][id].setValue(id);
  }

  /**
   * Remove a Contestant from the playground.
   * 
   * @param team Team of the Contestant
   * @param id   ID of the Contestant
   */
  public void setRemoveContestant(int team, int id) {
    activeContestants[team][id].setKey(-1);
    activeContestants[team][id].setValue(-1);
    nActiveContestants[team] = nActiveContestants[team] - 1;
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
  public void setNewTrial() {
    currentTrial++;
  }

  /**
   * Set the match winner.
   * 
   * matchWinner == 0 : team 0 wins.
   * matchWinner == 1 : team 1 wins.
   * matchWinner == 2 : draw.
   * 
   * @param scores Scores of the match
   */

  public void setMatchWinner(int[] scores) {
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
    this.legend();
  }

  /**
   * Set the end of the game.
   */
  public void setEndOfGame() {
    this.endOfGame = true;
  }
    /**
   * Operation of shutting down the general repository server.
   */

  public synchronized void shutdown() {
    nEntities += 1;
    // the contestantas just to shut down all at the same time
    if (nEntities >= 3) {
      ServerGameOfRopeGeneralRepository.waitConnection = false;
    }
    notifyAll();
  }

}
