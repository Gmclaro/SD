package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import commonInfra.Strategy;
import commonInfra.Strategy.StrategyType;
import commonInfra.View;

/**
 * Custom Thread
 * It simulates the life cycle of a coach
 * Communication is based on remote calls under Java RMI protocol.
 */

public class Coach extends Thread {
    /**
     * Coach team
     */

    private int team;

    /**
     * Coach State
     */

    private int state;

    /**
     * Reference to the Contestants Bench
     */

    private final ContestantBenchInterface contestantBenchStub;

    /**
     * Reference to the Playground
     */

    private PlaygroundInterface playgroundStub;

    /**
     * Reference to the Referee Site
     */

    private RefereeSiteInterface refereeSiteStub;

    /**
     * Stratgies of the coach
     * 
     */
    private Strategy coachStrategy;

    /**
     * Name of the Thread
     * 
     * @return String Thread name
     */

    public String whoAmI() {
        return "Coach(" + team + ")";
    }

    /**
     * Set the coach state
     * 
     * @param state Coach state
     */
    public void setEntityState(int state) {
        this.state = state;
    }

    /**
     * Get the coach state
     * 
     * @return state Coach state
     */

    public int getEntityState() {
        return this.state;
    }

    /**
     * Set the coach team
     * 
     * @param team Coach team
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * Get the coach team
     * 
     * @return int Coach team
     */

    public int getTeam() {
        return this.team;
    }

    /**
     * Coach entity constructor
     * 
     * @param team            Coach team
     * @param contestantBench reference to contestant bench
     * @param playground      reference to playground
     * @param refereeSite     reference to referee site
     * @param coachStrategy   coach strategy
     */

    public Coach(int team, ContestantBenchInterface contestantBenchStub, PlaygroundInterface playgroundStub, RefereeSiteInterface refereeSiteStub,
            StrategyType coachStrategy) {
        super("Coach(" + team + ")");
        this.team = team;
        this.state = -1;
        this.contestantBenchStub = contestantBenchStub;
        this.playgroundStub = playgroundStub;
        this.refereeSiteStub = refereeSiteStub;
        this.coachStrategy = new Strategy(coachStrategy);
    }

    /**
     * Coach life cycle
     * 
     */
    @Override
    public void run() {
        System.out.println(this.whoAmI() + " has started.");

        /*
         * Start of Coach life cycle
         */
        int orders;

        View[] aboutContestants = reviewNotes();
        System.out.println(this.whoAmI() + " -> reviewNotes()");

        int[] selected = selectContestants(aboutContestants);
        System.out.println(this.whoAmI() + " -> selectContestants()");

        while (true) {
            orders = waitForCallTrial();
            System.out.println(this.whoAmI() + " -> waitForCallTrial()");

            if (orders == 0) {
                return;
            }
            callContestants(selected);
            System.out.println(this.whoAmI() + " -> callContestants()");
            
            waitForFollowCoachAdvice();
            System.out.println(this.whoAmI() + " -> waitForFollowCoachAdvice()");

            informReferee();
            System.out.println(this.whoAmI() + " -> informReferee()");

            waitForAssertTrialDecision();
            System.out.println(this.whoAmI() + " -> waitForAssertTrialDecision()");

            aboutContestants = reviewNotes();
            System.out.println(this.whoAmI() + " -> reviewNotes()");

            selected = selectContestants(aboutContestants);
            System.out.println(this.whoAmI() + " -> selectContestants()");
        }
    }

    /**
     * Select the team based on the strategy
     * 
     * @param selected The list of contestants to select the team from.
     * @return int[] The selected team.
     */

    private int[] selectContestants(View[] selected) {
        int[] sel = coachStrategy.getStrategy().selectTeam(selected);
        return sel;

    }

    /**
     * Inform the referee that the team is ready
     * 
     */
    private void informReferee() {
        try {
            refereeSiteStub.informReferee();
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Review the notes of the contestants
     * 
     */

     private View[] reviewNotes() {
        View[] aboutContestants = null;
        try {
            ReturnInt[] returnContestants = contestantBenchStub.reviewNotes(team);
            aboutContestants = new View[returnContestants.length];
            for (int i = 0; i < returnContestants.length; i++) {
                aboutContestants[i] = new View(returnContestants[i].getIntVal(), returnContestants[i].getIntStateVal());
            }
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }
        return aboutContestants;
    }

    /**
     * Wait for the referee to call the trial
     * 
     * @return int The number of orders
     */


    private int waitForCallTrial() {
        ReturnInt orders = null;
        try {
            orders = contestantBenchStub.waitForCallTrial(team);
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }
        if (orders.getIntVal()<0 || orders.getIntVal()>1) {
            System.err.println("Invalid return value from waitForCallTrial: " + orders.getIntVal());
            System.exit(1);
        }
        state = orders.getIntVal();
        return orders.getIntVal();
    }

    /**
     * Call the selected contestants
     * 
     * @param selected The selected contestants
     * @param team     The team of the coach
     * 
     */

    private void callContestants(int[] selected) {
        try {
            contestantBenchStub.callContestants(team, selected);
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Wait for follow coach advice
     * 
     * @param team The team of the coach
     */

    private void waitForFollowCoachAdvice() {
        try {
            state = playgroundStub.waitForFollowCoachAdvice(team);
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Wait For Assert Trial Decision
     * 
     * @param team The team of the coach
     */

    private void waitForAssertTrialDecision() {
        try {
            state = playgroundStub.waitForAssertTrialDecision(team);
        } catch (RemoteException e) {
            System.out.println(this.whoAmI() + " -> " + e.getMessage());
            System.exit(1);
        }
    }

}
