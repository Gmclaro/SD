package clientSide.entities;

import interfaces.ContestantBenchInterface;
import interfaces.PlaygroundInterface;
import interfaces.RefereeSiteInterface;
import interfaces.ReturnInt;
import serverSide.main.SimulParse;

/**
 * Custom Thread
 * It simulates the life cycle of a referee
 * It will be a static solution
 */

public class Referee extends Thread {

    /**
     * Referee State
     */
    private int state;

    /**
     * Reference to the Playground
     */
    private PlaygroundInterface playgroundStub;

    /**
     * Reference to the Contestant Bench
     */
    private ContestantBenchInterface contestantBenchStub;

    /**
     * Reference to the Referee Site
     */
    private RefereeSiteInterface refereeSiteStub;

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

    public Referee(PlaygroundInterface playgroundStub, RefereeSiteInterface refereeSiteStub,
            ContestantBenchInterface contestantBenchStub) {
        super("Referee()");
        this.playgroundStub = playgroundStub;
        this.refereeSiteStub = refereeSiteStub;
        this.contestantBenchStub = contestantBenchStub;
        this.state = RefereeState.START_OF_THE_MATCH;

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

            announceNewGame();
            System.out.println(this.whoAmI() + " -> announceNewGame()");

            boolean continueGame;
            int currentTrial = 0;

            do {

                callTrial();
                System.out.println(
                        "Trial " + (++currentTrial) + ": ----------------------------------------------------------");
                System.out.println(this.whoAmI() + " -> callTrial()");

                waitForInformReferee();
                System.out.println(this.whoAmI() + " -> waitForInformReferee()");

                startTrial();
                System.out.println(this.whoAmI() + " -> startTrial()");

                waitForAmDone();
                System.out.println(this.whoAmI() + " -> waitForAmDone()");

                continueGame = assertTrialDecision();
                System.out.println(this.whoAmI() + " -> assertTrialDecision()");
            } while (continueGame);

            waitForSeatAtBench();
            System.out.println(this.whoAmI() + " -> waitForSeatAtBench()");

            ropePosition = declareGameWinner();
            System.out.println(this.whoAmI() + " -> declareGameWinner()");

            if (ropePosition > 0)
                scores[0]++;
            else if (ropePosition < 0)
                scores[1]++;
            else {
            }
        }

        declareMatchWinner();
        System.out.println(this.whoAmI() + " -> declareMatchWinner()");

    }

    /**
     * The referee announces a new game
     */

    private void announceNewGame() {
        try {
            state = refereeSiteStub.announceNewGame();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Operation waitForInformReferee
     * Remote Operation
     * The referee waits for the coaches to inform the teams are ready
     */

    private void waitForInformReferee() {
        try {
            refereeSiteStub.waitForInformReferee();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Operation callTrial
     * Remote Operation
     * The referee calls the trial
     */

    private void callTrial() {
        try {
            state = contestantBenchStub.callTrial();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Operation startTrial
     * Remote Operation
     * The referee starts the trial
     */

    private void startTrial() {
        try {
            state = playgroundStub.startTrial();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Operation waitForAmDone
     * Remote Operation
     * The referee waits for the contestants to finish the trial
     */

    private void waitForAmDone() {
        try {
            playgroundStub.waitForAmDone();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Operation assertTrialDecision
     * Remote Operation
     * The referee asserts the decision of the trial
     * 
     * @return boolean true if the trial is not over, false otherwise
     */

     private boolean assertTrialDecision(){
        boolean result = false;

        try {
            result = playgroundStub.assertTrialDecision();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        return result;
     }

    /**
     * Operation waitForSeatAtBench
     * Remote Operation
     * The referee waits for the contestants to seat at the bench
     */

    private void waitForSeatAtBench() {
        try {
            contestantBenchStub.waitForSeatAtBench();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Operation declareGameWinner
     * Remote Operation
     * The referee declares the winner of the game
     * 
     * @return int the position of the rope
     */
    private int declareGameWinner() {
        ReturnInt result = null;

        try {
            result = playgroundStub.declareGameWinner();
            setEntityState(result.getIntStateVal());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        return result.getIntVal();
    }

    /**
     * Operation declareMatchWinner
     * Remote Operation
     * The referee declares the winner of the match
     * 
     */

    private void declareMatchWinner() {
        try {
            state = contestantBenchStub.declareMatchWinner(scores);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    



}