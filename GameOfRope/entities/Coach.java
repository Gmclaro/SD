package GameOfRope.entities;

import GameOfRope.sharedRegions.*;


public class Coach extends Thread{

    /**
     * Reference to the Contestants Bench 
    */

    private ContestantBench bench;

    /**
     * Reference to the Playground
    */

    private Playground playground;

    /**
     * Reference to the Referee Site
     */

    private RefereeSite refereeSite;


    /**
     * Coach ID
     */

    private int coachId;

    /**
     * Coach State
     */

    private int coachState;

    /**
     * Coach team id
     */
     
    private int teamId;


    /**
    * Set the coach state
    */

    public void setCoachState(int couchState){
        this.coachState = couchState;
    }

    /**
     * Get the coach state
     */

    public int getCoachState(){
        return this.coachState;
    }

    /**
     * Get the coach ID
     */

    public int getcoachId(){
        return this.coachId;
    }

    /**
     * Get the coach team
     */

    public int getcoachTeamID(){
        return this.teamId;
    }

    /**
     * Coach instantiation
     * @param coachID
     */

    public Coach(int coachId,int teamId, ContestantBench bench, Playground playground, RefereeSite refereeSite){
        super("Coach_" + coachId + "T" + teamId);
        this.coachId = coachId;
        this.bench = bench;
        this.playground = playground;
        this.refereeSite = refereeSite;
        coachState = CoachState.WAIT_FOR_REFEREE_COMMAND;
    }

    /**
     * Coach life cycle
    */
    @Override

    public void run(){

    }




}


