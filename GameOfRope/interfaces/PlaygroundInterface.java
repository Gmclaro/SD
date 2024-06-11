package interfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type Playground.
 *
 * It provides the functionality to access the Playground.
 */

public interface PlaygroundInterface extends Remote {

    /**
     * Operation followCoachAdvice
     * Contestants go to the playground and inform when they have arrived
     * 
     * @param team The team of the contestant
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void followCoachAdvice(int team) throws RemoteException;

    /**
     * Operation waitForFollowCoachAdvice
     * Coaches wait for the contestants to arrive at the playground
     * 
     * @param team The team of the coach
     * @return int coach state
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int waitForFollowCoachAdvice(int team) throws RemoteException;

    /**
     * Operarion startTrial
     * The referee starts the trial
     * 
     * @return int The state of the referee
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int startTrial() throws RemoteException;

    /**
     * Operation waitForStartTrial
     * The coach waits for the referee to start the trial
     * 
     * @param team The team of the coach
     * @param id   The id of the coach
     * 
     * @return int The state of the contestant
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int waitForStartTrial(int team, int id) throws RemoteException;

    /**
     * Operation getReady
     * The contestant gets ready
     * 
     * @param team Team of the Contestant
     * @param id   Id of the Contestant
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public void getReady(int team, int id) throws RemoteException;

    /**
     * Operatoin amDone
     * The contestant informs that he is done
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     * 
     */

    public void amDone() throws RemoteException;

    /**
     * Operation waitForAmDone
     * Contestants wait for the decision of the referee
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public void waitForAmDone() throws RemoteException;

    /**
     * Operation assertTrialDecision
     * Referee will compare the strength of the teams and decide if the trial has
     * ended.
     * 
     * @return boolean The decision of the referee
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public boolean assertTrialDecision() throws RemoteException;

    /**
     * Operation waitForAssertTrialDecision
     * Contestants will do trial lifecycle, where he pulls the rope, inform that he
     * is done and wait for the decision of referee
     * 
     * @param team Team of the Contestant
     * 
     * @return int The state of the coach
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public int waitForAssertTrialDecision(int team) throws RemoteException;

    /**
     * Operation waitForAssertTrialDecision
     * Contestants will do trial lifecycle, where he pulls the rope, inform that he
     * 
     * @param team     The team of the Contestant
     * @param id       The id of the Contestant
     * @param strength The strength of the Contestant
     * 
     * @return int The state of the contestant
     * 
     * @throws RemoteException
     */

    public int waitForAssertTrialDecision(int team, int id, int strength) throws RemoteException;

    /**
     * Operation declareGameWinner
     * The referee declares the winner of the game
     * 
     * @return int The difference of strength between the teams
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public ReturnInt declareGameWinner() throws RemoteException;

    /**
     * Operation server shutdown
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     *                         communication with the registry service fails
     */

    public void shutdown() throws RemoteException;

}
