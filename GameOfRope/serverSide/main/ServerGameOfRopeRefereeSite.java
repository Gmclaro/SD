package serverSide.main;

import java.net.SocketTimeoutException;

import clientSide.stubs.GeneralRepositoryStub;
import commonInfra.ServerCom;
import serverSide.entities.RefereeSiteClientProxy;
import serverSide.sharedRegions.*;

public class ServerGameOfRopeRefereeSite {

    // TODO: javadoc
    public static boolean waitConnection;

    /**
     * Main method.
     * 
     * @param args runtime arguments.
     *             args[0] - port number for listening to service requests
     *             args[1] - name of the platform where is located the general
     *             repository server
     *             args[2] - port number where the server of the general repository
     *             us listening to service requests
     */
    public static void main(String[] args) {
        RefereeSite refereeSite;
        RefereeSiteInterface refereeSiteInterface;
        GeneralRepositoryStub generalRepositoryStub;
        ServerCom scon, sconi;

        if (args.length != 3) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                try {
                    int port = Integer.parseInt(args[i]);
                    if ((port < 4000) || (port > 65536)) {
                        System.out.println("Invalid args[" + i + "] port number!");
                        System.exit(1);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid args[" + i + "] type!");
                    System.exit(1);
                }

            }
        }

        /* service is established */

        generalRepositoryStub = new GeneralRepositoryStub(args[1], Integer.parseInt(args[2]));
        refereeSite = new RefereeSite(generalRepositoryStub);
        refereeSiteInterface = new RefereeSiteInterface(refereeSite);
        int port = Integer.parseInt(args[0]);
        scon = new ServerCom(port);

        scon.start();

        System.out.println("Service is established!");
        System.out.println("Service is listening for a service request in port " + port);

        /* service request processing */

        RefereeSiteClientProxy cliProxy;

        waitConnection = true;
        while (waitConnection) {
            try{
                sconi  = scon.accept();
                cliProxy = new RefereeSiteClientProxy(sconi,refereeSiteInterface);
                cliProxy.start();
            }catch(SocketTimeoutException e){}
        }
        scon.end();
        System.out.println("Server was shutdown.");

    }
}
