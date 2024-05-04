package serverSide.sharedRegions;

import commonInfra.View;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.*;
import serverSide.main.SimulParse;

/**
 * ContestantBench shared memory region.
 */
public class ContestantBench {

    /**
     * General Repository of Information
     */
    private final GeneralRepositoryStub repo;

    /**
     * Characteristics of the Contestants in the bench
     */
    private final View[][] contestants;

    /**
     * Array of Contestants that will play in the playground
     */
    private final int[][] playgroundQueue;

    /**
     * Number of Contestants in the bench for each team
     */
    private final int[] inBench;

    /**
     * Flag to indicate if the match is over
     */
    private boolean matchOver;

    /**
     * Flag to indicate if the referee called the trial
     */
    private int callTrial;

    /**
     * ContestantBench instantiation
     * 
     * @param repo General Repository of Information
     */
    public ContestantBench(GeneralRepositoryStub repo) {
        this.repo = repo;

        this.contestants = new View[2][SimulParse.CONTESTANT_PER_TEAM];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SimulParse.CONTESTANT_PER_TEAM; j++) {
                this.contestants[i][j] = new View(j, 0);
            }
        }

        this.playgroundQueue = new int[2][SimulParse.CONTESTANT_PER_TEAM];

        this.inBench = new int[] { 0, 0 };
        this.matchOver = true;
    }

    /**
     * Referee has announce a new trial.
     */
    public synchronized void callTrial() {
        this.matchOver = false;
        this.callTrial = 2;
        notifyAll();

        // TODO: MISSING STUB For generalRepo
        ((ContestantBenchClientProxy) Thread.currentThread()).setRefereeState(RefereeState.TEAMS_READY);
        repo.setRefereeState(RefereeState.TEAMS_READY);
        repo.setNewTrial();
    }

    /**
     * Coach is waiting for the referee to call the trial.
     * 
     * 0 if the match is over.
     * 1 if the trial was called.
     * 
     * @param team Team of the Coach
     * @return int order of the Coach in the playground
     */
    public synchronized int waitForCallTrial(int team) {
        ((ContestantBenchClientProxy) Thread.currentThread()).setCoachState(CoachState.WAIT_FOR_REFEREE_COMMAND);
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
        notifyAll();
        return 1;
    }

    /**
     * Coach calls the Contestants to play.
     * By default all Contestants are seated in the bench, only after the Coach
     * calls the chosen ones.
     * 
     * @param team     Team of the Coach
     * @param selected Ids of Contestants selected to play
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
     * @param team Team of the Contestant
     * @param id   Id of the Contestant
     * @return int Order of the Contestant in the playground
     */

    public int waitForCallContestants(int team, int id) {
        /**
         * Updates Contestant current state and placed the Contestant in Bench
         */
        ContestantBenchClientProxy contestant;
        synchronized (this) {
            contestant = (ContestantBenchClientProxy) Thread.currentThread();
            // contestant.setEntityState(ContestantState.SEAT_AT_THE_BENCH);

            // repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

            contestants[team][id].setValue(contestant.getStrength());

            // inBench[team]++;
            notifyAll();
        }

        /**
         * Contestants are seaten in the bench and wait to be called
         * 
         * order == 0 : Contestant was not selected yet
         * order == 1 : Contestant was selected to play
         * order == 2 : Contestant was selected to play and is already in the bench
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
                //TODO: uncomment REST
                //contestant.rest();
                System.out.println("ATENCAO FALTA O REST");
                contestants[team][id].setValue(contestant.getStrength() + 1);
                repo.setContestantStrength(team, id, contestant.getStrength() + 1);
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
     * @param team Team of the Contestant
     * @param id   Id of the Contestant
     */
    public synchronized void seatDown(int team, int id) {
        while (matchOver) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // TODO: ATUALIZAR A FORÇA DO CONTESTANT

        repo.setRemoveContestant(team, id);

        ContestantBenchClientProxy contestant = (ContestantBenchClientProxy) Thread.currentThread();
        contestant.setContestantState(ContestantState.SEAT_AT_THE_BENCH);

        repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

        contestants[team][id].setValue(contestant.getStrength());

        inBench[team]++;

        notifyAll();
    }

    /**
     * Coach gets all information about the Contestants
     * 
     * @param team Team of the Coach
     * @return View[] Array of Views with the Contestants information
     */
    public synchronized View[] reviewNotes(int team) {
        // while (inBench[team] < SimulParse.CONTESTANT_PER_TEAM) {
        // while ((inBench[0] + inBench[1]) < (2 * SimulParse.CONTESTANT_PER_TEAM)) {
        while ((inBench[0] < SimulParse.CONTESTANT_PER_TEAM) || (inBench[1] < SimulParse.CONTESTANT_PER_TEAM)) {
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
     * @param scores Scores of the match
     */
    public synchronized void declareMatchWinner(int[] scores) {
        matchOver = true;
        notifyAll();
        ((Referee) Thread.currentThread()).setEntityState(RefereeState.END_OF_THE_MATCH);
        repo.setRefereeState(RefereeState.END_OF_THE_MATCH);
        repo.setMatchWinner(scores);
    }

    /**
     * Contestant waits for the referee to declare the match winner
     */
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