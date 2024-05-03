package serverSide.sharedRegions;

import clientSide.entities.*;
import commonInfra.*;
import serverSide.entities.*;
import serverSide.main.SimulParse;

public class ContestantBenchInterface {

    private final ContestantBench contestantBench;

    public ContestantBenchInterface(ContestantBench contestantBench) {
        this.contestantBench = contestantBench;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        System.out.println("inMessage:\n" + inMessage.toString());

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
            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        /* Process Messages */
        int team, id,orders;
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
                ((ContestantBenchClientProxy) Thread.currentThread()).setId(id);
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

                outMessage = new Message(MessageType.REP_WAIT_FOR_CALL_TRIAL, team, orders,((ContestantBenchClientProxy) Thread.currentThread()).getCoachState());
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        System.out.println("\nCBI processAndReply: " + outMessage.toString());
        return (outMessage);
    }
}
