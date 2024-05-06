package serverSide.entities;

import clientSide.entities.CoachCloning;
import clientSide.entities.RefereeCloning;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.RefereeSiteInterface;

public class RefereeSiteClientProxy extends Thread implements RefereeCloning, CoachCloning {

    // TODO: javadoc
    private static int nProxy = 0;

    private ServerCom sconi;

    private RefereeSiteInterface refereeSiteInterface;

    private int coachTeam;

    private int refereeState;

    private int coachState;




    public RefereeSiteClientProxy(ServerCom sconi, RefereeSiteInterface refereeSiteInterface) {
        super("RefereeSiteClientProxy(" + RefereeSiteClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.refereeSiteInterface = refereeSiteInterface;
    }

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


    
    public void setRefereeState(int state) {
        refereeState = state;
    }

    
    public int getRefereeState() {
        return refereeState;
    }

    public void setCoachState(int state) {
        coachState = state;
    }

    public int getCoachState() {
        return coachState;
    }

    
    public void setCoachTeam(int team) {
        coachTeam = team;
    }

    
    public int getCoachTeam() {
       return coachTeam;
    }




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
