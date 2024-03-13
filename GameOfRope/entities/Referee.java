package  GameOfRope.entities;

import GameOfRope.sharedRegions.*;

public class Referee extends Thread{
    
    /**
     * Reference to the Playground
     */
    private Playground playground;

    /**
     * Reference to the Referee Site
     */
    private RefereeSite refereeSite;


    /**
     * Referee State
     */
    private int refereeState;

    
    /**
     * Referee instantiation
     * 
     * @param playground Reference to the Playground
     * @param refereeSite Reference to the Referee Site
     */

    
    public Referee(Playground playground, RefereeSite refereeSite){
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.refereeState = RefereeState.START_OF_THE_MATCH;
    }

    /**
     * Set the referee state
     */
    public void setRefereeState(int refereeState){
        this.refereeState = refereeState;
    }

    /**
     * Get the referee state
     */
    public int getRefereeState(){
        return this.refereeState;
    }

    /**
     * Referee life cycle
     */
    @Override
    public void run(){
        
    }
}