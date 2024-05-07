package serverSide.sharedRegions;

import clientSide.entities.CoachState;
import clientSide.entities.ContestantState;
import clientSide.entities.RefereeState;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;
import serverSide.main.SimulParse;


/**
 * Interface to the General Repository
 *  It is responsible to validate and process the incoming message, execute the corresponding method on the
 *   General Repository and generate the outgoing message.
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralRepositoryInterface {


    /**
     *  Reference to the General Repository
     */
    private final GeneralRepository repo;

    /**
     *   Instantiation of an interface to the General Repository.
     *
     *     @param repo Reference to the General Repository
     */

    public GeneralRepositoryInterface(GeneralRepository repo) {
        this.repo = repo;
    }

    /**
     *   Processing of the incoming messages
     *   Validation, execution of the corresponding method and generation of the outgoing message.
     *
     * 	   @param inMessage service request
     * 	   @return service reply
     * 	   @throws MessageException if incoming message was not valid
     */

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
            case MessageType.REQ_SET_MATCH_WINNER:
                // No parameters to validate
                break;
            case MessageType.REQ_INIT_SIMUL:
                // No parameters to validate
                break;
            case MessageType.REQ_GENERAL_REPOSITORY_SHUTDOWN:
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
            case MessageType.REQ_SET_MATCH_WINNER:
                repo.setMatchWinner(inMessage.getScores());
                outMessage = new Message(MessageType.REP_SET_MATCH_WINNER);
                break;
            case MessageType.REQ_INIT_SIMUL:
                repo.initSimul(inMessage.getContestantStrengths());
                outMessage = new Message(MessageType.REP_INIT_SIMUL);
                break;
            case MessageType.REQ_GENERAL_REPOSITORY_SHUTDOWN:
                repo.shutdown();
                outMessage = new Message(MessageType.REP_GENERAL_REPOSITORY_SHUTDOWN);
                break;

            default:
                break;
        }

        System.out.println("\nGRI processAndReply() -> Reply: " + outMessage.toString());
        return (outMessage);
    }

}
