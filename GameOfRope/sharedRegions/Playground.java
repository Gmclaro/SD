package sharedRegions;

import entities.*;
import main.SimulParse;

import commonInfra.View;

public class Playground {

    private final GeneralRepository repo;

    private final View[][] contestants;

    private final int[] arrivedContestants;

    // TODO: change name of this var
    private int nOfAmDone;

    private int playedTrial;

    private final int[] strengthPerTeam;

    /**
     * Flags
     * 
     * It is used to control the flow of the game
     */
    private boolean startOfTrial;
    private boolean endOfTrial;

    // TODO: Constructor -> check if it's missing something
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
     * @param team
     */
    public synchronized void followCoachAdvice(int team) {
        arrivedContestants[team]++;
        notifyAll();
    }

    public synchronized void waitForFollowCoachAdvice(int team) {
        ((Coach) Thread.currentThread()).setEntityState(CoachState.ASSEMBLE_TEAM);
        repo.setCoachState(team,CoachState.ASSEMBLE_TEAM);
        
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
     * 
     */
    public synchronized void startTrial() {
        ((Referee)Thread.currentThread()).setEntityState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
        repo.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        startOfTrial = true;
        endOfTrial = false;
        playedTrial++;
        notifyAll();
    }

    /**
     * Contestants wait for the trial to start
     * 
     * @param team
     * @param id
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

    public synchronized void getReady(int team, int id) {
        repo.setActiveContestant(team, id);
    }

    public synchronized void amDone() {
        nOfAmDone++;
        notifyAll();
    }

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
     * false -> trial has ended
     * true -> trial has not ended
     * 
     * @return boolean
     */
    public synchronized boolean assertTrialDecision() {
        endOfTrial = true;

        int result = Math.abs(strengthPerTeam[0] - strengthPerTeam[1]);

        repo.setRopePosition(result);

        /**
         * Referee will check if the trial was a knockout or if the trial limit was met
         */
        if (playedTrial >= SimulParse.TRIALS
                || ( result>= SimulParse.KNOCKOUT)) {
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
     * @param team
     * @param id
     * @param strength
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
     * @param team
     */
    public void waitForAssertTrialDecision(int team) {
        // TODO: Missing implementation

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
     * @return int
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
