package commonInfra;

import java.io.Serializable;

import serverSide.main.SimulParse;

public class Message implements Serializable {
    /**
     * Serialization key.
     */

    private static final long serialVersionUID = 2024L;

    /**
     * Type of the message.
     */

    private int msgType = -1;

    private int state = -1;

    private int team = -1;

    private int id = -1;

    private boolean continueGame = false;

    private int ropePostion;

    private int[] scores = new int[2];

    private View[] aboutContestants = new View[SimulParse.CONTESTANT_PER_TEAM];

    private int[] selected = new int[SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM];

    private int strength = -1;

    private int orders = -1;

    /**
     * Message instantiation (form 1).
     * 
     * @param type type of the message
     */
    public Message(int msgType) {
        this.msgType = msgType;
    }

    /**
     * Message instantiation (form 2).
     * 
     * @param msgType type of the message
     * @param value   set/get entity state
     * 
     *                ---
     *                Message instantiation (form 3).
     * 
     * @param msgType type of the message
     * @param value   set ropePosition
     * 
     *                ---
     *                Message instantiation (form 4).
     * 
     * @param msgType type of the message
     * @param value   get orders
     * 
     *                ---
     *                Message instantiation (form 5).
     * 
     * @param msgType type of the message
     * @param value   get team
     * 
     *                ---
     *                Message instantiation (form 6).
     * 
     * @param msgType type of the message
     * @param value   get strength
     */
    public Message(int msgType, int value) {
        this.msgType = msgType;

        // TODO: value can be refereeState/ropePosition/orders/team/strength
        if (msgType == MessageType.REQ_ANNOUNCE_NEW_GAME || msgType == MessageType.REP_ANNOUNCE_NEW_GAME || msgType == MessageType.REQ_LOG_SET_REFEREE_STATE) {
            this.state = value;
        }
        else {
            System.out.println ("Message type = " + msgType + ": non-implemented instantiation!");
            System.exit(1);
        }
    }

    /**
     * Message instantiation (form 7).
     * 
     * @param msgType type of the message
     * @param value   set continueGame
     */
    public Message(int msgType, boolean value) {
        this.msgType = msgType;
        this.continueGame = value;
    }

    /**
     * Message instantiation (form 8).
     * 
     * @param msgType type of the message
     * @param value   set score
     */
    public Message(int msgType, int[] value) {
        this.msgType = msgType;
        this.scores = value;
    }

    /**
     * Message instantiation (form 9).
     * 
     * @param msgType type of the message
     * @param value   get aboutContestants
     */
    public Message(int msgType, View[] value) {
        this.msgType = msgType;
        this.aboutContestants = value;
    }

    /**
     * Message instantiation (form 10).
     * 
     * @param msgType type of the message
     * @param value1  set team
     * @param value2  set selected
     */
    public Message(int msgType, int team, int[] selected) {
        this.msgType = msgType;
        this.team = team;
        this.selected = selected;
    }

    /**
     * Message instantiation (form 11).
     * 
     * @param msgType type of the message
     * @param value1  set team
     * @param value2  set id
     */
    public Message(int msgType, int team, int id) {
        this.msgType = msgType;
        this.team = team;
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getEntityState() {
        return state;
    }

    public int setEntityState(int state) {
        return this.state = state;
    }

    public View[] getAboutContestants(){
        return aboutContestants;
    }

    public int getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msgType=" + msgType +
                ", state=" + state +
                ", team=" + team +
                ", id=" + id +
                ", continueGame=" + continueGame +
                ", ropePostion=" + ropePostion +
                ", scores=" + scores +
                ", aboutContestants=" + aboutContestants +
                ", selected=" + selected +
                ", strength=" + strength +
                ", orders=" + orders +
                '}';
    }

}
