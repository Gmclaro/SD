package entities;

import main.SimulParse;
import sharedRegions.*;

public class Referee extends Thread {

    /**
     * Referee State
     */
    private int state;

    /**
     * Reference to the Playground
     */
    private Playground playground;

    /**
     * Reference to the Contestant Bench
     */
    private ContestantBench contestantBench;

    /**
     * Reference to the Referee Site
     */
    private RefereeSite refereeSite;

    /**
     * Scores of the games
     */
    private int[] scores;

    /**
     * Referee instantiation
     * 
     * @param playground  Reference to the Playground
     * @param refereeSite Reference to the Referee Site
     */

    public Referee(Playground playground, RefereeSite refereeSite, ContestantBench contestantBench) {
        super("Referee()");
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.contestantBench = contestantBench;
        this.state = RefereeState.START_OF_THE_MATCH;
    }

    /**
     * Set the referee state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get the referee state
     */
    public int getEntityState() {
        return this.state;
    }

    /**
     * Referee life cycle
     * 
     * assertTrialDecision() == true -> trial is not over
     * assertTrialDecision() == false -> trial is over
     * 
     * ropePosition < 0 -> team 0 wins
     * ropePosition > 0 -> team 1 wins
     * ropePosition == 0 -> draw
     */
    @Override
    public void run() {
        System.out.println("Referee() has started.");
        int currentGame;
        int ropePosition;

        for (currentGame = 1; currentGame <= SimulParse.GAMES; currentGame++) {
            refereeSite.announceNewGame();
            do {
                contestantBench.callTrial();
                refereeSite.waitForInformReferee();
                playground.startTrial();
                playground.waitForAmDone();
            } while (playground.assertTrialDecision() == true);
            ropePosition = playground.declareGameWinner();

            if (ropePosition < 0)
                scores[0]++;
            else if (ropePosition > 0)
                scores[1]++;
            else {
            }
        }

        contestantBench.declareMatchWinner(scores);
    }
}