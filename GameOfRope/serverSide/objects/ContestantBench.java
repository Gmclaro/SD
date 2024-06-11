package serverSide.objects;

import clientSide.entities.CoachState;
import clientSide.entities.ContestantState;
import clientSide.entities.Referee;
import clientSide.entities.RefereeState;
import commonInfra.View;
import interfaces.*;
import serverSide.main.ServerGameOfRopeContestantBench;
import serverSide.main.SimulParse;
import java.rmi.*;

/**
 * ContestantBench shared memory region.
 */
public class ContestantBench implements ContestantBenchInterface {

    /**
     * General Repository of Information
     */
    private final GeneralRepositoryInterface repo;

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

    private int nEntities = 0;

    /**
     * ContestantBench instantiation
     * 
     * @param repo General Repository of Information
     */
    public ContestantBench(GeneralRepositoryInterface repo) {
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
     * 
     * @return int Referee State
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized int callTrial() throws RemoteException{
        this.matchOver = false;
        this.callTrial = 2;
        notifyAll();

        repo.setRefereeState(RefereeState.TEAMS_READY);
        repo.setNewTrial();

        return RefereeState.TEAMS_READY;
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
    public synchronized ReturnInt waitForCallTrial(int team) throws RemoteException{
        repo.setCoachState(team, CoachState.WAIT_FOR_REFEREE_COMMAND);

        while (!matchOver && callTrial == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (matchOver) {
            return new ReturnInt(0, CoachState.WAIT_FOR_REFEREE_COMMAND);
        }

        callTrial--;
        notifyAll();
        return new ReturnInt(1, CoachState.WAIT_FOR_REFEREE_COMMAND);
    }

    /**
     * Coach calls the Contestants to play.
     * By default all Contestants are seated in the bench, only after the Coach
     * calls the chosen ones.
     * 
     * @param team     Team of the Coach
     * @param selected Ids of Contestants selected to play
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized void callContestants(int team, int[] selected) throws RemoteException{
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
     * @param strength Strength of the Contestant
     * @return int Order of the Contestant in the playground
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public int waitForCallContestants(int team, int id, int strength) throws RemoteException {
        /**
         * Updates Contestant current state and placed the Contestant in Bench
         */
        synchronized (this) {

            // repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

            contestants[team][id].setValue(strength);

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
                contestants[team][id].setValue(strength + 1);
                repo.setContestantStrength(team, id, strength + 1);
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
     * @param strength Strength of the Contestant
     * 
     * @return int Contestant State
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized int seatDown(int team, int id,int strength) throws RemoteException {
        while (matchOver) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        repo.setContestantState(team, id, ContestantState.SEAT_AT_THE_BENCH);

        contestants[team][id].setValue(strength);

        inBench[team]++;

        repo.setRemoveContestant(team, id);

        notifyAll();
        return ContestantState.SEAT_AT_THE_BENCH;
    }

    /**
     * Coach gets all information about the Contestants
     * 
     * @param team Team of the Coach
     * @return View[] Array of Views with the Contestants information
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized ReturnInt[] reviewNotes(int team) throws RemoteException{
        // while (inBench[team] < SimulParse.CONTESTANT_PER_TEAM) {
        // while ((inBench[0] + inBench[1]) < (2 * SimulParse.CONTESTANT_PER_TEAM)) {
        while ((inBench[0] < SimulParse.CONTESTANT_PER_TEAM) || (inBench[1] < SimulParse.CONTESTANT_PER_TEAM)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        ReturnInt[] returnContestants = new ReturnInt[SimulParse.CONTESTANT_PER_TEAM];
        for (int i = 0; i < SimulParse.CONTESTANT_PER_TEAM; i++) {
            returnContestants[i] = new ReturnInt(contestants[team][i].getKey(), contestants[team][i].getValue());
        }
        return returnContestants;
    }

    /**
     * The referee declares the match winner
     * 
     * Reset the matchOver flag and notify all the threads
     * 
     * @param scores Scores of the match
     * 
     * @return int Referee State
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized int declareMatchWinner(int[] scores) throws RemoteException{
        matchOver = true;
        notifyAll();
        
        repo.setRefereeState(RefereeState.END_OF_THE_MATCH);
        repo.setMatchWinner(scores);

        return RefereeState.END_OF_THE_MATCH;
    }

    /**
     * Contestant waits for the referee to declare the match winner
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized void waitForSeatAtBench() throws RemoteException{

        while (inBench[0] < SimulParse.CONTESTANT_PER_TEAM || inBench[1] < SimulParse.CONTESTANT_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Operation server shutdown.
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public synchronized void shutdown() throws RemoteException{
        nEntities += 1;
        // the contestantas just to shut down all at the same time
        if (nEntities >= 3) {
            ServerGameOfRopeContestantBench.shutdown();
        }
        notifyAll();
    }

    
}