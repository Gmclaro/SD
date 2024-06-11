package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.RefereeState;
import interfaces.*;
import serverSide.main.ServerGameOfRopeRefereeSite;

/**
 * Referee Site
 * Shared Memory Region
 */

public class RefereeSite implements RefereeSiteInterface{

    /**
     * Reference to the General Repository
     */
    private final GeneralRepositoryInterface repo;

    /**
     * Number of teams ready
     */

    private int teamsReady;

    /**
     * Number of entities
     */

    private int nEntities = 0;

    /**
     * Referee Site Constructor
     * 
     * @param repo General Repository
     */

    public RefereeSite(GeneralRepositoryInterface repo) {
        this.repo = repo;
        teamsReady = 0;
    }

    /**
     * The referee announces a new game
     * 
     * @return the state of the referee
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    
    public synchronized int announceNewGame() throws RemoteException{
        repo.newGameStarted();
        repo.setRefereeState(RefereeState.START_OF_A_GAME);
        return RefereeState.START_OF_A_GAME;
    }

    /**
     * The referee waits for the coaches to inform the teams are ready
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public synchronized void informReferee() throws RemoteException{
        teamsReady++;
        notifyAll();
    }

    /**
     * The referee waits for the coaches to inform the teams are ready
     * 
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public synchronized void waitForInformReferee()throws RemoteException{
        while (teamsReady < 2) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();

        /**
         * Reset the number of teams ready
         */
        teamsReady = 0;
    }


    /**
     *   Operation server shutdown.
     * 
     *  @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public synchronized void shutdown() throws RemoteException{
        nEntities += 1;

        // the contestantas just to shut down all at the same time
        if (nEntities >= 2) {
            ServerGameOfRopeRefereeSite.shutdown();
        }
        notifyAll();
    }
}
