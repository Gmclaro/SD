package serverSide.sharedRegions;

import clientSide.entities.*;
import commonInfra.*;
import serverSide.entities.*;
import serverSide.main.SimulParse;

public class PlaygroundInterface {

    private final Playground playground;

    public PlaygroundInterface(Playground playground) {
        this.playground = playground;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        /* Validate messages */

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_FOLLOW_COACH_ADVICE:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                }
                break;

            case MessageType.REQ_WAIT_FOR_FOLLOW_COACH_ADVICE:
                if ((inMessage.getTeam() < 0) || (inMessage.getTeam() > SimulParse.COACH)) {
                    throw new MessageException("Invalid number of team !", inMessage);
                } else if (inMessage.getEntityState() < CoachState.WAIT_FOR_REFEREE_COMMAND
                        || inMessage.getEntityState() > CoachState.WATCH_TRIAL) {
                    throw new MessageException("Invalid number of state !", inMessage);
                }
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        /* Process Messages */
        int team, id;

        switch (inMessage.getMsgType()) {
            case MessageType.REQ_FOLLOW_COACH_ADVICE:
                team = inMessage.getTeam();

                playground.followCoachAdvice(team);

                outMessage = new Message(MessageType.REP_FOLLOW_COACH_ADVICE, team);
                break;

            case MessageType.REQ_WAIT_FOR_FOLLOW_COACH_ADVICE:
                team = inMessage.getTeam();

                ((PlaygroundClientProxy) Thread.currentThread()).setCoachState(inMessage.getEntityState());

                playground.waitForFollowCoachAdvice(team);

                outMessage = new Message(MessageType.REP_WAIT_FOR_FOLLOW_COACH_ADVICE, team,
                        ((PlaygroundClientProxy) Thread.currentThread()).getCoachState());
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        System.out.println("\nPI processAndReply: " + outMessage.toString());
        return (outMessage);
    }
}
