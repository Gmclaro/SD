package clientSide.stubs;

import commonInfra.*;
import clientSide.entities.*;

public class RefereeSiteStub {
    /**
     * Name of the platform where is located the RefereeSite server
     */
    private String serverHostName;
    /**
     * Port number for listening to service requests
     */
    private int serverPortNumb;

    /**
     * Instantiation of a Referee site stub
     * 
     * @param hostName
     *                 name of the platform where is located the referee site
     *                 server
     * @param port
     *                 port number for listening to service requests
     */

    public RefereeSiteStub(String hostname, int port) {
        serverHostName = hostname;
        serverPortNumb = port;
    }

    public void announceNewGame() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_ANNOUNCE_NEW_GAME, RefereeState.START_OF_THE_MATCH);
        System.out.println("outMessage:\n" + outMessage.toString());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_ANNOUNCE_NEW_GAME) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println("Expected:" + MessageType.REP_ANNOUNCE_NEW_GAME + " Got: " + inMessage.getMsgType());
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        if (inMessage.getEntityState() != RefereeState.START_OF_A_GAME) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid referee state!");
            System.out.println("Expected:" + RefereeState.START_OF_A_GAME + " Got: " + inMessage.getEntityState());
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        ((Referee) Thread.currentThread()).setEntityState(inMessage.getEntityState());

        com.close();
    }

    // TODO: informReferee
    public void informReferee() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

    }

    // TODO: waitForInformReferee
    public void waitForInformReferee() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
    }

    public void shutdown() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_REFEREE_SITE_SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_REFEREE_SITE_SHUTDOWN) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
