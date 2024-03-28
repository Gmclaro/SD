package sharedRegions;

import java.util.LinkedList;

import entities.*;
public class RefereeSite {

    /**
     * Reference to the General Repository
     */
    private final GeneralRepository repo;

    // TODO: number of coaches ready
    private final int teamsReady;

    /**
     * Number of games played
     */

    private final int gamesPlayed;

    private final LinkedList<Integer> histOfGames = new LinkedList<Integer>();


    public RefereeSite(GeneralRepository repo) {
        this.repo = repo;
        gamesPlayed = 0;
        teamsReady = 0;
    }

    /**
     * The referee announces a new game
     */

    public synchronized void announceNewGame() {
        // TODO : implement announceNewGame
    }


    public synchronized void informReferee() {
        // TODO : implement informReferee
    }

    public synchronized void waitForInformReferee() {
        // TODO : implement waitForInformReferee -> wait() -> usar o teams ready
    }
    

    /**
     * The referee declares the game winner
     */

    public synchronized void declareGameWinner(LinkedList<Integer> histOfTrials) {
        // TODO : implement declareGameWinner
    }





}
