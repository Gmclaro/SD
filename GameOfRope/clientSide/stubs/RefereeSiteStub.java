package clientSide.stubs;

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
     *            name of the platform where is located the referee site
     *            server
     * @param port
     *            port number for listening to service requests
     */

    public RefereeSiteStub(String hostname, int port) {
        serverHostName = hostname;
        serverPortNumb = port;
    }
}
