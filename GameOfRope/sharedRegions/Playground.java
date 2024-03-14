package GameOfRope.sharedRegions;

import GameOfRope.commonInfra.MemFIFO;
import GameOfRope.entities.*;

public class Playground {
    
    private Contestant[][] contestants;
    private Referee referee;
    private Coach[] coach;

    private int[] strengthByTeam;

    /**
     * Waiting for contestants to be amDone
     */
    private final MemFIFO<Boolean>[] contestantsAmDone;
    


    // TODO: amDone  is a method in the shared memory region that will  sleep for a random time and then inform it already pulled the rope
    public synchronized Boolean amDone(int strength){
        
    }

    // TODO: call Trial
    public synchronized void callTrial(){}

    public synchronized void startTrial(){}

    public synchronized void getReady(){}
}
