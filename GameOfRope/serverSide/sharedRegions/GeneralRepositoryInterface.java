package serverSide.sharedRegions;

import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;

public class GeneralRepositoryInterface {

    private final GeneralRepository repo;

    public GeneralRepositoryInterface(GeneralRepository repo) {
        this.repo = repo;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        if (inMessage.getMsgType() < MessageType.REQ_LOG_SET_REFEREE_STATE
                || inMessage.getMsgType() > MessageType.REQ_SET_MATCH_WINNER) {
            throw new MessageException("Invalid Message type", inMessage);
        }

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_LOG_SET_REFEREE_STATE:
                repo.setRefereeState(inMessage.getEntityState());
                System.out.println("Gen Repo Referee state: " + inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_REFEREE_STATE);
                break;
            case MessageType.REQ_NEW_GAME_STARTED:
                repo.newGameStarted();
                outMessage = new Message(MessageType.REP_NEW_GAME_STARTED);
                break;

            default:
                break;
        }

        return (outMessage);
    }

}
