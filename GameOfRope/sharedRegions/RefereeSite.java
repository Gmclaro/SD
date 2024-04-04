package sharedRegions;

import entities.*;

public class RefereeSite {

    /**
     * Reference to the General Repository
     */
    private final GeneralRepository repo;

    /**
     * Number of teams ready
     */

    private int teamsReady;

    public RefereeSite(GeneralRepository repo) {
        this.repo = repo;
        teamsReady = 0;
    }

    /**
     * The referee announces a new game
     */
    public synchronized void announceNewGame() {
        repo.newGameStarted();

        ((Referee) Thread.currentThread()).setEntityState(RefereeState.START_OF_A_GAME);
        repo.setRefereeState(RefereeState.START_OF_A_GAME);
    }

    /**
     * The referee waits for the coaches to inform the teams are ready
     */

    public synchronized void informReferee() {
        teamsReady++;
        notifyAll();
    }

    /**
     * The referee waits for the coaches to inform the teams are ready
     */
    public synchronized void waitForInformReferee() {
        while (teamsReady < 2) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Reset the number of teams ready
         */
        teamsReady = 0;
    }
}
