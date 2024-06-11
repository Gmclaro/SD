package clientSide.entities;

import java.rmi.RemoteException;

import interfaces.ContestantBenchInterface;
import interfaces.PlaygroundInterface;

import interfaces.*;

/**
 * Custom Thread
 * 
 * It simulates the life cycle of a contestant
 * It will be a static solution
 */
public class Contestant extends Thread {

    /**
     * Constestant Identification
     */
    private int id;

    /**
     * Contestant Strength
     */
    private int strength;

    /**
     * Contestant Team Identification
     */
    private int team;

    /**
     * Contestant State
     */
    private int state;

    /**
     * Playground shared memory region declaration
     */
    private final PlaygroundInterface playgroundStub;

    /**
     * ContestantBench shared memory region declaration
     */
    private final ContestantBenchInterface contestantBenchStub;

    /**
     * Contestant Constructor
     * 
     * @param team               team
     * @param id                 Contestant id
     * @param contestantStrength Contestant strength
     * @param playground         reference to playground
     * @param contestantBench    reference to contestantBench
     */
    public Contestant(int team, int id, int contestantStrength, PlaygroundInterface playgroundStub,
            ContestantBenchInterface contestantBenchStub) {
        super("Contestant(T" + team + "," + id + ")");
        this.id = id;
        this.team = team;
        this.strength = contestantStrength;
        this.state = ContestantState.SEAT_AT_THE_BENCH;

        // shared regions
        this.playgroundStub = playgroundStub;
        this.contestantBenchStub = contestantBenchStub;
    }

    /**
     * Name of the Thread
     * 
     * @return String Thread name
     */

    public String whoAmI() {
        return "Contestant(T" + team + "," + id + ")";
    }

    /**
     * Set contestant id
     * 
     * @param id contestant id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get contestant id
     * 
     * @return int contestant id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get team
     * 
     * @return int team
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * Set team
     * 
     * @param team team
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * Set contestant state
     * 
     * @param state contestant state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get contestant state
     * 
     * @return int contestant state
     */
    public int getEntityState() {
        return this.state;
    }

    /**
     * Get strength of the contestant
     * 
     * @return int strength of the contestant
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Set strength of the contestant
     * 
     * @param strength strength of the contestant
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Life cycle of the contestant
     */
    @Override
    public void run() {
        System.out.println(this.whoAmI() + " has started.");
        seatDown();
        System.out.println(this.whoAmI() + " -> seatDown()");

        int orders;
        while (true) {
            orders = waitForCallContestant();
            System.out.println(this.whoAmI() + " -> waitForCallContestant()");

            switch (orders) {
                case 0:
                    return; // match is over and contestant thread is done
                case 1:
                    rest();

                    continue; // contestant was not selected, rest
                case 2:
                    break; // contestant was selected, go to playground and continue the lifecycle
            }

            followCoachAdvice();
            System.out.println(this.whoAmI() + " -> followCoachAdvice()");

            waitForStartTrial();
            System.out.println(this.whoAmI() + " -> waitForStartTrial()");

            getReady();
            System.out.println(this.whoAmI() + " -> getReady()");

            pullTheRope();

            waitForAssertTrialDecision();
            System.out.println(this.whoAmI() + " -> waitForAssertTrialDecision()");

            seatDown();
            System.out.println(this.whoAmI() + " -> seatDown()");
        }
    }

    /**
     * Rest increases the strength of the Contestant
     */

    private void rest() {
        this.strength++;
    }

    private void pullTheRope() {
        /**
         * Contestant sleeps for a random time
         */
        try {
            sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.strength--;
    }

    /**
     * Operation seatDown
     * Remote operation.
     * Contestatn is placed in the bench
     * 
     * @param team Team of the Contestant
     * @param id   Id of the Contestantc
     */

    private void seatDown() {
        try {
            state = contestantBenchStub.seatDown(team, id, strength);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operation followCoachAdvice
     * Remote operation.
     * Contestants go to the playground and inform when they have arrived
     * 
     * @param team The team of the contestant
     */

    private void followCoachAdvice() {
        try {
            playgroundStub.followCoachAdvice(team);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operation waitForCallContestant
     * Remote operation.
     * Contestant waits to be called by the coach
     * 
     * @return int orders
     */

    private int waitForCallContestant() {
        int orders = 0;
        try {
            orders = contestantBenchStub.waitForCallContestants(team, id, strength);


        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Operation waitForStartTrial
     * Remote operation.
     * Contestant waits for the trial to start
     * 
     */

    private void waitForStartTrial() {
        try {
            state = playgroundStub.waitForStartTrial(team, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operation getReady
     * Remote operation.
     * Contestant gets ready
     * 
     */

    private void getReady() {
        try {
            playgroundStub.getReady(team, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operation waitForAssertTrialDecision
     * Remote operation.
     * Contestant waits for the decision of the referee
     * 
     * 
     */

    private void waitForAssertTrialDecision() {
        try {
            state= playgroundStub.waitForAssertTrialDecision(team, id,strength);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
