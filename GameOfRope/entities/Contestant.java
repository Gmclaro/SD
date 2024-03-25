package entities;

import sharedRegions.ContestantBench;
import sharedRegions.Playground;
import sharedRegions.RefereeSite;

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
    private int id;

    /**
     * Contestant Strength
     */
    private int strength;

    /**
     * Contestant Team Identification
     */
    private int team;

    /**
     * Contestant State
     */
    private int state;

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
    public Contestant(int id, int team, Playground playground, RefereeSite refereeSite,
            ContestantBench contestantBench) {
        super("Co" + id + "T" + team);
        this.id = id;
        this.team = team;
        this.state = ContestantState.SEAT_AT_THE_BENCH;
        this.strength = (int) (Math.random() * 4 + 6);
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.contestantBench = contestantBench;
    }

    /**
     * Set contestant id
     * 
     * @param contestantID contestant id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get contestant id
     * 
     * @return contestant id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get contestant id
     * 
     * @return contestant id
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * Set contestant id
     * 
     * @param contestantID contestant id
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * Set contestant state
     * 
     * @param contestantState contestant state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get contestant state
     * 
     * @return contestant state
     */
    public int getEntityState() {
        return this.state;
    }

    /**
     * Life cycle of the contestant
     */
    @Override
    public void run() {
        // TODO: implement contestant life cycle
        /*
         * contestantBench.seatDown(contestantID, teamID);
         * 
         * while(RefereeSite.referee.getState() != RefereeState.END_OF_THE_MATCH){
         * contestantBench.followCoachAdvice();
         * playground.getReady();
         * strength = pullTheRope();
         * playground.amDone(strength, teamID);
         * }
         * 
         */

    }

    /**
     * Pull the rope
     * 
     * Internal operation
     * 
     * @return strength of the contestant
     */
    public int pullTheRope() {
        // TODO: maybe missing the sleep time from the independent operations
        return this.strength--;
    }
}
