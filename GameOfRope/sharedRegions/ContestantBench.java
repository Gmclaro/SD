package sharedRegions;

import commonInfra.View;
import entities.*;
import main.SimulParse;

public class ContestantBench {

    private final GeneralRepository repo;

    private final View[][] contestants;
    private final int[][] playgroundQueue;
    private final int[] inBench;

    private boolean matchOver;
    private int callTrial;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;

        this.contestants = new View[2][SimulParse.CONTESTANT_PER_TEAM];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
                this.contestants[i][j] = new View(j, 0);
            }
        }

        this.playgroundQueue = new int[2][SimulParse.CONTESTANT_PER_TEAM];

        this.inBench = new int[] { 0, 0 };
        this.matchOver = false;
    }

    /**
     * Referee is announce a new trial
     */
    public synchronized void callTrial() {
        this.callTrial = 2;
        notifyAll();

        ((Referee) Thread.currentThread()).setEntityState(RefereeState.TEAMS_READY);
        repo.setRefereeState(RefereeState.TEAMS_READY);
        repo.setNewTrial();
    }

    /**
     * Coach is waiting for the referee to call the trial
     * 
     * @param team
     * @return
     */
    public synchronized int waitForCallTrial(int team) {
        ((Coach) Thread.currentThread()).setEntityState(CoachState.WAIT_FOR_REFEREE_COMMAND);
        repo.setCoachState(team, CoachState.WAIT_FOR_REFEREE_COMMAND);

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

            // inBench[team]++;
            notifyAll();
        }

        /**
         * Contestants are seaten in the bench and wait to be called
         * 
         * order == 0 -> Contestant was not selected yet
         * order == 1 -> Contestant was selected to play
         * order == 2 -> Contestant was selected to play and is already in the bench
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

            int order = playgroundQueue[team][id];
            if (order == 2) {
                inBench[team]--;
            }

            if (order == 1) {
                contestant.rest();
                contestants[team][id].setValue(contestant.getStrength());
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

        repo.setRemoveContestant(team, id);
        notifyAll();
    }

    /**
     * Coach gets all information about the Contestants
     * 
     */
    public synchronized View[] reviewNotes(int team) {
        while (inBench[team] < SimulParse.CONTESTANT_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return contestants[team];
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

    public synchronized void waitForSeatAtBench() {

        while (inBench[0] < SimulParse.CONTESTANT_PER_TEAM || inBench[1] < SimulParse.CONTESTANT_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

}