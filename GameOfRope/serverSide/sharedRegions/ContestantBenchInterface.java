package serverSide.sharedRegions;

import clientSide.entities.ContestantState;
import clientSide.entities.RefereeState;
import commonInfra.Message;
import commonInfra.MessageException;
import commonInfra.MessageType;
import commonInfra.View;
import serverSide.entities.ContestantBenchClientProxy;
import serverSide.main.SimulParse;


/**
 *   Interface to the Contestant Bench 
 *   It is responsible to validate and process the incoming message, execute the corresponding method on the
 *   Contestant Bench e and generate the outgoing message.
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */
public class ContestantBenchInterface {

    /**
     *  Reference to the Contestant Bench
     */

    private final ContestantBench contestantBench;

    /**
     *   Instantiation of an interface to the Contestant Bench.
     *
     *     @param contestantBench Reference to the Contestant Bench
     */
    public ContestantBenchInterface(ContestantBench contestantBench) {
        this.contestantBench = contestantBench;
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

        /* Validate messages */

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_CALL_TRIAL:
                if (inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH
                        || inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH) {
                    throw new MessageException("Invalid Referee state!", inMessage);
                }
                break;
            case MessageType.REQ_SEAT_DOWN:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid number of id !", inMessage);
                } else if (inMessage.getEntityState() < ContestantState.SEAT_AT_THE_BENCH
                        || inMessage.getEntityState() > ContestantState.DO_YOUR_BEST) {
                    throw new MessageException("Invalid number of state !", inMessage);
                }
                break;
            case MessageType.REQ_REVIEW_NOTES:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                }
                break;
            case MessageType.REQ_WAIT_FOR_CALL_TRIAL:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                }
                break;
            case MessageType.REQ_CALL_CONTESTANTS:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                }
                break;
            case MessageType.REQ_WAIT_FOR_CALL_CONTESTANTS:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                } else if (inMessage.getID() < 0 || inMessage.getID() > SimulParse.CONTESTANT_PER_TEAM) {
                    throw new MessageException("Invalid number of id !", inMessage);
                }
                break;
            case MessageType.REQ_WAIT_FOR_SEAT_AT_BENCH:
                // No validation required
                break;
            case MessageType.REQ_DECLARE_MATCH_WINNER:
                if (inMessage.getEntityState() < RefereeState.START_OF_THE_MATCH
                        || inMessage.getEntityState() > RefereeState.END_OF_THE_MATCH) {
                    throw new MessageException("Invalid Referee state!", inMessage);
                }
                break;

            case MessageType.REQ_CONTESTANT_BENCH_SHUTDOWN:
                // No validation required
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        /* Process Messages */
        int team, id, orders;
        View[] aboutContestants;

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_CALL_TRIAL:
                ((ContestantBenchClientProxy) Thread.currentThread()).setRefereeState(inMessage.getEntityState());
                contestantBench.callTrial();
                outMessage = new Message(MessageType.REP_CALL_TRIAL,
                        ((ContestantBenchClientProxy) Thread.currentThread()).getRefereeState());
                break;

            case MessageType.REQ_SEAT_DOWN:
                team = inMessage.getTeam();
                id = inMessage.getID();
                ((ContestantBenchClientProxy) Thread.currentThread()).setContestantTeam(team);
                ((ContestantBenchClientProxy) Thread.currentThread()).setID(id);
                ((ContestantBenchClientProxy) Thread.currentThread()).setStrength(inMessage.getStrength());
                ((ContestantBenchClientProxy) Thread.currentThread()).setContestantState(inMessage.getEntityState());

                contestantBench.seatDown(team, id);

                outMessage = new Message(MessageType.REP_SEAT_DOWN, team, id,
                        ((ContestantBenchClientProxy) Thread.currentThread()).getContestantState());

                break;

            case MessageType.REQ_REVIEW_NOTES:
                team = inMessage.getTeam();
                ((ContestantBenchClientProxy) Thread.currentThread()).setCoachTeam(team);

                aboutContestants = contestantBench.reviewNotes(team);

                outMessage = new Message(MessageType.REP_REVIEW_NOTES, team, aboutContestants);

                break;

            case MessageType.REQ_WAIT_FOR_CALL_TRIAL:
                team = inMessage.getTeam();
                ((ContestantBenchClientProxy) Thread.currentThread()).setCoachTeam(team);

                orders = contestantBench.waitForCallTrial(team);

                outMessage = new Message(MessageType.REP_WAIT_FOR_CALL_TRIAL, team, orders,
                        ((ContestantBenchClientProxy) Thread.currentThread()).getCoachState());
                break;

            case MessageType.REQ_CALL_CONTESTANTS:
                team = inMessage.getTeam();

                ((ContestantBenchClientProxy) Thread.currentThread()).setCoachTeam(team);

                contestantBench.callContestants(team, inMessage.getSelected());

                outMessage = new Message(MessageType.REP_CALL_CONTESTANTS, team);
                break;

            case MessageType.REQ_WAIT_FOR_CALL_CONTESTANTS:
                team = inMessage.getTeam();
                id = inMessage.getID();
                ((ContestantBenchClientProxy) Thread.currentThread()).setContestantTeam(team);
                ((ContestantBenchClientProxy) Thread.currentThread()).setID(id);
                ((ContestantBenchClientProxy) Thread.currentThread()).setStrength(inMessage.getStrength());

                orders = contestantBench.waitForCallContestants(team, id);

                outMessage = new Message(MessageType.REP_WAIT_FOR_CALL_CONTESTANTS, team, id, orders);
                break;
            case MessageType.REQ_WAIT_FOR_SEAT_AT_BENCH:
                contestantBench.waitForSeatAtBench();

                outMessage = new Message(MessageType.REP_WAIT_FOR_SEAT_AT_BENCH);
                break;
            case MessageType.REQ_DECLARE_MATCH_WINNER:
                ((ContestantBenchClientProxy) Thread.currentThread()).setRefereeState(inMessage.getEntityState());
                contestantBench.declareMatchWinner(inMessage.getScores());

                outMessage = new Message(MessageType.REP_DECLARE_MATCH_WINNER,
                        ((ContestantBenchClientProxy) Thread.currentThread()).getRefereeState());
                break;
            case MessageType.REQ_CONTESTANT_BENCH_SHUTDOWN:
                contestantBench.shutdown();
                outMessage = new Message(MessageType.REP_CONTESTANT_BENCH_SHUTDOWN);
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        System.out.println("\nCBI processAndReply: " + outMessage.toString());
        return (outMessage);
    }
}
