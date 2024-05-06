package serverSide.entities;

import clientSide.entities.CoachCloning;
import clientSide.entities.RefereeCloning;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.RefereeSiteInterface;


/**
 * Service Provider agent for access to the Referee Site
 * Implementation of client-servev model of type 2 (server replication)
 * Communication is based on passing messages over sockets using TCP protocol
 */
public class RefereeSiteClientProxy extends Thread implements RefereeCloning, CoachCloning {

    /**
     * Number of instantiated threads
     */
    private static int nProxy = 0;

    /**
     * Communication channel
     */

    private ServerCom sconi;

    /**
     * Interface to RefereeSite
     */

    private RefereeSiteInterface refereeSiteInterface;

    /**
     * Coach Team
     */

    private int coachTeam;

    /**
     * Referee State
     */

    private int refereeState;

    /**
     * Coach State
     */

    private int coachState;



    /**
     * Instantiation of a client proxy
     * 
     * @param sconi                communication channel
     * @param refereeSiteInterface referee site interface
     */

    public RefereeSiteClientProxy(ServerCom sconi, RefereeSiteInterface refereeSiteInterface) {
        super("RefereeSiteClientProxy(" + RefereeSiteClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.refereeSiteInterface = refereeSiteInterface;
    }

    /**
     * Generation of the instantiation identifier.
     *
     * @return instantiation identifier
     */

    public static int getProxyId(){
        Class<?> cl = null;
        int proxyId;

        try{
            cl = Class.forName("serverSide.entities.RefereeSiteClientProxy");
        }catch(ClassNotFoundException e){
            System.out.println("Data type RefereeSiteClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }
        synchronized(cl){
            proxyId = nProxy++;
        }
        return proxyId;
    }



    /**
     * Set Referee State
     * @param state Referee State
     */
    
    public void setRefereeState(int state) {
        refereeState = state;
    }

    /**
     * Get Referee State
     * @return Referee State
     */

    
    public int getRefereeState() {
        return refereeState;
    }

    /**
     * Set Coach State
     * @param state Coach State
     */

    public void setCoachState(int state) {
        coachState = state;
    }

    /**
     * Get Coach State
     * @return Coach State
     */

    public int getCoachState() {
        return coachState;
    }

    /**
     * Set Coach Team
     * @param team Coach Team
     */

    
    public void setCoachTeam(int team) {
        coachTeam = team;
    }

    /**
     * Get Coach Team
     * @return Coach Team
     */

    
    public int getCoachTeam() {
       return coachTeam;
    }


    /**
     * Life cycle of the service provider agent.
     */


    @Override
    public void run(){
        Message inMessage = null,
        outMessage = null;

        /* service providing */

        inMessage = (Message) sconi.readObject();
        try{
            outMessage  = refereeSiteInterface.processAndReply(inMessage);
        }catch(MessageException e){
            System.out.println("Thread"+ getName() + ": "+ e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        sconi.writeObject(outMessage);
        sconi.close();
    }

}
