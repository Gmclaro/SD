package serverSide.entities;

import clientSide.entities.CoachCloning;
import clientSide.entities.ContestantCloning;
import clientSide.entities.RefereeCloning;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.PlaygroundInterface;

/**
 * Service Provider agent for access to the Playground
 * Implementation of client-servev model of type 2 (server replication)
 * Communication is based on passing messages over sockets using TCP protocol
 */
public class PlaygroundClientProxy extends Thread implements CoachCloning, RefereeCloning, ContestantCloning {

    /**
     * Number of instantiated threads
     */

    private static int nProxy = 0;

    /**
     * Communication channel
     */
    private ServerCom sconi;

    /**
     * Interface to Playground
     */
    private PlaygroundInterface playgroundInterface;

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
     * 
     * @param sconi               Communication channel
     * @param playgroundInterface Interface to Playground
     */

    public PlaygroundClientProxy(ServerCom sconi, PlaygroundInterface playgroundInterface) {
        super("PlaygroundClientProxy(" + PlaygroundClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.playgroundInterface = playgroundInterface;
    }

    /**
     * Generation of the instantiation identifier.
     *
     * @return instantiation identifier
     */

    public static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.PlaygroundClientProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("Data type PlaygroundClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }
        synchronized (cl) {
            proxyId = nProxy++;
        }
        return proxyId;
    }

    /**
     * Set Contestant State
     * 
     * @param state Contestant State
     */

    public void setContestantState(int state) {
        this.contestantState = state;
    }

    /**
     * Get Contestant State
     * 
     * @return Contestant State
     */
    public int getContestantState() {
        return this.contestantState;
    }

    /**
     * Set Contestant Team
     * 
     * @param team Contestant Team
     */

    public void setContestantTeam(int team) {
        this.contestantTeam = team;
    }

    /**
     * Get Contestant Team
     * 
     * @return Contestant Team
     */

    public int getContestantTeam() {
        return this.contestantTeam;
    }

    /**
     * Set Contestant ID
     * 
     * @param id Contestant ID
     */

    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get Contestant ID
     * 
     * @return Contestant ID
     */

    public int getID() {
        return this.id;
    }

    /**
     * Get Contestant Strength
     * 
     * @return strength Contestant Strength
     */

    public int getStrength() {
        return this.strength;
    }

    /**
     * Set Contestant Strength
     * 
     * @param strength Contestant Strength
     */

    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Set Referee State
     * 
     * @param state Referee State
     */

    public void setRefereeState(int state) {
        this.refereeState = state;
    }

    /**
     * Get Referee State
     * 
     * @return Referee State
     */

    public int getRefereeState() {
        return this.refereeState;
    }

    /**
     * Set Coach State
     * 
     * @param state Coach State
     */

    public void setCoachState(int state) {
        this.coachState = state;
    }

    /**
     * Get Coach State
     * 
     * @return Coach State
     */

    public int getCoachState() {
        return this.coachState;
    }

    /**
     * Set Coach Team
     * 
     * @param team Coach Team
     */

    public void setCoachTeam(int team) {
        this.coachTeam = team;
    }

    /**
     * Get Coach Team
     * 
     * @return Coach Team
     */

    public int getCoachTeam() {
        return this.coachTeam;
    }

    /**
     * Life cycle of the service provider agent.
     */

    @Override
    public void run() {
        Message inMessage = null,
                outMessage = null;

        /* service providing */

        inMessage = (Message) sconi.readObject();
        try {
            outMessage = playgroundInterface.processAndReply(inMessage);
        } catch (MessageException e) {
            System.out.println("Thread" + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        sconi.writeObject(outMessage);
        sconi.close();
    }

}
