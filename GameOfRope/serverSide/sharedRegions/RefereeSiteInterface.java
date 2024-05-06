package serverSide.sharedRegions;

import clientSide.entities.RefereeState;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;
import serverSide.entities.RefereeSiteClientProxy;


/**
 * Interface to the Referee Site
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * Referee Site and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class RefereeSiteInterface {

    /**
     * Reference to the Referee Site
     */
    private final RefereeSite refereeSite;

    /**
     * Instantiation of an interface to the Referee Site.
     *
     * @param refereeSite Reference to the Referee Site
     */
    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
    }

    /**
     * Processing of the incoming messages
     * Validation, execution of the corresponding method and generation of the outgoing message.
     *
     * @param inMessage service request
     * @return service reply
     * @throws MessageException if incoming message was not valid
     */

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        /* Validate messages */
        switch (inMessage.getMsgType()) {
            case MessageType.REQ_ANNOUNCE_NEW_GAME:
                if ((inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH)
                        || (inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH)) {
                    throw new MessageException("Invalid Referee state!", inMessage);
                }
                break;
            case MessageType.REQ_INFORM_REFEREE:
                // No validation needed
                break;
            case MessageType.REQ_WAIT_FOR_INFORM_REFEREE:
                // No validation needed
                break;

            case MessageType.REQ_REFEREE_SITE_SHUTDOWN:
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        /* Process Messages */
        switch (inMessage.getMsgType()) {
            case MessageType.REQ_ANNOUNCE_NEW_GAME:
                ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(inMessage.getEntityState());

                refereeSite.announceNewGame();
                outMessage = new Message(MessageType.REP_ANNOUNCE_NEW_GAME, RefereeState.START_OF_A_GAME);
                break;

            case MessageType.REQ_REFEREE_SITE_SHUTDOWN:
                refereeSite.shutdown();
                outMessage = new Message(MessageType.REP_REFEREE_SITE_SHUTDOWN);
                break;
            case MessageType.REQ_INFORM_REFEREE:
                refereeSite.informReferee();
                outMessage = new Message(MessageType.REP_INFORM_REFEREE);
                break;
            case MessageType.REQ_WAIT_FOR_INFORM_REFEREE:
                refereeSite.waitForInformReferee();
                outMessage = new Message(MessageType.REP_WAIT_FOR_INFORM_REFEREE);
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        System.out.println("\nRSI processAndReply() -> Reply: " + outMessage.toString());
        return (outMessage);
    }

}
