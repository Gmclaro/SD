package sharedRegions;

import commonInfra.View;
import entities.*;
import main.SimulParse;

public class ContestantBench {

    private final GeneralRepository repo;
    // private final Playground playground;

    // TODO: Instead of using Contestant reference, use the content of each one, and
    // only change it not the "person" itself
    private final View[][] contestants;
    private final int[][] playgroundQueue;
    private final int[] inBench;

    private boolean matchOver;
    private int callTrial;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;

        this.contestants = new View[2][SimulParse.CONTESTANT_PER_TEAM];
        this.playgroundQueue = new int[2][SimulParse.CONTESTANT_PER_TEAM];

        this.inBench = new int[] { 0, 0 };
        this.matchOver = false;
    }

    /**
     * Referee is announce a new trial
     */
    public synchronized void callTrial() {
        // TODO: nao me lembro o que faz isto
        this.callTrial = 2;
        notifyAll();

        ((Referee) Thread.currentThread()).setEntityState(RefereeState.TEAMS_READY);
        repo.setRefereeState(RefereeState.TEAMS_READY);
    }

    /**
     * Coach is waiting for the referee to call the trial
     * 
     * @param team
     * @return
     */
    public int waitForCallTrial(int team) {
        ((Coach) Thread.currentThread()).setEntityState(CoachState.WAIT_FOR_REFEREE_COMMAND);
        repo.setCoachState(team, CoachState.WAIT_FOR_REFEREE_COMMAND);

        // TODO: o que esta merda do callTrial faz
        while (!matchOver && callTrial == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (matchOver) {
            return 0;
        }

        callTrial--;
        return 1;
    }

    /**
     * Coach calls the Contestants to play.
     * By default all Contestants are seated in the bench, only after the Coach
     * calls the chosen ones.
     * 
     * @param team
     * @param selected
     */
    public synchronized void callContestants(int team, int[] selected) {
        for (int i = 0; i < SimulParse.CONTESTANT_PER_TEAM; i++)
            playgroundQueue[team][i] = 1;
        for (int id : selected)
            playgroundQueue[team][id] = 2;
        notifyAll();
    }

    /**
     * Place Contestant in the bench, and wait to be called
     * 
     * @param team
     * @param id
     * @return
     */

    public int waitForCallContestant(int team, int id) {
        /**
         * Updates Contestant current state and placed the Contestant in Bench
         */
        Contestant contestant;
        synchronized (this) {
            contestant = (Contestant) Thread.currentThread();
            contestant.setEntityState(ContestantState.SEAT_AT_THE_BENCH);

            repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

            contestants[team][id].setValue(contestant.getStrength());

            inBench[team]++;
            notifyAll();
        }

        /**
         * Contestants are seaten in the bench and wait to be called
         * 
         * order == 0 -> Contestant was not selected yet
         * order == 1 -> Contestant was selected to play
         */
        synchronized (this) {
            while (!matchOver && playgroundQueue[team][id] == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /**
             * Since match is over, Contestant Thread will be ended.
             */
            if (matchOver) {
                return 0;
            }

            /**
             * Contestant was selected to play
             */
            inBench[team]--;
            int order = playgroundQueue[team][id];
            // TODO: O que e esta merda
            if (order == 1) {
                contestant.rest();
                repo.setContestantStrength(team, id, contestant.getStrength());
            }

            /**
             * Restart the life cycle of the Contestant
             */
            playgroundQueue[team][id] = 0;
            return order;
        }

    }

    /**
     * Contestant is placed in the bench
     * 
     * @param team
     * @param id
     */
    public synchronized void seatDown(int team, int id) {
        Contestant contestant = (Contestant) Thread.currentThread();
        contestant.setEntityState(ContestantState.SEAT_AT_THE_BENCH);

        repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

        contestants[team][id].setValue(contestant.getStrength());

        inBench[team]++;
        notifyAll();
    }

    /**
     * The referee declares the match winner
     * 
     * Reset the matchOver flag and notify all the threads
     * 
     * @param scores
     */
    public synchronized void declareMatchWinner(int[] scores) {
        matchOver = true;
        notifyAll();
        repo.setMatchWinner(scores);
        ((Referee) Thread.currentThread()).setEntityState(RefereeState.END_OF_THE_MATCH);
        repo.setRefereeState(RefereeState.END_OF_THE_MATCH);
    }

    public View[] getBench(int team) {
        return contestants[team];
    }

}