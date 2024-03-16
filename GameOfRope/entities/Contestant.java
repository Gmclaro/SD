package GameOfRope.entities;

import GameOfRope.sharedRegions.ConstestantBench;
import GameOfRope.sharedRegions.ContestantBench;
import GameOfRope.sharedRegions.Playground;
import GameOfRope.sharedRegions.RefereeSite;

/**
 * Custom Thread
 * 
 * It simulates the life cycle of a contestant
 * It will be a static solution
 */
public class Contestant extends Thread {

    /**
     * Constestant Identification
     */
    private int constestantID;

    /**
     * Contestant Strength
     */
    private int strength;

    /**
     * Contestant Team Identification
     */
    private int teamID;

    /**
     * Contestant State
     */
    private int contestantState;

    /**
     * Shared memory region declaration
     */
    private final Playground playground;
    private final RefereeSite refereeSite;
    private final ContestantBench contestantBench;

    /**
     * Contestant Constructor
     * 
     * @param name         thread name
     * @param contestantID contestant id
     * @param teamID       team id
     * @param playground   reference to playground
     * @param refereeSite  reference to refereeSite
     * @param bench        reference to contestantBench
     */
    public Contestant(int contestantID, int teamID, Playground playground, RefereeSite refereeSite,
            ContestantBench bench) {
        super("Co" + contestantID + "T" + teamID);
        this.constestantID = contestantID;
        this.teamID = teamID;
        this.contestantState = ContestantState.SEAT_AT_THE_BENCH;
        this.strength = (int) (Math.random() * 4 + 6);
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.bench = bench;
    }

    /**
     * Set contestant id
     * 
     * @param contestantID contestant id
     */
    public void setContestantID(int id) {
        this.teamID = id;
    }

    /**
     * Get contestant id
     * 
     * @return contestant id
     */
    public int getTeamID() {
        return this.constestantID;
    }

    /**
     * Set contestant id
     * 
     * @param contestantID contestant id
     */
    public void setTeamID(int id) {
        this.constestantID = id;
    }

    /**
     * Get contestant id
     * 
     * @return contestant id
     */
    public int getContestantID() {
        return this.constestantID;
    }

    /**
     * Set contestant state
     * 
     * @param contestantState contestant state
     */
    public void setContestantState(int state) {
        this.contestantState = state;
    }

    /**
     * Get contestant state
     * 
     * @return contestant state
     */
    public int getContestantState() {
        return this.contestantState;
    }

    /**
     * Life cycle of the contestant
     */
    @Override
    public void run() {
        // TODO: implement contestant life cycle
        /*
         * while(!endOfTheMatch()){
         * 
         * followCoachAdvice();
         * getReady();
         * pullTheRope();
         * amDone();
         * }
         */

        ContestantBench.seatDown();
        while(!RefereeSite.getInstance().endOfTheMatch()){
            switch(contestantState){
                case SEAT_AT_THE_BENCH:
                    Playground.followCoachAdvice();
                    break;
                case STAND_IN_POSITION:
                    Playground.getReady();
                    break;
                case DO_YOUR_BEST:
                    pullTheRope();
                    ContestantBench.seatDown();
                    break;

            }
        }

    }

    /**
     * Pull the rope
     * 
     * Internal operation
     * 
     * @return strength of the contestant
     */
    public int pullTheRope() {
        // TODO: return a the strength and decrease by one
    }
}
