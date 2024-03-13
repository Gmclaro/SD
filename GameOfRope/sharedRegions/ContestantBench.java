package GameOfRope.sharedRegions;

import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.commInfra.*;
import GameOfRope.entities.*;

public class ContestantBench {
    /**
     * Reference to Contestants threads
     */
    private final Contestant[] contestants;

    /**
     * Waiting for contestants to be amDone
     */
    private final MemFIFO<Integer>[] contestantsAmDone;

}
