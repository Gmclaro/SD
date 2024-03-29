package sharedRegions;

import entities.*;
import java.util.LinkedList;

public class Playground {

    private final GeneralRepository repo;

    private final int playedTrials;

    private final Contestant[][] contestants;
    
    private final int[] arrivedContestants;

    private final int[] arrivedCoaches;

    //private final Coach[] coaches;

    //private final Referee referee;

    // TODO: change name of this var
    private final int nOfAmDone;

    private final int[] strengthPerTeam;

    // TODO: nao sei se e necessiario
    private final LinkedList<Integer> histOfTrials = new LinkedList<Integer>();

    // TODO: Constructor -> check if it's missing something
    public Playground(GeneralRepository repo) {
        this.repo = repo;
        contestants = new Contestant[2][3];
        playedTrials = 0;
        
        // during the game
        arrivedContestants = new int[]{0,0};
        arrivedCoaches = new int[]{0,0};
        nOfAmDone = 0;
        strengthPerTeam = new int[2];
    }

    public synchronized void callTrial() {
        // TODO: missing callTrial,
    }

    public synchronized void waitForCallTrial() {
        // TODO: Since this is a synchronized method, it should be a wait() method
    }

    public synchronized void startTrial() {
        // TODO: missing startTrial, reset variables
    }

    public synchronized void getReady(int contestantID, int teamID) {
        // TODO: missing getReady, informs repo
    }

    public synchronized void amDone(int strength, int teamID) {
        // TODO: missing amDone
    }

    public synchronized boolean assertTrialDecision() {
        // TODO: missing assertTrialDecision
        return false;
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
