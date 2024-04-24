package entities;

//import commonInfra.Strategy.*;
import commonInfra.View;
import commonInfra.Strategy;
import commonInfra.Strategy.StrategyType;
import sharedRegions.*;

/**
 * Custom Thread
 * It simulates the life cycle of a coach
 * It will be a static solution
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

    private ContestantBench contestantBench;

    /**
     * Reference to the Playground
     */

    private Playground playground;

    /**
     * Reference to the Referee Site
     */

    private RefereeSite refereeSite;

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

    public Coach(int team, ContestantBench contestantBench, Playground playground, RefereeSite refereeSite,
            StrategyType coachStrategy) {
        super("Coach(" + team + ")");
        this.team = team;
        this.state = -1;
        this.contestantBench = contestantBench;
        this.playground = playground;
        this.refereeSite = refereeSite;
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

        View[] aboutContestants = contestantBench.reviewNotes(team);
        System.out.println(this.whoAmI() + " -> reviewNotes()");

        int[] selected = selectContestants(aboutContestants);
        System.out.println(this.whoAmI() + " -> selectContestants()");

        while (true) {
            orders = contestantBench.waitForCallTrial(team);
            System.out.println(this.whoAmI() + " -> waitForCallTrial()");

            if (orders == 0) {
                return;
            }

            contestantBench.callContestants(team, selected);
            System.out.println(this.whoAmI() + " -> callContestants()");

            playground.waitForFollowCoachAdvice(team);
            System.out.println(this.whoAmI() + " -> waitForFollowCoachAdvice()");

            refereeSite.informReferee();
            System.out.println(this.whoAmI() + " -> informReferee()");

            playground.waitForAssertTrialDecision(team);
            System.out.println(this.whoAmI() + " -> waitForAssertTrialDecision()");

            aboutContestants = contestantBench.reviewNotes(team);
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
}
