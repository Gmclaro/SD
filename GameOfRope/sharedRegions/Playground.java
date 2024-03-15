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

    public synchronized void callTrial(){
        // TODO: call trial
    }

    public synchronized void startTrial(){
        // TODO: start trial
    }

    public synchronized void getReady(){
        // TODO: get ready
    }

    public synchronized void followCoachAdvice(int[] contestants){
        // TODO: follow coach advice
    }

}
