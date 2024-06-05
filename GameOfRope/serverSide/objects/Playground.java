package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.CoachState;
import clientSide.entities.ContestantState;
import clientSide.entities.RefereeState;
import commonInfra.View;
import interfaces.*;
import serverSide.main.ServerGameOfRopePlayground;
import serverSide.main.SimulParse;

/**
 * Playground
 * Shared Memory Region where the game takes place
 */

public class Playground implements PlaygroundInterface{

    /**
     * Reference to the General Repository
     */

    private final GeneralRepositoryInterface repo;

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

    /**
     * Rope Position
     */
    private int ropePosition;

    /**
     * Number of contestants that are ready to start the trial
     */

    private int nOfGetReady;

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

    private int nEntities = 0;

    /**
     * Playground instantiation
     * 
     * @param repo Reference to the General Repository
     */

    public Playground(GeneralRepositoryInterface repo) {
        this.repo = repo;
        contestants = new View[2][SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM];

        // during the game
        arrivedContestants = new int[] { 0, 0 };
        this.nOfAmDone = 0;
        strengthPerTeam = new int[2];
        ropePosition = 0;
        nOfGetReady = 0;

        // Flags
        startOfTrial = false;
        endOfTrial = false;
    }

    /**
     * Contestants go to the playground and inform when they have arrived
     * 
     * @param team The team of the contestant
     */
    public synchronized void followCoachAdvice(int team) throws RemoteException{
        arrivedContestants[team]++;
        notifyAll();
    }

    /**
     * Coaches wait for the contestants to arrive at the playground
     * 
     * @param team The team of the coach
     */

    public synchronized int waitForFollowCoachAdvice(int team) throws RemoteException{
        repo.setCoachState(team, CoachState.ASSEMBLE_TEAM);

        while (arrivedContestants[team] < SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        // arrivedContestants[team] = 0;
        return CoachState.ASSEMBLE_TEAM;
    }

    /**
     * The Referee will start the trial
     */
    public synchronized int startTrial() throws RemoteException{
        repo.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        startOfTrial = true;
        endOfTrial = false;
        playedTrial++;
        notifyAll();
        return RefereeState.WAIT_FOR_TRIAL_CONCLUSION;
    }

    /**
     * Contestants wait for the trial to start
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     */
    public synchronized int waitForStartTrial(int team, int id) throws RemoteException{

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

        nOfGetReady++;
        notifyAll();

        return ContestantState.STAND_IN_POSITION;

    }

    /**
     * Contestants get ready to start the trial
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     */

    public synchronized void getReady(int team, int id)  throws RemoteException{
        while (nOfGetReady < (2 * SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notifyAll();
        repo.setActiveContestant(team, id);
    }

    /**
     * Contestants inform that they are done pulling the rope
     */
    public synchronized void amDone() throws RemoteException{
        this.nOfAmDone++;
        notifyAll();
    }

    /**
     * Contestants wait for the decision of the referee
     */

    public synchronized void waitForAmDone() throws RemoteException{
        while (nOfAmDone < (2 * SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.nOfAmDone = 0;
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
    public synchronized boolean assertTrialDecision() throws RemoteException{
        nOfGetReady = 0;
        endOfTrial = true;
        notifyAll();

        ropePosition += strengthPerTeam[0] - strengthPerTeam[1];

        repo.setRopePosition(ropePosition);

        /**
         * Referee will check if the trial was a knockout or if the trial limit was met
         */
        if (playedTrial >= SimulParse.TRIALS
                || (Math.abs(ropePosition) >= SimulParse.KNOCKOUT)) {
            playedTrial = 0;
            notifyAll();
            return false;
        }

        strengthPerTeam[0] = 0;
        strengthPerTeam[1] = 0;

        return true;
    }

    /**
     * Contestants will do trial lifecycle, where he pulls the rope, inform that he
     * is done and wait for the decision of referee
     * 
     * @param team The coach team
     * @param id   The id of the contestants
     * @param strength The strength of the contestant
     */

    public int waitForAssertTrialDecision(int team, int id, int strength) throws RemoteException{
        /**
         * Contestant pulls the rope
         */

        synchronized (this) {

            strengthPerTeam[team] += strength;
            repo.setContestantStrength(team, id, strength);


            repo.setContestantState(team, id, ContestantState.DO_YOUR_BEST);
        }


        /**
         * Contestant informs that he is done pulling the rope
         */
        synchronized (this) {
            amDone();
            while (!endOfTrial) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            arrivedContestants[team]--;
            notifyAll();
        }
        return ContestantState.DO_YOUR_BEST;

    }

    /**
     * Coach will wait until the decision of the referee of the trial
     * 
     * @param team The team of the coach
     */
    public int waitForAssertTrialDecision(int team)throws RemoteException {
        synchronized (this) {
            repo.setCoachState(team, CoachState.WATCH_TRIAL);

            while (!endOfTrial || arrivedContestants[0] > 0 || arrivedContestants[1] > 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyAll();
        }
        return CoachState.WATCH_TRIAL;
    }

    /**
     * The referee will declare the winner of the game
     * 
     * @return int The difference of strength between the teams
     */
    public synchronized ReturnInt declareGameWinner() throws RemoteException {
        repo.setRefereeState(RefereeState.END_OF_A_GAME);

        repo.setEndOfGame();

        repo.showGameResult(ropePosition);

        for (int i = 0; i < SimulParse.COACH; i++) {
            strengthPerTeam[i] = 0;
        }

        int aux = ropePosition;
        ropePosition = 0;

        return new ReturnInt(aux,RefereeState.END_OF_A_GAME);

        
    }


    /**
     * Operation server shutdown.
     */
    public synchronized void shutdown() {
        nEntities += 1;
        // the contestantas just to shut down all at the same time
        if (nEntities >= 3) {
            ServerGameOfRopePlayground.shutdown();
        }
        notifyAll();
    }
}
