package serverSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import serverSide.objects.*;
import interfaces.*;

/**
 * Playground (shared region)
 * This class implements the Playground shared region.
 * Public methods executed in mutual exclusion are implemented.
 * Instantion and registering of a server object that enables the simulatoion to
 * run
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class ServerGameOfRopePlayground {
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

        Playground playground = new Playground(generalRepositoryStub);
        PlaygroundInterface playgroundStub = null;

        try {
            playgroundStub = (PlaygroundInterface) UnicastRemoteObject.exportObject(playground, portNumb);
        } catch (RemoteException e) {
            System.out.println("Playground stub create exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Playground stub was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "Playground";
        Register reg = null;

        try {
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
            reg.bind(nameEntryObject, playgroundStub);
        } catch (RemoteException e) {
            System.out.println("Playground registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Playground already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Playground object was registered!");

        System.out.println("Playground is in service!");

        try {
            while (!end)
                synchronized (Class.forName("serverSide.main.ServerGameOfRopePlayground")) {
                    try {
                        (Class.forName("serverSide.main.ServerGameOfRopePlayground")).wait();
                    } catch (InterruptedException e) {
                        System.out.println("Playground main thread was interrupted!");
                    }
                }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type serverSide.main.ServerGameOfRopePlayground was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }

        boolean shutdownDone = false;

        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Playground deregistration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Playground not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Playground was deregistered!");

        try {
            shutdownDone = UnicastRemoteObject.unexportObject(playground, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Playground unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        if (shutdownDone)
            System.out.println("Playground was shutdown!");
    }

    /**
     * Shutdown the Playground service
     */

    public static void shutdown() {
        end = true;
        try {
            synchronized (Class.forName("serverSide.main.ServerGameOfRopePlayground")) {
                (Class.forName("serverSide.main.ServerGameOfRopePlayground")).notify();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type Playground was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
