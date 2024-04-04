package entities;

/**
 * Referee States
 * 
 */
public class RefereeState {
    /**
     * The referee is in the start of the match
     */
    public static final int START_OF_THE_MATCH = 0;
    /**
     * The referee is in the start of a game
     */
    public static final int START_OF_A_GAME = 1;
    /**
     * The referee is waiting for the teams to be ready
     */
    public static final int TEAMS_READY = 2;
    /**
     * The referee is waiting for the trial conclusion
     */
    public static final int WAIT_FOR_TRIAL_CONCLUSION = 3;
    /**
     * The referee is in the end of a game
     */
    public static final int END_OF_A_GAME = 4;
    /**
     * The referee is in the end of the match
     */
    public static final int END_OF_THE_MATCH = 5;
}
