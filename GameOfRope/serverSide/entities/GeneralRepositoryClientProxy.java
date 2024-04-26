package serverSide.entities;

import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.GeneralRepositoryInterface;

public class GeneralRepositoryClientProxy extends Thread {
    // TODO: javadoc
    private static int nProxy = 0;

    private ServerCom sconi;

    private GeneralRepositoryInterface generalRepositoryInterface;

    public GeneralRepositoryClientProxy(ServerCom sconi, GeneralRepositoryInterface generalRepositoryInterface) {
        super("GeneralRepositoryInterface(" + GeneralRepositoryClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.generalRepositoryInterface = generalRepositoryInterface;
    }

    public static int getProxyId() {
        Class<?> cl = null;
        int proxyId;

        try {
            cl = Class.forName("serverSide.entities.RefereeSiteClientProxy");
        } catch (ClassNotFoundException e) {
            System.out.println("Data type RefereeSiteClientProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }
        synchronized (cl) {
            proxyId = nProxy++;
        }
        return proxyId;
    }

    @Override
    public void run() {
        Message inMessage = null,
                outMessage = null;

        /* service providing */

        inMessage = (Message) sconi.readObject();
        try {
            outMessage = generalRepositoryInterface.processAndReply(inMessage);
        } catch (MessageException e) {
            System.out.println("Thread" + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        sconi.writeObject(outMessage);
        sconi.close();
    }

}
