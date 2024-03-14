package GameOfRope.sharedRegions;

import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.commInfra.*;
import GameOfRope.entities.*;

public class ContestantBench {

    private final Coach[] coach;

    private final int[][] selectedContestants;

    /**
     * Reference to Contestants threads
     */
    private final Contestant[][] contestants;

    
    public synchronized void callContestants(int teamID){}

    public synchronized void seatDown(int id){}

}