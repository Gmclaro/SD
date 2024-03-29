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
    public Contestant(int team, int id, int contestantStrength, Playground playground, RefereeSite refereeSite,
            ContestantBench contestantBench) {
        super("Contestant(T" + team + "," + id + ")");
        this.id = id;
        this.team = team;
        this.strength = contestantStrength;
        this.state = ContestantState.SEAT_AT_THE_BENCH;

        // shared regions
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.contestantBench = contestantBench;
    }

    public String whoAmI() {
        return "Contestant(T" + team + "," + id + ")";
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
     * Get strength of the contestant
     * 
     * @return strength
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Life cycle of the contestant
     */
    @Override
    public void run() {
        System.out.println(this.whoAmI() +" has started.");

        // TODO: implement contestant life cycle
        int orders;
        while (true) {
            orders = contestantBench.waitForCallContestant(team, id);

            switch (orders) {
                case 0:return;      // match is over and contestant thread is done
                case 1:continue;    // contestant was not selected, rest
                case 2:break;       // contestant was selected, go to playground and continue the lifecycle
            }

            playground.followCoachAdvice(this.team);
            playground.waitForStartTrial(this.team, this.id);
            playground.getReady(this.team, this.id);
            playground.waitForAssertTrialDecision(this.team, this.id);
            contestantBench.seatDown(this.team,this.id);

        }

        /*
         * 
         * while(RefereeSite.referee.getState() != RefereeState.END_OF_THE_MATCH){
         * // TODO: Referee vai ao contestantBench e chama os concorrentes, tem uma flag
         * ou varias de outra flag
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
        System.out.println("pullTheRope: " + "Co" + id + "T" + team + " - " + strength);

        // wait for a random time
        try {
            sleep((long) (Math.random()));
        } catch (InterruptedException e) {
        }

        return this.strength--;
    }

    /**
     * Rest increases the strength of the Contestant
     * 
     */

    public void rest() {
        this.strength++;
    }
}
