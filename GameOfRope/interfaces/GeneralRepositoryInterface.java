package interfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type Playground.
 *
 * It provides the functionality to access the Playground.
 */

public interface GeneralRepositoryInterface extends Remote {

    /**
     * Operatoin initSimul
     * Initialize the simulation
     * 
     * @param contestantStrength The strength of the contestants
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void initSimul(int[][] contestantStrength) throws RemoteException;

    /**
     * Operation showGameResults
     * Show the results of the game
     * 
     * @param difference The difference between the scoress of the two teams
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void showGameResult(int difference) throws RemoteException;

    /**
     * Operation updateInfoTemplate
     * Update the internal state of the entities
     * 
     * 
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     * 
     */

    public void updateInfoTemplate() throws RemoteException;

    /**
     * Operation newGameStarted
     * Inform that a new game has started
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void newGameStarted() throws RemoteException;

    /**
     * Operation setRefereeState
     * Set the state of the referee
     * 
     * @param state The state of the referee
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setRefereeState(int refereeState) throws RemoteException;

    /**
     * Operation setCoachState
     * Set the state of the coach
     * 
     * @param team  The team of the coach
     * @param state The state of the coach
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setCoachState(int team, int state) throws RemoteException;

    /**
     * Operation setContestantState
     * Set the state of the contestant
     * 
     * @param team  The team of the contestant
     * @param id    The id of the contestant
     * @param state The state of the contestant
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setContestantState(int team, int id, int state) throws RemoteException;

    /**
     * Operation setContestantStrength
     * Set the strength of the contestant
     * 
     * @param team     The team of the contestant
     * @param id       The id of the contestant
     * @param strength The strength of the contestant
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setContestantStrength(int team, int id, int strength) throws RemoteException;

    /**
     * Operation setActiveContestant
     * Set the active contestant
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setActiveContestant(int team, int id) throws RemoteException;


    /**
     * Operation setRopePosition
     * Set the position of the rope
     * 
     * @param positionOfRope The position of the rope
     * 
     */

    public void setRopePosition(int positionOfRope) throws RemoteException;


    /**
     * Operation setRemoveContestant
     * Set the remove contestant
     * 
     * @param team The team of the contestant
     * @param id   The id of the contestant
     * @throws RemoteException
     */

    public void setRemoveContestant(int team, int id) throws RemoteException;


    /**
     * Operation setNewTrial
     * Set the new trial
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setNewTrial() throws RemoteException;


    /**
     * Operation setMatchWinner
     * Set the match winner
     * 
     * @param scores[] The match number
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setMatchWinner(int[]scores) throws RemoteException;

    /**
     * Operation setEndOfGame
     * Set the end of the game
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void setEndOfGame() throws RemoteException;


    /**
     * Operation shudown
     * 
     * @throws RemoteException if either the invocation of the remote method, or the
     */

    public void shutdown() throws RemoteException;



}