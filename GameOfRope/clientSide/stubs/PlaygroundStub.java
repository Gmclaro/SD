package clientSide.stubs;

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
     *            name of the platform where is located the playground
     *            server
     * @param port
     *            port number for listening to service requests
     */

    public PlaygroundStub(String hostname, int port) {
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

        outMessage = new Message(MessageType.REQ_PLAYGROUND_SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if(inMessage.getMsgType() != MessageType.REP_PLAYGROUND_SHUTDOWN){
            System.out.println("Thread "+ Thread.currentThread().getName()+ ":Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }

        com.close();
    }
}
