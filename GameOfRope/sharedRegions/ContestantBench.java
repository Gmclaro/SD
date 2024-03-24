package GameOfRope.sharedRegions;

import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.commInfra.*;
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


    public synchronized void callContestants(int[] selected,int team) {
        // TODO: implement callContestants
        // playgroundQueue[teamID].write(contestantID);
    }

    public synchronized void followCoachAdvice() {
        // TODO: implement followCoachAdvice
    }

    public synchronized void seatDown(int id, int team) {
        // TODO: implement seatDown
    }
    

}