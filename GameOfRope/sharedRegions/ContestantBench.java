package sharedRegions;

import java.util.Arrays;
import java.util.Comparator;

import commonInfra.MemException;
import commonInfra.MemFIFO;
import entities.*;

public class ContestantBench {

    /**
     * General Repository of Information
     */
    private final GeneralRepository repo;

    private final Contestant[][] contestants;
    private final MemFIFO<Integer>[] playgroundQueue;

    public ContestantBench(GeneralRepository repo) {
        this.repo = repo;
        this.contestants = new Contestant[2][5];
        playgroundQueue = new MemFIFO[2];
    }


    public synchronized void callContestants(int[] selected,int team) throws MemException {

        // TODO: implement callContestants
        for(int contestantID:selected){
            playgroundQueue[team].write(contestantID);
        }
        notifyAll();
    }

    public synchronized void followCoachAdvice() {
        // TODO: implement followCoachAdvice
                
    }

    public synchronized void seatDown(int id, int team) {
        // TODO: implement seatDown
    }
    
    /**
     * Get the contestants in the bench
     * @return Contestant[][]
     */
    public synchronized Contestant[] getBench(int team){
        return contestants[team];
    }

}