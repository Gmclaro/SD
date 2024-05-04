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
        if (msgType == MessageType.REQ_ANNOUNCE_NEW_GAME || msgType == MessageType.REP_ANNOUNCE_NEW_GAME
                || msgType == MessageType.REQ_CALL_TRIAL || msgType == MessageType.REP_CALL_TRIAL
                || msgType == MessageType.REQ_LOG_SET_REFEREE_STATE || msgType == MessageType.REP_LOG_SET_REFEREE_STATE
                || msgType == MessageType.REP_LOG_SET_CONTESTANT_STATE
                || msgType == MessageType.REP_LOG_SET_REMOVE_CONTESTANT|| msgType == MessageType.REQ_START_TRIAL|| msgType == MessageType.REP_START_TRIAL) {
            this.state = value;
        } else if (msgType == MessageType.REQ_REVIEW_NOTES || msgType == MessageType.REQ_WAIT_FOR_CALL_TRIAL
                || msgType == MessageType.REP_CALL_CONTESTANTS || msgType == MessageType.REQ_FOLLOW_COACH_ADVICE
                || msgType == MessageType.REP_FOLLOW_COACH_ADVICE) {
            this.team = value;

        } else {
            System.out.println("Message type = " + msgType + ": non-implemented instantiation!");
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
    public Message(int msgType, int team, View[] aboutContestants) {
        this.msgType = msgType;
        this.team = team;
        this.aboutContestants = aboutContestants;
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
     * @param value2  set id/state
     */
    public Message(int msgType, int team, int value) {
        this.msgType = msgType;
        this.team = team;
        if (msgType == MessageType.REQ_LOG_SET_COACH_STATE || msgType == MessageType.REQ_WAIT_FOR_FOLLOW_COACH_ADVICE
                || msgType == MessageType.REP_WAIT_FOR_FOLLOW_COACH_ADVICE) {
            this.state = value;
        } else if (msgType == MessageType.REQ_LOG_SET_REMOVE_CONTESTANT) {
            this.id = value;
        } else {
            System.out.println("ATENCAO" + msgType);
            System.out.println("Message type = " + msgType + ": non-implemented instantiation!");
            System.exit(1);
        }
    }

    /**
     * Message instantiation (form 12).
     * 
     * @param msgType type of the message
     * @param team    set team
     * @param value1  set id/orders
     * @param value2  set strength/state/orders
     */

    public Message(int msgType, int team, int value1, int value2) {
        this.msgType = msgType;
        this.team = team;
        if (msgType == MessageType.REP_WAIT_FOR_CALL_TRIAL) {
            this.orders = value1;
        } else if (msgType == MessageType.REQ_LOG_SET_CONTESTANT_STATE
                || msgType == MessageType.REQ_LOG_SET_CONTESTANT_STRENGTH || msgType == MessageType.REP_SEAT_DOWN
                || msgType == MessageType.REQ_WAIT_FOR_CALL_CONTESTANTS
                || msgType == MessageType.REP_WAIT_FOR_CALL_CONTESTANTS) {
            this.id = value1;
        }

        if (msgType == MessageType.REQ_LOG_SET_CONTESTANT_STATE || msgType == MessageType.REP_SEAT_DOWN
                || msgType == MessageType.REP_WAIT_FOR_CALL_TRIAL) {
            this.state = value2;
        } else if (msgType == MessageType.REQ_WAIT_FOR_CALL_CONTESTANTS
                || msgType == MessageType.REQ_LOG_SET_CONTESTANT_STRENGTH) {
            this.strength = value2;

        } else if (msgType == MessageType.REP_WAIT_FOR_CALL_CONTESTANTS) {
            this.orders = value2;

        } else {
            System.out.println("Message type = " + msgType + ": non-implemented instantiation!");
            System.exit(1);
        }
    }

    /**
     * Message instantiation (form 13).
     * 
     * @param msgType type of the message
     * @param value1  set team
     * @param value2  set id
     * @param value3  set strength
     * @param value4  set state
     */

    public Message(int msgType, int team, int id, int strength, int state) {
        this.msgType = msgType;
        this.team = team;
        this.id = id;
        this.strength = strength;
        this.state = state;
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

    public View[] getAboutContestants() {
        return aboutContestants;
    }

    public int getTeam() {
        return team;
    }

    public int getID() {
        return id;
    }

    public int getStrength() {
        return strength;
    }

    public int getOrders() {
        return orders;
    }

    public int[] getSelected() {
        return selected;
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
