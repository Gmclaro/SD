package clientSide.main;

import clientSide.entities.Referee;
import interfaces.*;
import java.rmi.*;
import java.rmi.registry.*;;

/**
 * Client side of the Game of Rope problem(Referee).
 * Impletation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ClientGameOfRopeReferee {
    /**
     * Main method.
     * 
     * @param args runtime arguments
     *             args[0] - name of the platform where is located the RMI
     *             registering service
     *             args[1] - port number where the registering service is listening
     *             to service requests
     */
    public static void main(String[] args) {
        Referee referee;
        String rmiRegHostName;
        int rmiRegPortNumb = -1;
        /* Getting problem runtime parameters */
        if (args.length != 2) {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }

        rmiRegHostName = args[0];

        try {
            rmiRegPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit(1);
        }

        /* problem intitailization */

        String nameEntryContestantBench = "ContestantBench";
        ContestantBenchInterface contestantBenchStub = null;
        String nameEntryPlayground = "Playground";
        PlaygroundInterface playgroundStub = null;
        String nameEntryRefereeSite = "RefereeSite";
        RefereeSiteInterface refereeSiteStub = null;
        GeneralRepositoryInterface generalRepositoryStub = null;
        String nameEntryGeneralRepository = "GeneralRepository";

        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }
        try {
            contestantBenchStub = (ContestantBenchInterface) registry.lookup(nameEntryContestantBench);
        } catch (RemoteException e) {
            System.out.println("ContestantBench lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ContestantBench not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        try {
            playgroundStub = (PlaygroundInterface) registry.lookup(nameEntryPlayground);
        } catch (RemoteException e) {
            System.out.println("Playground lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Playground not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            refereeSiteStub = (RefereeSiteInterface) registry.lookup(nameEntryRefereeSite);
        } catch (RemoteException e) {
            System.out.println("RefereeSite lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RefereeSite not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            generalRepositoryStub = (GeneralRepositoryInterface) registry.lookup(nameEntryGeneralRepository);
        } catch (RemoteException e) {
            System.out.println("GeneralRepository lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepository not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        referee = new Referee(playgroundStub, refereeSiteStub, contestantBenchStub);

        System.out.println("Lauching Referee Thread.");
        referee.start();

        try{
            referee.join();
        }catch(InterruptedException e){
            System.out.println("Referee thread has ended.");
        }

        try{
            playgroundStub.shutdown();
        }catch(RemoteException e){
            System.out.println("Playground shutdown exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try{
            contestantBenchStub.shutdown();
        }catch(RemoteException e){
            System.out.println("ContestantBench shutdown exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try{
            refereeSiteStub.shutdown();
        }catch(RemoteException e){
            System.out.println("RefereeSite shutdown exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        try{
            generalRepositoryStub.shutdown();
        }catch(RemoteException e){
            System.out.println("GeneralRepository shutdown exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
