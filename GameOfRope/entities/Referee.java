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
    }
}