package entities;

/**
 * Coach States
 * 
 */
public class CoachState {
    /**
     * The coach is waiting for the referee command.
     */
    public static final int WAIT_FOR_REFEREE_COMMAND = 0;
    /**
     * The coach is assembling the team.
     */
    public static final int ASSEMBLE_TEAM = 1;
    /**
     * The coach is watching the trial.
     */
    public static final int WATCH_TRIAL = 2;
}
