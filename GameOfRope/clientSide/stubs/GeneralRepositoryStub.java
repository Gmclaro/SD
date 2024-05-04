package clientSide.stubs;

import commonInfra.ClientCom;
import commonInfra.Message;
import commonInfra.MessageType;

public class GeneralRepositoryStub {
  /**
   * Name of the plataform where is located the general repo server
   */
  private String serverHostName;

  /**
   * Port number for listening to service requests
   */
  private int serverPortNumb;

  /**
   * Instantiation of a general repo stub.
   *
   * @param serverHostName name of the platform where is located the general repo
   *                       server
   * @param serverPortNumb port number for listening to service requests
   */
  public GeneralRepositoryStub(String serverHostName, int serverPortNumb) {
    this.serverHostName = serverHostName;
    this.serverPortNumb = serverPortNumb;
  }

  public void initSimul(int[][] contestantStrength){
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_INIT_SIMUL, contestantStrength);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_INIT_SIMUL) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in initSimul()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("\nGRS initSimul()");
  }

  public void setRefereeState(int state) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_REFEREE_STATE, state);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_REFEREE_STATE) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setRefereeState()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("\nGRS setRefereeState()");

  }

  // TODO : Missing set Entity states

  public void setCoachState(int team, int state) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_COACH_STATE,team, state);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_COACH_STATE) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setCoachState()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("GRS setCoachState()");
  }

  public void setContestantState(int team, int id, int state) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_CONTESTANT_STATE, team, id, state);

    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_CONTESTANT_STATE) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setContestantState()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("GRS setContestantState()");
  }

  public void setContestantStrength(int team, int id, int strength) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_CONTESTANT_STRENGTH, team, id, strength);

    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_CONTESTANT_STRENGTH) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setContestantStrength()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("GRS setContestantStrength()");
  }

  // TOOD: newGameStated
  public void newGameStarted() {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_NEW_GAME_STARTED);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_NEW_GAME_STARTED) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in newGameStarted()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();

    System.out.println("\nGRS newGameStarted()");
  }

  // TODO: setNewTrial
  public void setNewTrial() {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_SET_NEW_TRIAL);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_SET_NEW_TRIAL) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setNewTrial()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
    System.out.println("\nGRS setNewTrial()");

  }

  public void setActiveContestant(int team, int id) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_ACTIVE_CONTESTANT, team, id);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_ACTIVE_CONTESTANT) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setActiveContestant()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    
    com.close();
    System.out.println("\nGRS setActiveContestant()");
  }

  public void setRemoveContestant(int team, int id) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_LOG_SET_REMOVE_CONTESTANT, team, id);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_LOG_SET_REMOVE_CONTESTANT) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setRemoveContestant()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    com.close();
    System.out.println("\nGRS setRemoveContestant()");

  }

  public void setRopePosition(int ropePosition) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_SET_ROPE_POSITION, ropePosition);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_SET_ROPE_POSITION) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setRopePosition()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    com.close();
    System.out.println("\nGRS setRopePosition()");
  }

  // TODO : setEndOfGame
  public void setEndOfGame() {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_SET_END_OF_GAME);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_SET_END_OF_GAME) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setEndOfGame()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    com.close();
    System.out.println("\nGRS setEndOfGame()");
  }

  // TODO: showGameResult
  public void showGameResult(int difference) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_SHOW_GAME_RESULT, difference);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_SHOW_GAME_RESULT) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in showGameResult()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    com.close();
    System.out.println("\nGRS showGameResult()");
  }

  // TODO: setMatchWinner
  public void setMatchWinner(int[] scores) {
    ClientCom com;
    Message outMessage, inMessage;

    com = new ClientCom(serverHostName, serverPortNumb);

    while (!com.open()) {
      try {
        Thread.currentThread().sleep((long) (10));
      } catch (InterruptedException e) {
      }
    }

    outMessage = new Message(MessageType.REQ_SET_MATCH_WINNER, scores);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_SET_MATCH_WINNER) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Type error in setMatchWinner()");
      System.out.println(inMessage.toString());
      System.exit(1);
    }
    com.close();
    System.out.println("\nGRS setMatchWinner()");
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

    outMessage = new Message(MessageType.REQ_GENERAL_REPOSITORY_SHUTDOWN);
    com.writeObject(outMessage);
    inMessage = (Message) com.readObject();

    if (inMessage.getMsgType() != MessageType.REP_GENERAL_REPOSITORY_SHUTDOWN) {
      System.out.println("Thread " + Thread.currentThread().getName() + ":Invalid message type!");
      System.out.println(inMessage.toString());
      System.exit(1);
    }

    com.close();
  }

}
