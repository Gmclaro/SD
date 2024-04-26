package clientSide.entities;

import clientSide.stubs.ContestantBenchStub;
import clientSide.stubs.PlaygroundStub;

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
     * Playground shared memory region declaration
     */
    private final PlaygroundStub playground;

    /**
     * ContestantBench shared memory region declaration
     */
    private final ContestantBenchStub contestantBench;

    /**
     * Contestant Constructor
     * 
     * @param team               team
     * @param id                 Contestant id
     * @param contestantStrength Contestant strength
     * @param playground         reference to playground
     * @param contestantBench    reference to contestantBench
     */
    public Contestant(int team, int id, int contestantStrength, PlaygroundStub playground,
            ContestantBenchStub contestantBench) {
        super("Contestant(T" + team + "," + id + ")");
        this.id = id;
        this.team = team;
        this.strength = contestantStrength;
        this.state = -1;

        // shared regions
        this.playground = playground;
        this.contestantBench = contestantBench;
    }

    /**
     * Name of the Thread
     * 
     * @return String Thread name
     */

    public String whoAmI() {
        return "Contestant(T" + team + "," + id + ")";
    }

    /**
     * Set contestant id
     * 
     * @param id contestant id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get contestant id
     * 
     * @return int contestant id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get team
     * 
     * @return int team
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * Set team
     * 
     * @param team team
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * Set contestant state
     * 
     * @param state contestant state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get contestant state
     * 
     * @return int contestant state
     */
    public int getEntityState() {
        return this.state;
    }

    /**
     * Get strength of the contestant
     * 
     * @return int strength of the contestant
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Life cycle of the contestant
     */
    @Override
    public void run() {
        System.out.println(this.whoAmI() + " has started.");
        // contestantBench.seatDown(this.team, this.id);
        // System.out.println(this.whoAmI() + " -> seatDown()");

        // int orders;
        // while (true) {
        //     orders = contestantBench.waitForCallContestant(team, id);
        //     System.out.println(this.whoAmI() + " -> waitForCallContestant()");

        //     switch (orders) {
        //         case 0:
        //             return; // match is over and contestant thread is done
        //         case 1:
        //             continue; // contestant was not selected, rest
        //         case 2:
        //             break; // contestant was selected, go to playground and continue the lifecycle
        //     }

        //     playground.followCoachAdvice(this.team);
        //     System.out.println(this.whoAmI() + " -> followCoachAdvice()");

        //     playground.waitForStartTrial(this.team, this.id);
        //     System.out.println(this.whoAmI() + " -> waitForStartTrial()");

        //     playground.getReady(this.team, this.id);
        //     System.out.println(this.whoAmI() + " -> getReady()");

        //     playground.waitForAssertTrialDecision(this.team, this.id);
        //     System.out.println(this.whoAmI() + " -> waitForAssertTrialDecision()");

        //     contestantBench.seatDown(this.team, this.id);
        //     System.out.println(this.whoAmI() + " -> seatDown()");
        // }
    }

    /**
     * Pull the rope
     * 
     * Internal operation
     * 
     * @return int strength of the contestant
     */
    public int pullTheRope() {
        return this.strength--;
    }

    /**
     * Rest increases the strength of the Contestant
     */

    public void rest() {
        this.strength++;
    }
}
