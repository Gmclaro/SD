package GameOfRope.entities;

import GameOfRope.sharedRegions.*;

public class Coach extends Thread {
    /**
     * Coach ID
     */

    private int coachId;

    /**
     * Coach State
     */

    private int coachState;

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

    public void setCoachState(int state) {
        this.coachState = state;
    }

    /**
     * Get the coach state
     */

    public int getCoachState() {
        return this.coachState;
    }

    /**
     * Get the coach ID
     */

    public int getcoachId() {
        return this.coachId;
    }

    /**
     * Get the coach team
     */

    public int getcoachTeamID() {
        return this.teamId;
    }

    /**
     * Coach instantiation
     * 
     * @param coachID
     */

    public Coach(int coachId, ContestantBench contestantBench, Playground playground, RefereeSite refereeSite) {
        super("Coach_" + coachId);
        this.coachId = coachId;
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
        /**
         * while(!endOfTheMatch()) {
         *  callContestants();
         *  waitForFollowCoachAdvice -> with while inside of it in the playground
         *  informReferee();
         *  reviewNotes();
         * }
         */

        // TODO create a function to instantiate the teams and wait for next trial
         ContestantBench.getInstance().waitforNextTrial();

         while(!RefereeSite.getInstance().endOfTheMatch()) {
            switch(coachState){
                case CoachState.WAIT_FOR_REFEREE_COMMAND:
                    playground.callContestants();
                    break;
                case CoachState.ASSEMBLE_TEAM:
                    Playground.informReferee();
                    break;
                case CoachState.WATCH_TRIAL:
                    reviewNotes();
                    break;
            }
        }
    }


    /**
     * Based on the information from the match defines which is the next strategy to the team
     */
    public void reviewNotes() {
        // TODO : implement reviewNotes
    }



}
