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

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        System.out.println("\nPI processAndReply: " + outMessage.toString());
        return (outMessage);
    }
}
