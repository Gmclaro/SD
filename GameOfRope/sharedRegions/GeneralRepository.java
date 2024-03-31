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
  private String logFileName;

  private int currentGame;
  private int currentTrial;
  private int positionOfRope;

  private int refereeState;
  private int[] coachState;
  private int[][] contestantState;

  private int[][] contestantStrength;
  private int[][] activeContestants;

  private int matchWinner;

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

    /**
     * Header of the log file
     */
    this.header();

    /**
     * Inital Strength of the Contestants
     */
    this.contestantStrength = contestantStrength;

    /**
     * Initial state of the game
     */
    this.currentGame = 0;
    this.currentTrial = 0;
    this.positionOfRope = 0;

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

    updateInfoTemplate(true);

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

  // public void gameResult(){
  //   TextFile log = new TextFile();

  //   // Open the file
  //   if (!log.openForAppending(".", logFileName)) {
  //     GenericIO.writelnString("The operation of opening the file " + logFileName + " failed!");
  //     System.exit(1);
  //   }

  //   // Write the game result to the log file
  //   //Game # was won by team # by knock out in # trials. / by points. / was a draw. (it changes according to the result)
  //   if (matchWinner == 0 && currentTrial < SimulParse.TRIALS) {
  //     log.writelnString("Game " + currentGame + " was won by team 0 by knock out in " + currentTrial + " trials.");
  //   } else if (matchWinner == 1 && currentTrial < SimulParse.TRIALS) {
  //     log.writelnString("Game " + currentGame + " was won by team 1 by knock out in " + currentTrial + " trials.");
  //   } else if (matchWinner == 0 && currentTrial == SimulParse.TRIALS) {
  //     log.writelnString("Game " + currentGame + " was won by team 0 by points.");
  //   } else if (matchWinner == 1 && currentTrial == SimulParse.TRIALS) {
  //     log.writelnString("Game " + currentGame + " was won by team 1 by points.");
  //   } else {
  //     log.writelnString("Game " + currentGame + " was a draw.");
  //   }
  // }

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



  public synchronized void updateInfoTemplate(boolean startOfMatch) {
    TextFile log = new TextFile();
    if (!log.openForWriting(".", logFileName)) {
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
    for(int i=0 ; i <SimulParse.COACH; i++){
        for(int j= 0; j <SimulParse.CONTESTANT_PER_TEAM;j++){
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


    // Write referee state, coach,  states to log file
    log.writelnString(refereeState + " " + coachState[0] + " " + contestantState[0][0] + " " + contestantStrength[0][0] + " " + contestantState[0][1] + " " + contestantStrength[0][1] + " " + contestantState[0][2] + " " + contestantStrength[0][2] + " " + contestantState[0][3] + " " + contestantStrength[0][3] + " " + contestantState[0][4] + " " + contestantStrength[0][4] + " " + coachState[1] + " " + contestantState[1][0] + " " + contestantStrength[1][0] + " " + contestantState[1][1] + " " + contestantStrength[1][1] + " " + contestantState[1][2] + " " + contestantStrength[1][2] + " " + contestantState[1][3] + " " + contestantStrength[1][3] + " " + contestantState[1][4] + " " + contestantStrength[1][4] + " " + currentTrial + " " + positionOfRope);


    if(!startOfMatch){
        log.writelnString("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
    }else{
        log.writelnString("- - - . - - - -- --");
    }
    if (!log.close()) {
      GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
      System.exit(1);
    }
  }


  public synchronized void newGameStarted() {
    currentGame++;
    currentTrial = 0;
    positionOfRope = 0;
    endOfGame = false;
  }

  public synchronized void setRefereeState(int refereeState) {
    this.refereeState = refereeState;
  }

  public synchronized void setCoachState(int coachID, int coachState) {
    this.coachState[coachID] = coachState;
  }

  public synchronized void setContestantState(int team, int id, int state) {
    contestantState[team][id] = state;
  }

  public synchronized  void setContestantStrength(int team, int id, int strength) {
    contestantStrength[team][id] = strength;
  }

  public synchronized void setActiveContestant(int team, int id) {
    activeContestants[team][id] = 1;
  }

  public synchronized void setGameWinner(int difference) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setGameWinner'");
  }

  /**
   * Set the match winner.
   * 
   * matchWinner == 0 -> team 0 wins
   * matchWinner == 1 -> team 1 wins
   * matchWinner == 2 -> draw
   * 
   * @param scores
   */
  

  public synchronized void setMatchWinner(int[] scores) {
    if (scores[0] > scores[1]) {
      matchWinner = 0;
    } else if (scores[0] < scores[1]) {
      matchWinner = 1;
    } else {
      matchWinner = 2;
    }
  }
  
  public synchronized void setEndOfGame(Boolean endOfGame) {
    this.endOfGame = endOfGame;
  }

  // function to print the trial result
  

  

}
