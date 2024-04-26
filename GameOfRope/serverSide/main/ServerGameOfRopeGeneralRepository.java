package serverSide.main;

import java.net.SocketTimeoutException;

import commonInfra.*;
import serverSide.entities.GeneralRepositoryClientProxy;
import serverSide.sharedRegions.GeneralRepository;
import serverSide.sharedRegions.GeneralRepositoryInterface;

public class ServerGameOfRopeGeneralRepository {
    // TODO: javadoc
    public static boolean waitConnection = true;

    /**
     * Main method.
     * 
     * @param args runtime arguments.
     *             args[0] - port number for listening to service requests
     */
    public static void main(String[] args) {
        GeneralRepository repo;
        GeneralRepositoryInterface repoInterface;
        ServerCom scon, sconi;
        int port = -1;

        System.out.println(args.length);
        if (args.length != 1) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
            if ((port < 4000) || (port > 65536)) {
                System.out.println("Invalid args[" + args[0] + "] port number!");
                System.exit(1);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid args[" + args[0] + "] type!");
            System.exit(1);
        }

        // TODO: remove strength
        int[][] strength = new int[][] { { 6, 6, 6, 6, 6 }, { 6, 6, 6, 6, 6 } };

        repo = new GeneralRepository("log", strength);
        repoInterface = new GeneralRepositoryInterface(repo);
        scon = new ServerCom(port);

        scon.start();

        System.out.println("Service is established!");
        System.out.println("Service is listening for a service request in port " + port);

        GeneralRepositoryClientProxy cliProxy;

        while (waitConnection) {
            try {
                sconi = scon.accept();
                cliProxy = new GeneralRepositoryClientProxy(sconi, repoInterface);
                cliProxy.start();
            } catch (SocketTimeoutException e) {
            }
        }
        scon.end();
        System.out.println("Server was shutdown.");
    }

}
