package clientSide.stubs;

public class GeneralRepositoryStub {
        /**
     * Name of the plataform where is located the  general repo server
     */
    private String serverHostName;

    /**
	 * Port number for listening to service requests
     */
    private int serverPortNumb;

    /**
     *  Instantiation of a general repo stub.
     *
     *    @param serverHostName name of the platform where is located the general repo server
     *    @param serverPortNumb port number for listening to service requests
     */
    public GeneralRepositoryStub(String serverHostName, int serverPortNumb) {
		this.serverHostName = serverHostName;
		this.serverPortNumb = serverPortNumb;
    }
}
