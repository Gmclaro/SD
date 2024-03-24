package GameOfRope.entities;

import GameOfRope.sharedRegions.*;

public class Coach extends Thread {
    /**
     * Coach ID
     */

    private int team;

    /**
     * Coach State
     */

    private int state;

    /**
     * Reference to the Contestants Bench
     */

    private ContestantBench contestantBench;

    /**
     * Reference to the Playground
     */

    private Playground playground;

    /**
     * Reference to the Referee Site
     */

    private RefereeSite refereeSite;

    /**
     * Set the coach state
     */

    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get the coach state
     */

    public int getEntityState() {
        return this.state;
    }

    /**
     * Set the coach team
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * Get the coach team
     */

    public int getTeam() {
        return this.team;
    }

    /**
     * Coach instantiation
     * 
     * @param coachID
     */

    public Coach(int team, ContestantBench contestantBench, Playground playground, RefereeSite refereeSite) {
        super("Coach_" + coachId);
        this.team = team;
        this.coachState = CoachState.WAIT_FOR_REFEREE_COMMAND;
        this.contestantBench = contestantBench;
        this.playground = playground;
        this.refereeSite = refereeSite;
    }

    /**
     * Coach life cycle
     */
    @Override
    public void run() {
        // TODO create a function to instantiate the teams and wait for next trial

        /**
         * selected = {1,2,3}
         * while(RefereeSite.referee.getState() != RefereeState.END_OF_THE_MATCH) {
         * contestantBench.callContestants(selected,teamID);
         * // TODO: waitForFollowCoachAdvice -> with while inside of it in the
         * playground
         * refereeSite.informReferee();
         * selected = reviewNotes();
         * }
         */

    }

    /**
     * Based on the information from the match defines which is the next strategy to
     * the team
     */
    public int[] reviewNotes() {
        // TODO : implement reviewNotes
        return null;
    }

}
