package GameOfRope.sharedRegions;

import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.commInfra.*;
import GameOfRope.entities.*;

public class ContestantBench {


    private final int[][] selectedContestants;

    /**
     * Reference to Contestants threads
     */
    private final Contestant[][] contestants;

    /**
     * Reference to the Coach
     */
    private final Coach[] coach;

    
    
    public synchronized void callContestants(int teamID){
        // TODO : call contestants
    }

    public synchronized void seatDown(int id){
        // TODO : seat down
    }

}