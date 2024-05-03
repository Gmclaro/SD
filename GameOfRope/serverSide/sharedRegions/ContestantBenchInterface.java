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
            case MessageType.REQ_REVIEW_NOTES:
                if ((inMessage.getTeam() < 1) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                } else if (((ContestantBenchClientProxy) Thread.currentThread()).getCoachTeam() != inMessage
                        .getTeam()) {
                    throw new MessageException("Invalid team!", inMessage);
                }
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        /* Process Messages */

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_CALL_TRIAL:
                ((ContestantBenchClientProxy) Thread.currentThread()).setRefereeState(inMessage.getEntityState());
                contestantBench.callTrial();
                outMessage = new Message(MessageType.REP_CALL_TRIAL, ((ContestantBenchClientProxy) Thread.currentThread()).getRefereeState());
                System.out.println("REP_CALL_TRIAL: " + outMessage.getEntityState());
                break;
            case MessageType.REQ_REVIEW_NOTES:
                int team = ((ContestantBenchClientProxy) Thread.currentThread()).getCoachTeam();

                contestantBench.reviewNotes(team);
                outMessage = new Message(MessageType.REP_REVIEW_NOTES, team);

                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        return (outMessage);
    }
}
