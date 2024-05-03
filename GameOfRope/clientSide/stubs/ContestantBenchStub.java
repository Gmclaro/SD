package clientSide.stubs;

import clientSide.entities.*;
import commonInfra.*;

public class ContestantBenchStub {
    /**
     * Name of the platform where is located the Contestant Bench server
     */
    private String serverHostName;
    /**
     * Port number for listening to service requests
     */
    private int serverPortNumb;

    /**
     * Instantiation of a contestant bench stub
     * 
     * @param hostName
     *                 name of the platform where is located the contestant bench
     *                 server
     * @param port
     *                 port number for listening to service requests
     */

    public ContestantBenchStub(String hostname, int port) {
        serverHostName = hostname;
        serverPortNumb = port;
    }

    public void callTrial() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_CALL_TRIAL, ((Referee) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_CALL_TRIAL) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH
                || inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid entity state!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        ((Referee) Thread.currentThread()).setEntityState(inMessage.getEntityState());
        System.out.println("\nCBS callTrial() -> Sta" + ((Referee) Thread.currentThread()).getEntityState());
    }

    public View[] reviewNotes(int team) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_REVIEW_NOTES, team);

        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_REVIEW_NOTES) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getTeam() != ((Coach) Thread.currentThread()).getTeam()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid coachID!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        System.out.println("\nCBS reviewNotes()");
        return inMessage.getAboutContestants();
    }

    public void seatDown(int team, int id) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_SEAT_DOWN, team, id,
                ((Contestant) Thread.currentThread()).getStrength(),
                ((Contestant) Thread.currentThread()).getEntityState());

        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_SEAT_DOWN) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getTeam() != ((Contestant) Thread.currentThread()).getTeam()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getID() != ((Contestant) Thread.currentThread()).getID()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid id!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getEntityState() < ContestantState.SEAT_AT_THE_BENCH
                || inMessage.getEntityState() > ContestantState.DO_YOUR_BEST) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid entity state!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        ((Contestant) Thread.currentThread()).setEntityState(inMessage.getEntityState());
        System.out.println("\nCBS seatDown() -> Sta" + ((Contestant) Thread.currentThread()).getEntityState());

    }

    public int waitForCallTrial(int team) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_CALL_TRIAL, team);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_CALL_TRIAL) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getTeam() != ((Coach) Thread.currentThread()).getTeam()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getEntityState() < CoachState.WAIT_FOR_REFEREE_COMMAND
                || inMessage.getEntityState() > CoachState.WATCH_TRIAL) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid entity state!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((Coach) Thread.currentThread()).setEntityState(inMessage.getEntityState());
        System.out.println("\nCBS waitForCallTrial() -> O" + inMessage.getOrders());
        return inMessage.getOrders();

    }

    public void callContestants(int team, int[] selected) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_CALL_CONTESTANTS, team, selected);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();


        if (inMessage.getMsgType() != MessageType.REP_CALL_CONTESTANTS) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getTeam() != ((Coach) Thread.currentThread()).getTeam()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        System.out.println("\nCBS callContestants() ");

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

        outMessage = new Message(MessageType.REQ_CONTESTANT_BENCH_SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_CONTESTANT_BENCH_SHUTDOWN) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }



}
