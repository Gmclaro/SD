package sharedRegions;

import entities.*;
import main.SimulParse;

import commonInfra.View;

public class Playground {

    /**
     * Reference to the General Repository
     */

    private final GeneralRepository repo;

    /**
     * Characteristics of contestants in the playground
     */

    private final View[][] contestants;

    /**
     * Contestants in the playground
     */

    private final int[] arrivedContestants;

    /**
     * Number of contestants that have finished the trial
     */
    private int nOfAmDone;

    /**
     * Number of trials played
     */

    private int playedTrial;

    /**
     * Strength of the teams
     */

    private final int[] strengthPerTeam;

    /*
     * Flags
     * 
     * It is used to control the flow of the game
     */

    /**
     * Flag that indicates the start of the trial
     */
    private boolean startOfTrial;
    /**
     * Flag that indicates the end of the trial
     */
    private boolean endOfTrial;

    /**
     * Playground instantiation
     * 
     * @param repo Reference to the General Repository
     */

    public Playground(GeneralRepository repo) {
        this.repo = repo;
        contestants = new View[2][SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM];

        // during the game
        arrivedContestants = new int[] { 0, 0 };
        nOfAmDone = 0;
        strengthPerTeam = new int[2];

        // Flags
        startOfTrial = false;
        endOfTrial = false;
    }

    /**
     * Contestants go to the playground and inform when they have arrived
     * 
     * @param team The team of the contestant
     */
    public synchronized void followCoachAdvice(int team) {
        arrivedContestants[team]++;
        notifyAll();
    }

    /**
     * Coaches wait for the contestants to arrive at the playground
     * 
     * @param team The team of the coach
     */

    public synchronized void waitForFollowCoachAdvice(int team) {
        ((Coach) Thread.currentThread()).setEntityState(CoachState.ASSEMBLE_TEAM);
        repo.setCoachState(team, CoachState.ASSEMBLE_TEAM);

        while (arrivedContestants[team] < SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        arrivedContestants[team] = 0;
    }

    /**
     * The Referee will start the trial
     */
    public synchronized void startTrial() {
        ((Referee) Thread.currentThread()).setEntityState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
        repo.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        startOfTrial = true;
        endOfTrial = false;
        playedTrial++;
        notifyAll();
    }

    /**
     * Contestants wait for the trial to start
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     */
    public synchronized void waitForStartTrial(int team, int id) {
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.STAND_IN_POSITION);

        repo.setContestantState(team, id, ContestantState.STAND_IN_POSITION);

        /**
         * While the trial is not started, the contestants wait in the playground
         */
        while (!startOfTrial) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Contestants get ready to start the trial
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     */

    public synchronized void getReady(int team, int id) {
        repo.setActiveContestant(team, id);
    }

    /**
     * Contestants inform that they are done pulling the rope
     */
    public synchronized void amDone() {
        nOfAmDone++;
        notifyAll();
    }

    /**
     * Contestants wait for the decision of the referee
     */

    public synchronized void waitForAmDone() {
        while (nOfAmDone < 2 * SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        nOfAmDone = 0;
    }

    /**
     * Referee will compare the strength of the teams and decide if the trial has
     * ended.
     * 
     * false : trial has ended
     * true : trial has not ended
     * 
     * @return boolean The decision of the referee
     */
    public synchronized boolean assertTrialDecision() {
        endOfTrial = true;

        int result = Math.abs(strengthPerTeam[0] - strengthPerTeam[1]);

        repo.setRopePosition(result);

        /**
         * Referee will check if the trial was a knockout or if the trial limit was met
         */
        if (playedTrial >= SimulParse.TRIALS
                || (result >= SimulParse.KNOCKOUT)) {
            playedTrial = 0;
            notifyAll();
            return false;
        }

        return true;
    }

    /**
     * Contestants will do trial lifecycle, where he pulls the rope, inform that he
     * is done and wait for the decision of referee
     * 
     * @param team     The coach team
     * @param id       The id of the contestants
     * @param strength The strength of the contestants
     */

    public void waitForAssertTrialDecision(int team, int id) {
        /**
         * Contestant pulls the rope
         */
        Contestant contestant;
        synchronized (this) {
            contestant = (Contestant) Thread.currentThread();
            contestant.setEntityState(ContestantState.DO_YOUR_BEST);

            repo.setContestantState(team, id, ContestantState.DO_YOUR_BEST);
            strengthPerTeam[team] += contestant.pullTheRope();
        }

        /**
         * Contestant sleeps for a random time
         */
        try {
            contestant.sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * Contestant informs that he is done pulling the rope
         */
        synchronized (this) {

            while (!endOfTrial) {
                amDone();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            notifyAll();
        }

    }

    /**
     * Coach will wait until the decision of the referee of the trial
     * 
     * @param team The team of the coach
     */
    public void waitForAssertTrialDecision(int team) {
        synchronized (this) {
            ((Coach) Thread.currentThread()).setEntityState(CoachState.WATCH_TRIAL);
            repo.setCoachState(team, CoachState.WATCH_TRIAL);

            while (!endOfTrial) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * The referee will declare the winner of the game
     * 
     * @return int The difference of strength between the teams
     */
    public synchronized int declareGameWinner() {
        ((Referee) Thread.currentThread()).setEntityState(RefereeState.END_OF_A_GAME);
        repo.setRefereeState(RefereeState.END_OF_A_GAME);

        int difference = strengthPerTeam[0] - strengthPerTeam[1];

        repo.setEndOfGame();

        repo.showGameResult(difference);

        for (int i = 0; i < SimulParse.COACH; i++) {
            strengthPerTeam[i] = 0;
        }

        return difference;
    }

}
