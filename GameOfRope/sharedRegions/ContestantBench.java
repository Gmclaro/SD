package sharedRegions;

import java.util.Arrays;
import java.util.Comparator;

import commonInfra.MemException;
import commonInfra.MemFIFO;
import entities.*;

public class ContestantBench {

    private final GeneralRepository repo;

    private final Contestant[][] contestants;
    private final MemFIFO<Integer>[] playgroundQueue;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;
        this.contestants = new Contestant[2][5];
        playgroundQueue = new MemFIFO[2];
    }

    public synchronized void callContestants(int[] selected, int team) throws MemException {
        // TODO: implement callContestants
        for (int contestantID : selected) {
            playgroundQueue[team].write(contestantID);
        }
        notifyAll();
    }

    public synchronized void followCoachAdvice() {
        
        int team = ((Contestant) Thread.currentThread()).getTeam();
        int id = ((Contestant) Thread.currentThread()).getID();
        contestants[team][id] = null;
        notifyAll();
    }

    public synchronized void seatDown(int id, int team) {
        ((Contestant) Thread.currentThread()).setEntityState(ContestantState.SEAT_AT_THE_BENCH);
        // add contestat to bench
        
        
        notifyAll();
    }

    /**
     * Get the contestants in the bench
     * @return Contestant[][]
     */
    public synchronized Contestant[] getBench(int team){
        return contestants[team];
    }

}