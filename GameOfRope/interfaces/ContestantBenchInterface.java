package interfaces;

import commonInfra.View;

import java.rmi.*;

/**
 * Operational interface of a remote object of type ContestantBench.
 *
 * It provides the functionality to access the ContestantBench.
 */

public interface ContestantBenchInterface extends Remote {

    /**
     * Operation call trial
     * The referee calls a trial
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */
    public int callTrial() throws RemoteException;

    /**
     * Operation waitForCallTrial
     * The coach waits for the referee to call a trial
     * @param team Team of the Coach
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public ReturnInt waitForCallTrial(int team) throws RemoteException;

    /**
     * Operation callContestants
     * The coach calls the selected contestants
     * @param team                Team of the Coach
     * @param selectedContestants Selected Contestants
     */

    public void callContestants(int team, int[] selectedContestants) throws RemoteException;

    /**
     * Operation waitForCallContestants
     * The contestant waits for the coach to call him
     * @param team Team of the Coach
     * @param id   Id of the Contestant
     *             return int Order of the Contestant in the playground
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public ReturnInt waitForCallContestants(int team, int i,int strengh) throws RemoteException;

    /**
     * Operation seatDown
     * The contestant seats down
     * @param team Team of the Contestant
     * @param id   Id of the Contestant
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int seatDown(int team, int id,int strengh) throws RemoteException;

    /**
     * Operation reviewNotes
     * The coach reviews the notes of the contestants
     * @param team Team of the Coach
     * @return View[] Array of Views with the contestant information
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public View[] reviewNotes(int team) throws RemoteException;

    /**
     * Operation declare match winner
     * The referee declares the match winner
     * @param scores Match result
     * @return int State of the referee
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int declareMatchWinner(int[] scores) throws RemoteException;

    /**
     * Operation waitForSeatAtBench
     * The coach waits for the contestant to seat at the bench
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public void waitForSeatAtBench() throws RemoteException;

    /**
     * Operatoin server shutdown
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public void shutdown() throws RemoteException;
}
