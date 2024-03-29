package sharedRegions;

import entities.*;
import java.util.LinkedList;

public class Playground {

    private final GeneralRepository repo;

    private final int playedTrials;

    private final Contestant[][] contestants;

    private final int[] arrivedContestants;

    private final int[] arrivedCoaches;

    // private final Coach[] coaches;

    // private final Referee referee;

    // TODO: change name of this var
    private int nOfAmDone;

    private final int[] strengthPerTeam;

    // TODO: nao sei se e necessiario
    private final LinkedList<Integer> histOfTrials = new LinkedList<Integer>();

    /**
     * Flags
     * 
     * It is used to control the flow of the game
     */
    private final boolean startOfTrial;
    private final boolean endOfTrial;

    // TODO: Constructor -> check if it's missing something
    public Playground(GeneralRepository repo) {
        this.repo = repo;
        contestants = new Contestant[2][3];
        playedTrials = 0;

        // during the game
        arrivedContestants = new int[] { 0, 0 };
        arrivedCoaches = new int[] { 0, 0 };
        nOfAmDone = 0;
        strengthPerTeam = new int[2];

        // Flags
        startOfTrial = false;
        endOfTrial = false;
    }

    public synchronized void callTrial() {
        // TODO: missing callTrial,
    }

    public synchronized void waitForCallTrial() {
        // TODO: Since this is a synchronized method, it should be a wait() method
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

    public synchronized void startTrial() {
        // TODO: missing startTrial, reset variables
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

    public synchronized boolean assertTrialDecision() {
        // TODO: missing assertTrialDecision
        return false;
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
        }

    }

    public synchronized void addContestant(int team) {
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.STAND_IN_POSITION);
        contestants[team][arrivedContestants[team]] = (Contestant) Thread.currentThread();
        arrivedContestants[team]++;
        notifyAll();
    }

    public synchronized void removeContestant(int team) {
        arrivedContestants[team]--;
        notifyAll();
    }

}
