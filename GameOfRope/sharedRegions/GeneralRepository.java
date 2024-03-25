package sharedRegions;

import commonInfra.*;
import entities.*;
import main.*;

import java.io.FileWriter;
import java.io.IOException;

/**
 * General Repository
 * 
 * It is responsible for logging the internal state of the problem in a file.
 * 
 * It is a shared region.
 */

public class GeneralRepository {
  private String logFileName;

  private int refereeState = RefereeState.START_OF_THE_MATCH;
  private int[] coachState = {CoachState.WAIT_FOR_REFEREE_COMMAND};
  // private int[][] contestantState = {ContestantState.SEAT_AT_THE_BENCH};

  public GeneralRepository(String logFileName) {
    if(logFileName == null) {
      this.logFileName = "logger";
    }else{
      this.logFileName = logFileName;
    }
    try {
      FileWriter writer = new FileWriter(this.logFileName,false);
      writer.write("");
      writer.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
    /**
     * General Repository
     * 
     * It is responsible for logging the internal state of the problem in a file.
     * 
     * It is a shared region.
     */

     // private fiels

    /**
    * Constestants strength
    */

  private int[] strength;

  private Boolean endOfGame = false;

  
  public synchronized void header() {
    try {
        FileWriter writer = new FileWriter(this.logFileName,true);
        writer.write("Game of the Rope - Description of the internal state\n");
        writer.write("Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Trial\n");
        writer.write("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
  }


  public synchronized void legend(){
    try {
        FileWriter writer = new FileWriter(this.logFileName,true);
        writer.write("Legend:%n");
        writer.write("Ref Sta - state of the referee\n");
        writer.write("Coa # Stat - state of the coach of team # (# - 1 .. 2)\n");
        writer.write("Cont # Sta - state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\n");
        writer.write("Cont # SG - strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate lef\n");
        writer.write("TRIAL - ? - contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)\n");
        writer.write("TRIAL - NB - trial number\n");
        writer.write("TRIAL - PS - position of the centre of the rope at the beginning of the trial\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public synchronized void updateInfoTemplate(FileWriter writer){
      String referreState = "";

      switch (this.refereeState) {
          case RefereeState.START_OF_THE_MATCH:
              referreState = "SOM";
              break;
          case RefereeState.START_OF_A_GAME:
              referreState = "SOG";
              break;
          case RefereeState.TEAMS_READY:
              referreState = "TRY";
              break;
          case RefereeState.WAIT_FOR_TRIAL_CONCLUSION:
              referreState = "WTC";
              break;
          case RefereeState.END_OF_A_GAME:
              referreState = "EOG";
              break;
          case RefereeState.END_OF_THE_MATCH:
              referreState = "EOM";
              break;
          default:
              break;
      }

      for (int i = 0; i < 2; i++) {
          String coachState = "";
          switch (this.coachState[i]) {
              case CoachState.WAIT_FOR_REFEREE_COMMAND:
                  coachState = "WFRC";
                  break;
              case CoachState.ASSEMBLE_TEAM:
                  coachState = "ASTM";
                  break;
              case CoachState.WATCH_TRIAL:
                  coachState = "WATL";
                  break;
              default:
                  break;
          }
      }
      // i need to make it for the contestants

      // switch(this.contestantState[0][0]){
      //     case ContestantState.SEAT_AT_THE_BENCH:
      //         contestantState = "SAB";
      //         break;
      //     case ContestantState.STAND_IN_POSITION:
      //         contestantState = "SIP";
      //         break;
      //     case ContestantState.DO_YOUR_BEST:
      //         contestantState = "DYB";
      //         break;
      // }
  }
}
