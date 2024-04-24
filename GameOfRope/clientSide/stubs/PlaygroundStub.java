package clientSide.stubs;

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
}
