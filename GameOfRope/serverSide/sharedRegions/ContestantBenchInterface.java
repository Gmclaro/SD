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


    public Message processAndReply(Message inMessage) throws MessageException{
        Message outMessage = null;

        System.out.println("inMessage:\n" + inMessage.toString());

        /* Validate messages */

        switch(inMessage.getMsgType()){
            case MessageType.REQ_REVIEW_NOTES: 
                if((inMessage.getTeam() < 1) || (inMessage.getTeam() > SimulParse.COACH)){
                    throw new MessageException("Invalid number of team !", inMessage);
                }
                else if(((ContestantBenchClientProxy) Thread.currentThread()).getTeam() != inMessage.getTeam()){
                    throw new MessageException("Invalid team!", inMessage);
                }
                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);

        }

        /* Process Messages */

        switch(inMessage.getMsgType()){
            case MessageType.REQ_REVIEW_NOTES:
                System.out.println("REQ_REVIEW_NOTES:\n" + inMessage.toString());
                int team = ((ContestantBenchClientProxy) Thread.currentThread()).getTeam();

                contestantBench.reviewNotes(team);
                outMessage = new Message(MessageType.REP_REVIEW_NOTES, team);

                break;
            default:
                throw new MessageException("Invalid message type!", inMessage);
        }


        return (outMessage);
    }
}
