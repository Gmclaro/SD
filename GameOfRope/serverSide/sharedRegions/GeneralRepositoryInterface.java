package serverSide.sharedRegions;

import clientSide.entities.Coach;
import clientSide.entities.*;
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
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                } else if (inMessage.getEntityState() < ContestantState.SEAT_AT_THE_BENCH
                        || inMessage.getEntityState() > ContestantState.DO_YOUR_BEST) {
                    throw new MessageException("Invalid Contestant State", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_COACH_STATE:
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getEntityState() < CoachState.WAIT_FOR_REFEREE_COMMAND
                        || inMessage.getEntityState() > CoachState.WATCH_TRIAL) {
                    throw new MessageException("Invalid Coach State", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_REMOVE_CONTESTANT:
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_ACTIVE_CONTESTANT:
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                }
                break;
            case MessageType.REQ_LOG_SET_CONTESTANT_STRENGTH:
                if (inMessage.getTeam() < 0 || inMessage.getTeam() > SimulParse.COACH) {
                    throw new MessageException("Invalid Team", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid Contestant ID", inMessage);
                }
                break;
            case MessageType.REQ_SET_ROPE_POSITION:
                // No parameters to validate
                break;

            case MessageType.REQ_NEW_GAME_STARTED:
                // No parameters to validate
                break;

            case MessageType.REQ_SET_NEW_TRIAL:
                // No parameters to validate
                break;
            case MessageType.REQ_SET_END_OF_GAME:
                // No parameters to validate
                break;
            case MessageType.REQ_SHOW_GAME_RESULT:
                // No parameters to validate
                break;

            default:
                break;
        }

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_LOG_SET_REFEREE_STATE:
                repo.setRefereeState(inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_REFEREE_STATE);
                break;
            case MessageType.REQ_LOG_SET_CONTESTANT_STATE:
                repo.setContestantState(inMessage.getTeam(), inMessage.getID(), inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_CONTESTANT_STATE);
                break;
            case MessageType.REQ_LOG_SET_COACH_STATE:
                repo.setCoachState(inMessage.getTeam(), inMessage.getEntityState());
                outMessage = new Message(MessageType.REP_LOG_SET_COACH_STATE);
                break;

            case MessageType.REQ_LOG_SET_REMOVE_CONTESTANT:
                repo.setRemoveContestant(inMessage.getTeam(), inMessage.getID());
                outMessage = new Message(MessageType.REP_LOG_SET_REMOVE_CONTESTANT);
                break;
            case MessageType.REQ_LOG_SET_ACTIVE_CONTESTANT:
                repo.setActiveContestant(inMessage.getTeam(), inMessage.getID());
                outMessage = new Message(MessageType.REP_LOG_SET_ACTIVE_CONTESTANT);
                break;

            case MessageType.REQ_LOG_SET_CONTESTANT_STRENGTH:
                repo.setContestantStrength(inMessage.getTeam(), inMessage.getID(), inMessage.getStrength());
                outMessage = new Message(MessageType.REP_LOG_SET_CONTESTANT_STRENGTH);
                break;
            case MessageType.REQ_SET_ROPE_POSITION:
                repo.setRopePosition(inMessage.getRopePosition());
                outMessage = new Message(MessageType.REP_SET_ROPE_POSITION);
                break;

            case MessageType.REQ_NEW_GAME_STARTED:
                repo.newGameStarted();
                outMessage = new Message(MessageType.REP_NEW_GAME_STARTED);
                break;
            case MessageType.REQ_SET_NEW_TRIAL:
                repo.setNewTrial();
                outMessage = new Message(MessageType.REP_SET_NEW_TRIAL);
                break;
            case MessageType.REQ_SET_END_OF_GAME:
                repo.setEndOfGame();
                outMessage = new Message(MessageType.REP_SET_END_OF_GAME);
                break;
            case MessageType.REQ_SHOW_GAME_RESULT:
                repo.showGameResult(inMessage.getRopePosition());
                outMessage = new Message(MessageType.REP_SHOW_GAME_RESULT);
                break;

            default:
                break;
        }

        System.out.println("\nGRI processAndReply() -> Reply: " + outMessage.toString());
        return (outMessage);
    }

}
