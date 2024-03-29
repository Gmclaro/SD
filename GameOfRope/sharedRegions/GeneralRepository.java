package sharedRegions;

import commonInfra.*;
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
  private String logFileName;

  private int refereeState;
  private int[] coachState;
  private int[][] contestantState;

  private int[][] activeContestants;

  private int[][] contestantStrength;
  private int positionOfRope;

  private int teamWinner;

  private int currentTrial;
  private int currentGame;

  private Boolean endOfGame = false;

  /**
   * General Repository
   * 
   * It is responsible for logging the internal state of the problem in a file.
   * 
   * It is a shared region.
   */

  public GeneralRepository(String logFileName, int[][] contestantStrength) {
    if (logFileName == null) {
      this.logFileName = "logger";
    } else {
      this.logFileName = logFileName;
    }
    this.header();
    this.contestantStrength = contestantStrength;
  }

  public synchronized void header() {
    TextFile log = new TextFile();

    if (!log.openForWriting(".", logFileName)) {
      GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
      System.exit(1);
    }

    log.writelnString("                               Game of the Rope - Description of the internal state");
    log.writelnString(
        "Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5     Trial");
    log.writelnString(
        "Sta Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }

  }

  public synchronized void legend() {
    TextFile log = new TextFile();
    if (!log.openForAppending(".", logFileName)) {
      GenericIO.writelnString("The operation of opening the file " + logFileName + " failed!");
      System.exit(1);
    }

    log.writelnString("\nLegend:");
    log.writelnString("Ref Sta    – state of the referee");
    log.writelnString("Coa # Stat - state of the coach of team # (# - 1 .. 2)");
    log.writelnString(
        "Cont # Sta – state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left");
    log.writelnString(
        "Cont # SG  – strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left");
    log.writelnString(
        "TRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)");
    log.writelnString("TRIAL – NB – trial number");
    log.writelnString("TRIAL – PS – position of the centre of the rope at the beginning of the trial");
    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }

  /*
   * public synchronized void updateInfoTemplate(TextFile log) {
   * String refereeState = "";
   * 
   * switch (this.refereeState) {
   * case RefereeState.START_OF_THE_MATCH:
   * refereeState = "SOM";
   * break;
   * case RefereeState.START_OF_A_GAME:
   * refereeState = "SOG";
   * break;
   * case RefereeState.TEAMS_READY:
   * refereeState = "TRY";
   * break;
   * case RefereeState.WAIT_FOR_TRIAL_CONCLUSION:
   * refereeState = "WTC";
   * break;
   * case RefereeState.END_OF_A_GAME:
   * refereeState = "EOG";
   * break;
   * case RefereeState.END_OF_THE_MATCH:
   * refereeState = "EOM";
   * break;
   * default:
   * break;
   * }
   * 
   * 
   * String[] coachState = new String[2];
   * for (int i = 0; i < 2; i++) {
   * switch (this.coachState[i]) {
   * case CoachState.WAIT_FOR_REFEREE_COMMAND:
   * coachState = "WFRC";
   * break;
   * case CoachState.ASSEMBLE_TEAM:
   * coachState = "ASTM";
   * break;
   * case CoachState.WATCH_TRIAL:
   * coachState = "WATL";
   * break;
   * default:
   * break;
   * }
   * }
   * 
   * // i need to make it for the contestants
   * 
   * // switch(this.contestantState[0][0]){
   * // case ContestantState.SEAT_AT_THE_BENCH:
   * // contestantState = "SAB";
   * // break;
   * // case ContestantState.STAND_IN_POSITION:
   * // contestantState = "SIP";
   * // break;
   * // case ContestantState.DO_YOUR_BEST:
   * // contestantState = "DYB";
   * // break;
   * // }
   * }
   */

  // TODO: missing a lot of methods
  public synchronized void setRefereeState(int refereeState) {
    this.refereeState = refereeState;
  }

  public synchronized void setCoachState(int coachID, int coachState) {
    this.coachState[coachID] = coachState;
  }

}
