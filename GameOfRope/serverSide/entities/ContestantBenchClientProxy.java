package serverSide.entities;
import commonInfra.*;

import serverSide.sharedRegions.ContestantBenchInterface;

public class ContestantBenchClientProxy extends Thread{

    // TODO: javadoc

    private static int nProxy = 0;

    private ServerCom sconi;

    private ContestantBenchInterface contestantBenchInterface;

    private int coachState;

    private int contestantState;

    private int refereeState;


    private int team;

    public ContestantBenchClientProxy(ServerCom sconi, ContestantBenchInterface contestantBenchInterface) {
        super("ContestantBenchClientProxy(" + ContestantBenchClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.contestantBenchInterface = contestantBenchInterface;
    }

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

    public void setContestantState(int state) {
        contestantState = state;
    }

    public int getContestantState() {
        return contestantState;
    }

    public void setCoachState(int state) {
        coachState = state;
    }

    public int getCoachState() {
        return coachState;
    }

    public void setRefereeState(int state) {
        refereeState = state;
    }

    public int getRefereeState() {
        return refereeState;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }


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
