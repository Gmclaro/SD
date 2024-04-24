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
    
}
