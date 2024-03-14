package GameOfRope.sharedRegions;

import GameOfRope.entities.*;
public class RefereeSite {

    /**
     * Reference to the General Repository
     */

    private final GeneralRepository generalRepository;

    

    /**
     * Number of games played
     */

    private int gamesPlayed;

     /**
      * ReferreSite instantiation
        * @param generalRepository
      */
    public RefereeSite(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
        this.gamesPlayed = 0;
    }



    /**
     * The referee announces a new game
     */

    public synchronized void announceNewGame() {
        // TODO : implement announceNewGame
    }

    /**
     * The referee asserts the trial decision
     */

    public synchronized void assertTrialDecision() {
        // TODO : implement assertTrialDecision
    }

    /**
     * The referee declares the game winner
     */

    public synchronized void declareGameWinner() {
        // TODO : implement declareGameWinner
    }

    /**
     * The referee declares the match winner
     */

    public synchronized void declareMatchWinner() {
        // TODO : implement declareMatchWinner

    }

    
    
    
    // TODO: announce a new game 
    public synchronized void announceNewGame(){}   

    public synchronized void 
}
