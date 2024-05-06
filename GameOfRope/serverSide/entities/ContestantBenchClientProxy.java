package serverSide.entities;

import clientSide.entities.CoachCloning;
import clientSide.entities.ContestantCloning;
import clientSide.entities.RefereeCloning;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.ContestantBenchInterface;

public class ContestantBenchClientProxy extends Thread implements CoachCloning, RefereeCloning, ContestantCloning {

    // TODO: javadoc

    private static int nProxy = 0;

    private ServerCom sconi;

    private ContestantBenchInterface contestantBenchInterface;

    private int contestantState;

    private int contestantTeam;

    private int id;

    private int strength;

    private int refereeState;

    private int coachState;

    private int coachTeam;



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
        this.contestantState = state;
    }

    public int getContestantState() {
        return this.contestantState;
    }

    public void setContestantTeam(int team) {
        this.contestantTeam = team;
    }

    public int getContestantTeam() {
        return this.contestantTeam;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setRefereeState(int state) {
        this.refereeState = state;
    }

    public int getRefereeState() {
        return this.refereeState;
    }

    public void setCoachState(int state) {
        this.coachState = state;
    }

    public int getCoachState() {
        return this.coachState;
    }

    public void setCoachTeam(int team) {
        this.coachTeam = team;
    }

    
    public int getCoachTeam() {
        return this.coachTeam;
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
