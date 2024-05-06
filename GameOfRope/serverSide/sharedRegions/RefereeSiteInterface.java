package serverSide.sharedRegions;

import clientSide.entities.RefereeState;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;
import serverSide.entities.RefereeSiteClientProxy;

public class RefereeSiteInterface {

    // TODO: javadoc

    private final RefereeSite refereeSite;

    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
    }

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

            // TODO: missing msgType here

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

            // TODO: missing msgType here

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
