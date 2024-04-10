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
     * Referee Constructor
     * 
     * @param playground      Reference to the Playground
     * @param refereeSite     Reference to the Referee Site
     * @param contestantBench Reference to the Contestant Bench
     */

    public Referee(Playground playground, RefereeSite refereeSite, ContestantBench contestantBench) {
        super("Referee()");
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.contestantBench = contestantBench;
        this.state = -1;

        this.scores = new int[] { 0, 0 };
    }

    /**
     * Name of the Thread
     * 
     * @return String Thread name
     */

    public String whoAmI() {
        return "Referee()";
    }

    /**
     * Set the referee state
     * 
     * @param state referee state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get the referee state
     * 
     * @return int referee state
     */
    public int getEntityState() {
        return this.state;
    }

    /**
     * Referee life cycle
     * 
     * assertTrialDecision() == true : trial is not over.
     * 
     * assertTrialDecision() == false : trial is over.
     * 
     * ropePosition if greater than 0 : team 0 wins.
     * 
     * ropePosition if lesser than 0 : team 1 wins.
     * 
     * ropePosition if equal to 0 : draw.
     */
    @Override
    public void run() {
        System.out.println(this.whoAmI() + " has started.");
        int currentGame;
        int ropePosition;

        for (currentGame = 1; currentGame <= SimulParse.GAMES; currentGame++) {
            System.out.println("Game: " + currentGame
                    + " ----------------------------------------------------------");

            refereeSite.announceNewGame();
            System.out.println(this.whoAmI() + " -> announceNewGame()");

            boolean continueGame;
            int currentTrial = 0;

            do {

                contestantBench.callTrial();
                System.out.println(
                        "Trial " + (++currentTrial) + ": ----------------------------------------------------------");
                System.out.println(this.whoAmI() + " -> callTrial()");

                refereeSite.waitForInformReferee();
                System.out.println(this.whoAmI() + " -> waitForInformReferee()");

                playground.startTrial();
                System.out.println(this.whoAmI() + " -> startTrial()");

                playground.waitForAmDone();
                System.out.println(this.whoAmI() + " -> waitForAmDone()");

                continueGame = playground.assertTrialDecision();
                System.out.println(this.whoAmI() + " -> assertTrialDecision()");
            } while (continueGame);

            contestantBench.waitForSeatAtBench();

            ropePosition = playground.declareGameWinner();
            System.out.println(this.whoAmI() + " -> declareGameWinner()");

            if (ropePosition > 0)
                scores[0]++;
            else if (ropePosition < 0)
                scores[1]++;
            else {
            }
        }

        contestantBench.declareMatchWinner(scores);
        System.out.println(this.whoAmI() + " -> declareMatchWinner()");

    }
}