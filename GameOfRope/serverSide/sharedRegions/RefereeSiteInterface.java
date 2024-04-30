package serverSide.sharedRegions;

import clientSide.entities.RefereeState;
import commonInfra.*;
import serverSide.entities.*;

public class RefereeSiteInterface {

    // TODO: javadoc

    private final RefereeSite refereeSite;

    public RefereeSiteInterface(RefereeSite refereeSite) {
        this.refereeSite = refereeSite;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        System.out.println("inMessage:\n" + inMessage.toString());

        /* Validate messages */
        switch (inMessage.getMsgType()) {
            case MessageType.REQ_ANNOUNCE_NEW_GAME:
                if ((inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH)
                        || (inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH)) {
                    throw new MessageException("Invalid Referee state!", inMessage);
                }
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

            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        return (outMessage);
    }

}
