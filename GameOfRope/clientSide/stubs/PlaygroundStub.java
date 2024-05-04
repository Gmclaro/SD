package clientSide.stubs;

import clientSide.entities.*;
import commonInfra.ClientCom;
import commonInfra.Message;
import commonInfra.MessageType;

public class PlaygroundStub {
    /**
     * Name of the platform where is located the Playground server
     */
    private String serverHostName;
    /**
     * Port number for listening to service requests
     */
    private int serverPortNumb;

    /**
     * Instantiation of a Playground stub
     * 
     * @param hostName
     *                 name of the platform where is located the playground
     *                 server
     * @param port
     *                 port number for listening to service requests
     */

    public PlaygroundStub(String hostname, int port) {
        serverHostName = hostname;
        serverPortNumb = port;
    }

    // TODO: followCoachAdvice
    public void followCoachAdvice(int team) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_FOLLOW_COACH_ADVICE, team);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_FOLLOW_COACH_ADVICE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        if (inMessage.getTeam() != ((Contestant) Thread.currentThread()).getTeam()) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
        System.out.println("\nPS followCoachAdvice()");

    }

    // TODO: waitforFollowCoachAdvice
    public void waitForFollowCoachAdvice(int team) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_FOLLOW_COACH_ADVICE, team,
                ((Coach) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_FOLLOW_COACH_ADVICE) {
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
        System.out
                .println("\nPS waitForFollowCoachAdvice() -> Sta" + ((Coach) Thread.currentThread()).getEntityState());
    }

    // TODO: startTrial

    public void startTrial() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_START_TRIAL, ((Referee) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_START_TRIAL) {
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
        System.out.println("\nPS startTrial() -> Sta" + ((Referee) Thread.currentThread()).getEntityState());

    }

    // TODO: waitforStartTrial
    public void waitForStartTrial(int team, int id) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_START_TRIAL, team, id,
                ((Contestant) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_START_TRIAL) {
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
        System.out.println("\nPS waitForStartTrial() -> Sta" + ((Contestant) Thread.currentThread()).getEntityState());

    }

    // TODO: getReady
    public void getReady(int team, int id) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_GET_READY, team, id);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_GET_READY) {
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
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        System.out.println("\nPS getReady()");
    }

    // TODO : waitForAmDone

    public void waitForAmDone() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_AM_DONE);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_AM_DONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        System.out.println("\nPS waitForAmDone()");
    }

    // TODO: assertTrialDecision

    public boolean assertTrialDecision() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.REQ_ASSERT_TRIAL_DECISION);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_ASSERT_TRIAL_DECISION) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
        System.out.println("\nPS assertTrialDecision() -> CG" + inMessage.getContinueGame());
        return inMessage.getContinueGame();
    }

    public void waitForAssertTrialDecision(int team, int id, int strength) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_ASSERT_TRIAL_DECISION_CONTESTANT, team, id, strength,
                ((Contestant) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_ASSERT_TRIAL_DECISION_CONTESTANT) {
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
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid team!");
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
        ((Contestant) Thread.currentThread()).setStrength(inMessage.getStrength());
        ((Contestant) Thread.currentThread()).setEntityState(inMessage.getEntityState());
        System.out.println(
                "\nPS waitForAssertTrialDecision() -> ConSta" + ((Contestant) Thread.currentThread()).getEntityState());
    }

    // TODO: waitForAssertTrialDecision

    public void waitForAssertTrialDecision(int team) {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH, team,
                ((Coach) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH) {
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
        System.out.println(
                "\nPS waitForAssertTrialDecision() -> CoaSta" + ((Coach) Thread.currentThread()).getEntityState());
    }

    // TODO: declareGameWinner

    public int declareGameWinner() {
        ClientCom com;
        Message inMessage, outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while (!com.open()) {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.REQ_DECLARE_GAME_WINNER, ((Referee) Thread.currentThread()).getEntityState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_DECLARE_GAME_WINNER) {
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
        
        System.out.println("\nPS declareGameWinner() -> Sta" + ((Referee) Thread.currentThread()).getEntityState() + " RP" + inMessage.getRopePosition());
        return inMessage.getRopePosition();
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

        outMessage = new Message(MessageType.REQ_PLAYGROUND_SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.REP_PLAYGROUND_SHUTDOWN) {
            System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
