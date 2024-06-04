package serverSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import serverSide.objects.*;
import interfaces.*;

/**
 * Server side of the General Repository.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ServerGameOfRopeGeneralRepository {

    /**
     * Flag signaling the end of operations.
     */
    public static boolean end = false;

    /**
     * Main method.
     * 
     * @param args runtime arguments
     *  args[0] - port number for listening to service requests
     *  args[1] - name of the platform where is located the RMI registering service
     *  args[2] - port number where the registering service is listening to service requests
     */
    public static void main(String[] args){
        int portNumb = -1;                                             // port number for listening to service requests
        String rmiRegHostName;                                         // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;                                       // port number where the registering service is listening to service requests

        if(args.length != 3){
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }

        try{
            portNumb = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e){
            System.out.println("args[0] is not a number!");
            System.exit(1);
        }
        if((portNumb < 22130) || (portNumb >= 22139)){
            System.out.println("args[0] is not a valid port number!");
            System.exit(1);
        }

        rmiRegHostName = args[1];
        try{
            rmiRegPortNumb = Integer.parseInt(args[2]);
        }
        catch(NumberFormatException e){
            System.out.println("args[2] is not a number!");
            System.exit(1);
        }
        if((rmiRegPortNumb < 22130) || (rmiRegPortNumb >= 22139)){
            System.out.println("args[2] is not a valid port number!");
            System.exit(1);
        }

        /* create and install the security manager */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");

        // instantiate registry service object
        Registry registry = null;                                       // remote reference for registration in the RMI registry service

        try{
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        }
        catch(RemoteException e){
            System.out.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        GeneralRepository generalRepository = new GeneralRepository("log.txt");
        GeneralRepositoryInterface generalRepositoryStub = null;


        try{
            generalRepositoryStub = (GeneralRepositoryInterface) UnicastRemoteObject.exportObject(generalRepository, portNumb);
        }
        catch(RemoteException e){
            System.out.println("GeneralRepository stub creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("The stub for the GeneralRepository was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "GeneralRepository";
        Register reg = null;

        try{
            reg = (Register) registry.lookup(nameEntryBase);
        }
        catch(RemoteException e){
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(NotBoundException e){
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try{
            reg.bind(nameEntryObject, generalRepositoryStub);
        }
        catch(RemoteException e){
            System.out.println(nameEntryObject + " registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(AlreadyBoundException e){
            System.out.println(nameEntryObject + " already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(nameEntryObject + " object was registered!");

        System.out.println(nameEntryObject + " is in Operation!");


        try{
            while(!end) synchronized(Class.forName("serverSide.main.ServerGameOfRopeGeneralRepository")){
                try{
                Class.forName("serverSide.main.ServerGameOfRopeGeneralRepository").wait();
                }catch(InterruptedException e){
                    System.out.println("General Repository was interrupted!");
                }
            }
        }catch(ClassNotFoundException e){
            System.out.println("The Data Type GeneralRepository was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }
        boolean shutdownDone = false;

        try{
            reg.unbind(nameEntryObject);
        }
        catch(NotBoundException e){
            System.out.println(nameEntryObject + " is not registered!");
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            System.out.println("GeneralRepository deregistration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try{
            shutdownDone = UnicastRemoteObject.unexportObject(generalRepository, true);
        }
        catch(NoSuchObjectException e){
            System.out.println("GeneralRepository unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        if(shutdownDone)
            System.out.println("GeneralRepository was shutdown!");
    }

    /**
     * Shutdown the General Repository.
     */
    public static void shutdown(){
        end = true;
        try{
            synchronized(Class.forName("serverSide.main.ServerGameOfRopeGeneralRepository")){
                Class.forName("serverSide.main.ServerGameOfRopeGeneralRepository").notify();
            }
        }catch(ClassNotFoundException e){
            System.out.println("The Data Type GeneralRepository was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
