package GameOfRope.sharedRegions;

import java.util.Arrays;
import java.util.Comparator;

import GameOfRope.commonInfra.MemException;
import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.entities.*;

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
    

}