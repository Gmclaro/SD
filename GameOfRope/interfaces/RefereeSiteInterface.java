package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type RefereeSite.
 *
 *      It provides the functionality to access the RefereeSite.
 */

public interface RefereeSiteInterface extends Remote {

    /** 
     * Operations announceNewGame
     * The Referee announces a new game
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void announceNewGame() throws RemoteException;

    /** 
     * TOperations informReferee
     * The Coach informs the Referee that the teams are ready
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public void informReferee() throws RemoteException;


    /** 
     * Operation waitForInformReferee
     * The referee waits for the coach to inform him that the teams are ready
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public void waitForInformReferee() throws RemoteException;


    /**
     * Operation server shutdown
     * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
     */

    public void shutdown() throws RemoteException;

    
}
