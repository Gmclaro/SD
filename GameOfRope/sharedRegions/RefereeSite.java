package sharedRegions;

import java.util.LinkedList;

import entities.*;
public class RefereeSite {

    /**
     * Reference to the General Repository
     */
    private final GeneralRepository repo;

    private final Referee referee;

    private final Coach[] coaches;

    private final int teamsReady;

    /**
     * Number of games played
     */

    private final int gamesPlayed;

    private final LinkedList<Integer> histOfGames = new LinkedList<Integer>();


    public RefereeSite(GeneralRepository repo, Referee referee,Coach[] coaches) {
        this.repo = repo;
        this.referee = referee;
        this.coaches = coaches;
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
        // TODO : implement waitForInformReferee
    }
    

    /**
     * The referee declares the game winner
     */

    public synchronized void declareGameWinner(LinkedList<Integer> histOfTrials) {
        // TODO : implement declareGameWinner
    }

    /**
     * The referee declares the match winner
     */

    public synchronized void declareMatchWinner() {
        // TODO : implement declareMatchWinner

    }

    public synchronized Referee getReferee() {
        return referee;
    }

}
