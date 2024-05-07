package serverSide.entities;

import clientSide.entities.CoachCloning;
import clientSide.entities.ContestantCloning;
import clientSide.entities.RefereeCloning;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.ContestantBenchInterface;


/**
 * Service Provider agent for access to the Concentant Bench
 * Implementation of client-servev model of type 2 (server replication)
 * Communication is based on passing messages over sockets using TCP protocol
 */
public class ContestantBenchClientProxy extends Thread implements CoachCloning, RefereeCloning, ContestantCloning {


    /**
     * Number of instantiated threads
     */
    private static int nProxy = 0;

    /**
     * Communication channel
     */
    private ServerCom sconi;

    /**
     * Interface to ContestantBench
     */
    private ContestantBenchInterface contestantBenchInterface;

    /**
     * Contestant State
     */
    private int contestantState;

    /**
     * Contestant Team
     */
    private int contestantTeam;

    /**
     * Contestant ID
     */
    private int id;

    /**
     * Contestant Strength
     */
    private int strength;

    /**
     * Referee State
     */
    private int refereeState;

    /**
     * Coach State
     */
    private int coachState;

    /**
     * Coach Team
     */
    private int coachTeam;


    /**
     * Instantion of the Client Proxy
     * @param sconi Communication channel
     * @param contestantBenchInterface Interface to ContestantBench
     */

    public ContestantBenchClientProxy(ServerCom sconi, ContestantBenchInterface contestantBenchInterface) {
        super("ContestantBenchClientProxy(" + ContestantBenchClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.contestantBenchInterface = contestantBenchInterface;
    }

    /**
     * Get the Proxy ID
     * @return Proxy ID
     */
    public static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.ContestantBenchClientProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("Data type ContestantBenchClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }
        synchronized (cl) {
            proxyId = nProxy++;
        }
        return proxyId;
    }

    /**
     * Set the Contestant State
     * @param state Contestant State
     */
    public void setContestantState(int state) {
        this.contestantState = state;
    }

    /**
     * Get the Contestant State
     * @return Contestant State
     */
    public int getContestantState() {
        return this.contestantState;
    }

    /**
     * Set the Contestant Team
     * @param team Contestant Team
     */
    public void setContestantTeam(int team) {
        this.contestantTeam = team;
    }

    /**
     * Get the Contestant Team
     * @return Contestant Team
     */
    public int getContestantTeam() {
        return this.contestantTeam;
    }

    /**
     * Set the Contestant ID
     * @param id Contestant ID
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get the Contestant ID
     * @return Contestant ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get the Contestant Strength
     * @return strength
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Set the Contestant Strength
     * @param strength
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Set the Referee State
     * @param state Referee State
     */
    public void setRefereeState(int state) {
        this.refereeState = state;
    }

    /**
     * Get the Referee State
     * @return Referee State
     */
    public int getRefereeState() {
        return this.refereeState;
    }

    /**
     * Set the Coach State
     * @param state Coach State
     */
    public void setCoachState(int state) {
        this.coachState = state;
    }

    /**
     * Get the Coach State
     * @return Coach State
     */
    public int getCoachState() {
        return this.coachState;
    }

    /**
     * Set the Coach Team
     * @param team Coach Team
     */
    public void setCoachTeam(int team) {
        this.coachTeam = team;
    }

    /**
     * Get the Coach Team
     * @return Coach Team
     */
    
    public int getCoachTeam() {
        return this.coachTeam;
    }
    

    /**
     * Life cycle of the service provider agent
     */
    @Override
    public void run() {
        Message inMessage = null,
                outMessage = null;

        /* service providing */

        inMessage = (Message) sconi.readObject();
        try {
            outMessage = contestantBenchInterface.processAndReply(inMessage);
        } catch (MessageException e) {
            System.out.println("Thread" + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        sconi.writeObject(outMessage);
        sconi.close();
    }




}
