package GameOfRope.entities;

import GameOfRope.sharedRegions.*;

public class Referee extends Thread {

    /**
     * Referee State
     */
    private int refereeState;

    /**
     * Reference to the Playground
     */
    private Playground playground;

    /**
     * Reference to the Referee Site
     */
    private RefereeSite refereeSite;

    /**
     * Referee instantiation
     * 
     * @param playground  Reference to the Playground
     * @param refereeSite Reference to the Referee Site
     */

    public Referee(Playground playground, RefereeSite refereeSite) {
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.refereeState = RefereeState.START_OF_THE_MATCH;
    }

    /**
     * Set the referee state
     */
    public void setRefereeState(int state) {
        this.refereeState = state;
    }

    /**
     * Get the referee state
     */
    public int getRefereeState() {
        return this.refereeState;
    }

    /**
     * Referee life cycle
     */
    @Override
    public void run() {
        // TODO: implement referee life cycle
        /**
         * announceNewGame();
         * for(int n = 0; n < nGames; n++) {
         *  do {
         *   callTrial();
         *   startTrial();
         *  } while(assertTrialDecision() == 'c');
         * }
         * declareMatchWinner();
         */

        while(state != RefereeState.END_OF_THE_MATCH) {
            switch(state){

                case RefereeState.START_OF_THE_MATCH:
                    refereeSite.announceNewGame();
                    break;

                case RefereeState.START_OF_A_GAME:
                    playground.callTrial();
                    break;

                case RefereeState.TEAMS_READY:
                    playground.startTrial();
                    break;

                case RefereeState.WAIT_FOR_TRIAL_CONCLUSION:
                    playground.assertTrialDecision();
                    break;
                    // TODO :need to implement if the game is over (create a function to check if the game is ended)

                case RefereeState.END_OF_A_GAME:
                    playground.declareMatchWinner();
                    // TODO :need to implement if the match is over (create a function to check if the match is ended)
                    break;

                case RefereeState.END_OF_THE_MATCH:
                    break;
            }
        }
    }
}