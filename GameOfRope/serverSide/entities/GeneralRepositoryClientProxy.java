package serverSide.entities;

import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.ServerCom;
import serverSide.sharedRegions.GeneralRepositoryInterface;

/**
 * Service Provider agent for access to the General Repository
 * Implementation of client-servev model of type 2 (server replication)
 * Communication is based on passing messages over sockets using TCP protocol
 */
public class GeneralRepositoryClientProxy extends Thread {

    /**
     * Number of instantiated threads
     */
    private static int nProxy = 0;

    /**
     * Communication channel
     */
    private ServerCom sconi;

    /**
     * Interface of a client proxy
     * 
     * @param sconi                      communication channel
     * @param generalRepositoryInterface general repository interface
     */
    private GeneralRepositoryInterface generalRepositoryInterface;

    public GeneralRepositoryClientProxy(ServerCom sconi, GeneralRepositoryInterface generalRepositoryInterface) {
        super("GeneralRepositoryInterface(" + GeneralRepositoryClientProxy.getProxyId() + ")");
        this.sconi = sconi;
        this.generalRepositoryInterface = generalRepositoryInterface;
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
