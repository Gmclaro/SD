package serverSide.sharedRegions;

import clientSide.entities.ContestantState;
import clientSide.entities.RefereeState;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;
import serverSide.main.SimulParse;

public class GeneralRepositoryInterface {

    private final GeneralRepository repo;

    public GeneralRepositoryInterface(GeneralRepository repo) {
        this.repo = repo;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_LOG_SET_REFEREE_STATE:
                if (inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH
                        || inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH) {
                    throw new MessageException("Invalid Referee State", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_CONTESTANT_STATE:
            System.out.println("Gen Repo Contestanr state: " + inMessage.getEntityState());
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                } else if (inMessage.getEntityState() < ContestantState.SEAT_AT_THE_BENCH
                        || inMessage.getEntityState() > ContestantState.DO_YOUR_BEST) {
                    throw new MessageException("Invalid Contestant State", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_REMOVE_CONTESTANT:
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                }
                break;

            case MessageType.REQ_NEW_GAME_STARTED:
                // No parameters to validate
                break;

            case MessageType.REQ_SET_NEW_TRIAL:
                // No parameters to validate
                break;

            default:
                break;
        }

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_LOG_SET_REFEREE_STATE:
                repo.setRefereeState(inMessage.getEntityState());
                System.out.println("Gen Repo Referee state: " + inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_REFEREE_STATE);
                break;
            case MessageType.REQ_LOG_SET_CONTESTANT_STATE:
                repo.setContestantState(inMessage.getTeam(), inMessage.getID(), inMessage.getEntityState());
                System.out.println("Gen Repo Contestanr state: " + inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_CONTESTANT_STATE);
                break;

            case MessageType.REQ_LOG_SET_REMOVE_CONTESTANT:
                repo.setRemoveContestant(inMessage.getTeam(), inMessage.getID());
                outMessage = new Message(MessageType.REP_LOG_SET_REMOVE_CONTESTANT);
                break;
            case MessageType.REQ_NEW_GAME_STARTED:
                repo.newGameStarted();
                outMessage = new Message(MessageType.REP_NEW_GAME_STARTED);
                break;
            case MessageType.REQ_SET_NEW_TRIAL:
                repo.setNewTrial();
                outMessage = new Message(MessageType.REP_SET_NEW_TRIAL);
                break;

            default:
                break;
        }

        System.out.println("\nGRI processAndReply() -> Reply: " + outMessage.toString());
        return (outMessage);
    }

}
