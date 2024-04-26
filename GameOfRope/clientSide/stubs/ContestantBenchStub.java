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
     *            name of the platform where is located the contestant bench
     *            server
     * @param port
     *            port number for listening to service requests
     */

    public ContestantBenchStub(String hostname, int port) {
        serverHostName = hostname;
        serverPortNumb = port;
    }
 
    

    public void shutdown() {
        ClientCom com;
        Message inMessage,outMessage;

        com = new ClientCom(serverHostName, serverPortNumb);

        while(!com.open()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }

        outMessage = new Message(MessageType.REQ_CONTESTANT_BENCH_SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if(inMessage.getMsgType() != MessageType.REP_CONTESTANT_BENCH_SHUTDOWN){
            System.out.println("Thread "+ Thread.currentThread().getName()+ ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
