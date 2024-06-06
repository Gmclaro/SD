package clientSide.main;

import clientSide.entities.Coach;
import commonInfra.Strategy.StrategyType;
import serverSide.main.SimulParse;
import interfaces.*;
import java.rmi.*;
import java.rmi.registry.*;

/**
 * Client side of Coach
 * Impletation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ClientGameOfRopeCoach {

    /**
     * Main program.
     * args[0]- name of the platform whewre is located the RMI registering service
     * args[1]- port nunber where the registering service is listening to service
     * requests
     */

    public static void main(String[] args) {
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
        String nameEntryGeneralRepository = "GeneralRepository";
        GeneralRepositoryInterface generalRepositoryStub = null;

        Registry registry = null;
        Coach[] coach = new Coach[SimulParse.COACH];


        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI creation Exception " + rmiRegPortNumb);
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
            System.out.println("General Repository lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("General Repository not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        for (int i = 0; i < SimulParse.COACH; i++) {
            coach[i] = new Coach(i, contestantBenchStub, playgroundStub, refereeSiteStub, selectStrategy(i));

            System.out.println("Coach " + i + " started");
            coach[i].start();
        }

        try {
            for (int i = 0; i < SimulParse.COACH; i++) {
                coach[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread of coach was teminated!");
            System.exit(1);
        }

        System.out.println("Coach thread was terminated!");

        try {
            contestantBenchStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Remote exception on contestantBench shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            playgroundStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Remote exception on playground shutdown: " + e.getMessage());
            System.exit(1);
        }

        try {
            refereeSiteStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Remote exception on refereeSite shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            generalRepositoryStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Remote exception on generalRepositoryStub shutdown: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Select the strategy for the coach.
     * 
     * @param team team id
     * @return strategy type
     */

    public static StrategyType selectStrategy(int team) {
        switch (((int) (Math.random() * 3))) {
            case 0:
                System.out.println("Team " + team + "STRONGEST");
                return StrategyType.STRONGEST;
            case 1:
                System.out.println("Team " + team + "FIFO");
                return StrategyType.FIFO;
            case 2:
                System.out.println("Team " + team + "RANDOM");
                return StrategyType.RANDOM;

            default:
                System.out.println("Team " + team + "RANDOM");
                return StrategyType.RANDOM;
        }
    }
}
