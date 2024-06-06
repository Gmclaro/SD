package clientSide.main;

import clientSide.entities.Contestant;
import serverSide.main.SimulParse;
import interfaces.*;
import java.rmi.*;
import java.rmi.registry.*;

/**
 * Client side of the Game of Rope problem(Contestant).
 * Impletation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ClientGameOfRopeContestant {

    /**
     * Main method.
     * 
     * @param args runtime arguments
     *            args[0] - name of the platform where is located the RMI registering service
     *           args[1] - port number where the registering service is listening to service requests
     */

     public static void main(String[]args){
        Contestant[][] contestant = new Contestant[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
        String rmiRegHostName;
        int rmiRegPortNumb = -1;

        if (args.length != 2) {
            System.out.println("Wrong number of parameters!");
            System.exit (1);
        }

        rmiRegHostName = args[0];
        try {
            rmiRegPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit (1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit (1);
        }


        String nameEntryContestantBench = "ContestantBench";
        ContestantBenchInterface contestantBenchStub = null;
        String nameEntryPlayground = "Playground";
        PlaygroundInterface playgroundStub = null;
        String nameEntryGeneralRepository = "GeneralRepository";
        GeneralRepositoryInterface generalRepositoryStub = null;

        Registry registry = null;


        try{
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        }catch(RemoteException e){
            System.out.println("RMI not working");
            System.exit(1);
        }try{
            contestantBenchStub = (ContestantBenchInterface) registry.lookup(nameEntryContestantBench);
        }catch(RemoteException e){
            System.out.println("ContestantBench lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }catch(NotBoundException e){
            System.out.println("ContestantBench not bound"+ e.getMessage());
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
            generalRepositoryStub = (GeneralRepositoryInterface) registry.lookup (nameEntryGeneralRepository);
        } catch (RemoteException e) {
            System.out.println("General Repository lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("General Repository not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        int strength [][]=  new int[SimulParse.COACH][SimulParse.CONTESTANT_PER_TEAM];
        for(int i = 0; i< SimulParse.COACH; i++){
            for(int j = 0; j< SimulParse.CONTESTANT_PER_TEAM; j++){
                strength[i][j] = (int) (Math.random() * 5) + 6;
            }
        }

        try{
            generalRepositoryStub.initSimul(strength);
        }catch(RemoteException e){
            System.out.println("initSimul exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        for(int i = 0; i< SimulParse.COACH; i++){
            for(int j = 0; j< SimulParse.CONTESTANT_PER_TEAM; j++){
                contestant[i][j] = new Contestant(i, j, strength[i][j], playgroundStub, contestantBenchStub);
                contestant[i][j].start();
            }
        }

        for(int i = 0; i< SimulParse.COACH; i++){
            for(int j = 0; j< SimulParse.CONTESTANT_PER_TEAM; j++){
                try{
                    contestant[i][j].join();
                }catch(InterruptedException e){
                    System.out.println("Contestant main thread" + i + " " + j + " has been interrupted.");
                }
            }
        }

        System.out.println("Contestant main thread has ended.");

        try{
            contestantBenchStub.shutdown();
        }catch(RemoteException e){
            System.out.println("ContestantBench shutdown exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try{
            playgroundStub.shutdown();
        }catch(RemoteException e){
            System.out.println("Playground shutdown exception: " + e.getMessage());
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
