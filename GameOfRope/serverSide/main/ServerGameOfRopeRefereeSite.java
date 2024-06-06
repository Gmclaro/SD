package serverSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import serverSide.objects.*;
import interfaces.*;

/**
 * RefereeSite (shared region)
 * This class implements the RefereeSite shared region.
 * Public methods executed in mutual exclusion are implemented.
 * Instantion and registering of a server object that enables the simulatoion to
 * run
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ServerGameOfRopeRefereeSite {
    public static boolean end = false;

    /**
     * Main method
     * 
     * @param args runtime arguments
     * 
     *             args[0] - port number for listening to service requests
     *             args[1] - name of the platform where is located the RMI
     *             registering service
     *             args[2] - port number where the registering service is listening
     *             to service requests
     */

    public static void main(String[] args) {
        int portNumb = -1; // port number for listening to service requests
        String rmiRegHostName; // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1; // port number where the registering service is listening to service requests

        if (args.length != 3) {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }

        try {
            portNumb = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("args[0] is not a number!");
            System.exit(1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536)) {
            System.out.println("args[0] is not a valid port number!");
            System.exit(1);
        }

        rmiRegHostName = args[1];
        try {
            rmiRegPortNumb = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("args[2] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[2] is not a valid port number!");
            System.exit(1);
        }

        /* create and install the security manager */
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        System.out.println("Security manager was installed!");

        // instantiate registry service object
        Registry registry = null; // remote reference for registration in the RMI registry service

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        GeneralRepositoryInterface generalRepositoryStub = null;

        try {
            generalRepositoryStub = (GeneralRepositoryInterface) registry.lookup("GeneralRepository");
        } catch (RemoteException e) {
            System.out.println("GeneralRepository lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepository not bound to registry: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        try {
            generalRepositoryStub = (GeneralRepositoryInterface) registry.lookup("GeneralRepository");
        } catch (RemoteException e) {
            System.out.println("GeneralRepository lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepository not bound to registry: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        RefereeSite refereeSite = new RefereeSite(generalRepositoryStub);
        RefereeSiteInterface refereeSiteStub = null;

        try {
            refereeSiteStub = (RefereeSiteInterface) UnicastRemoteObject.exportObject(refereeSite, portNumb);
        } catch (RemoteException e) {
            System.out.println("RefereeSite stub generation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("RefereeSite stub was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "RefereeSite";
        Register reg = null;

        try{
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            reg.bind(nameEntryObject, refereeSiteStub);
        } catch (RemoteException e) {
            System.out.println("RefereeSite registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("RefereeSite already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("RefereeSite object was registered!");

        System.out.println("RefereeSite is in service.");

        try{
            while(!end)synchronized(Class.forName("serverSide.main.ServerGameOfRopeRefereeSite")){
                try{
                    Class.forName("serverSide.main.ServerGameOfRopeRefereeSite").wait();
                } catch (ClassNotFoundException | InterruptedException e) {
                    System.out.println("RefereeSite main thread was interrupted");
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type serverSide.main.ServerGameOfRopeRefereeSite was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }
        boolean shutdownDone = false;

        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("RefereeSite registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RefereeSite not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RefereeSite was deregistered!");

        try{
            shutdownDone = UnicastRemoteObject.unexportObject(refereeSite, true);
        }catch(NoSuchObjectException e){
            System.out.println("RefereeSite unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        if(shutdownDone)
            System.out.println("RefereeSite was shutdown!");
    }

    /**
     * Shutdown the RefereeSite service
     */

    public static void shutdown() {
        end = true;
        try {
            synchronized(Class.forName("serverSide.main.ServerGameOfRopeRefereeSite")){
                Class.forName("serverSide.main.ServerGameOfRopeRefereeSite").notify();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type RefereeSite was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
