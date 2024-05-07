package commonInfra;

import java.io.Serializable;

import serverSide.main.SimulParse;

/**
 *   This data type defines the message sent between the server and the clients.
 *   The message is composed by a type and a set of parameters.
 */
public class Message implements Serializable {
    /**
     * Serialization key.
     */

    private static final long serialVersionUID = 2024L;

    /**
     * Type of the message.
     */

    private int msgType = -1;
    
    /**
     * State of the entity.
     */
    private int state = -1;

    /**
     * Team of the entity.
     */
    private int team = -1;

    /**
     * ID of the entity.
     */
    private int id = -1;

    /**
     * Continue game flag.
     */
    private boolean continueGame = false;

    /**
     * Rope position.
     */
    private int ropePostion;

    /**
     * Scores of the teams.
     */
    private int[] scores = new int[2];

    /**
     * Contestants information (id,strength)
     */
    private View[] aboutContestants = new View[SimulParse.CONTESTANT_PER_TEAM];

    /**
     * Selected contestants for Playground.
     */
    private int[] selected = new int[SimulParse.CONTESTANT_IN_PLAYGROUND_PER_TEAM];

    /**
     * Strength of the entity.
     */
    private int strength = -1;

    /**
     * Orders of the entity.
     */
    private int orders = -1;

    /**
     * Strength of the contestants.
     */
    int[][] contestantStrength = new int[2][SimulParse.CONTESTANT_PER_TEAM];

    /**
     * Message instantiation (form 1).
     * 
     * @param msgType type of the message
     */
    public Message(int msgType) {
        this.msgType = msgType;
    }

    /**
     * Message instantiation (form 2).
     * 
     * @param msgType type of the message
     * @param value   value 
     */
    public Message(int msgType, int value) {
        this.msgType = msgType;
        if (msgType == MessageType.REQ_ANNOUNCE_NEW_GAME || msgType == MessageType.REP_ANNOUNCE_NEW_GAME
                || msgType == MessageType.REQ_CALL_TRIAL || msgType == MessageType.REP_CALL_TRIAL
                || msgType == MessageType.REQ_LOG_SET_REFEREE_STATE || msgType == MessageType.REP_LOG_SET_REFEREE_STATE
                || msgType == MessageType.REP_LOG_SET_CONTESTANT_STATE
                || msgType == MessageType.REP_LOG_SET_REMOVE_CONTESTANT || msgType == MessageType.REQ_START_TRIAL
                || msgType == MessageType.REP_START_TRIAL || msgType == MessageType.REQ_DECLARE_GAME_WINNER
                || msgType == MessageType.REP_DECLARE_MATCH_WINNER) {
            this.state = value;
        } else if (msgType == MessageType.REQ_REVIEW_NOTES || msgType == MessageType.REQ_WAIT_FOR_CALL_TRIAL
                || msgType == MessageType.REP_CALL_CONTESTANTS || msgType == MessageType.REQ_FOLLOW_COACH_ADVICE
                || msgType == MessageType.REP_FOLLOW_COACH_ADVICE) {
            this.team = value;
        } else if (msgType == MessageType.REQ_SET_ROPE_POSITION || msgType == MessageType.REQ_SHOW_GAME_RESULT) {
            this.ropePostion = value;
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
     * @param team  team
     * @param aboutContestants information about contestants
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
     * @param value1  set team/state
     * @param value2  set selected/score
     */
    public Message(int msgType, int value1, int[] value2) {
        this.msgType = msgType;
        if (msgType == MessageType.REQ_DECLARE_MATCH_WINNER) {
            this.state = value1;
            this.scores = value2;
        } else {
            this.team = value1;
            this.selected = value2;
        }
    }

    /**
     * Message instantiation (form 11).
     * 
     * @param msgType type of the message
     * @param value1  set team
     * @param value2  set id/state
     */
    public Message(int msgType, int value1, int value2) {
        this.msgType = msgType;

        if (msgType == MessageType.REP_DECLARE_GAME_WINNER) {
            this.state = value1;
        } else {
            this.team = value1;
        }
        if (msgType == MessageType.REQ_LOG_SET_COACH_STATE || msgType == MessageType.REQ_WAIT_FOR_FOLLOW_COACH_ADVICE
                || msgType == MessageType.REP_WAIT_FOR_FOLLOW_COACH_ADVICE
                || msgType == MessageType.REQ_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH
                || msgType == MessageType.REP_WAIT_FOR_ASSERT_TRIAL_DECISION_COACH) {
            this.state = value2;
        } else if (msgType == MessageType.REQ_LOG_SET_REMOVE_CONTESTANT
                || msgType == MessageType.REQ_LOG_SET_ACTIVE_CONTESTANT || msgType == MessageType.REQ_GET_READY
                || msgType == MessageType.REP_GET_READY) {
            this.id = value2;
        } else if (msgType == MessageType.REP_DECLARE_GAME_WINNER) {
            this.ropePostion = value2;
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
                || msgType == MessageType.REP_WAIT_FOR_CALL_CONTESTANTS
                || msgType == MessageType.REQ_WAIT_FOR_START_TRIAL || msgType == MessageType.REP_WAIT_FOR_START_TRIAL) {
            this.id = value1;
        }

        if (msgType == MessageType.REQ_LOG_SET_CONTESTANT_STATE || msgType == MessageType.REP_SEAT_DOWN
                || msgType == MessageType.REP_WAIT_FOR_CALL_TRIAL || msgType == MessageType.REQ_WAIT_FOR_START_TRIAL
                || msgType == MessageType.REP_WAIT_FOR_START_TRIAL) {
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
     * @param team  set team
     * @param id  set id
     * @param strength  set strength
     * @param state  set state
     */

    public Message(int msgType, int team, int id, int strength, int state) {
        this.msgType = msgType;
        this.team = team;
        this.id = id;
        this.strength = strength;
        this.state = state;
    }

    /**
     * Message instantion (form 14).
     * @param msgType
     * @param contestantStrength
     */

    public Message(int msgType, int[][] contestantStrength){
        this.msgType = msgType;
        this.contestantStrength = contestantStrength;
    }


    /**
     * Get message type.
     * @return message type
     */

    public int getMsgType() {
        return msgType;
    }

    /**
     * Get entity state.
     * @return entity state
     */

    public int getEntityState() {
        return state;
    }

    /**
     * Set entity state.
     * @param state entity state
     * @return entity state
     */

    public int setEntityState(int state) {
        return this.state = state;
    }

    /**
     * Get about contestants
     * @return about contestants
     */

    public View[] getAboutContestants() {
        return aboutContestants;
    }

    /**
     * Get team.
     * @return team
     */
    public int getTeam() {
        return team;
    }

    /**
     * Get ID.
     * @return ID
     */

    public int getID() {
        return id;
    }

    /**
     * Get strength.
     * @return strength
     */

    public int getStrength() {
        return strength;
    }

    /**
     * Get orders.
     * @return orders
     */

    public int getOrders() {
        return orders;
    }

    /**
     * Get selected contestants.
     * @return selected contestants
     */

    public int[] getSelected() {
        return selected;
    }

    /**
     * Get scores.
     * @return scores
     */

    public int[] getScores() {
        return scores;
    }

    /**
     * Get rope position.
     * @return rope position
     */

    public int getRopePosition() {
        return ropePostion;
    }

    /**
     * Get continue game.
     * @return continue game
     */

    public boolean getContinueGame() {
        return continueGame;
    }

    /**
     * Get contestant strengths.
     * @return contestant strengths
     */
    public int[][] getContestantStrengths() {
        return contestantStrength;
    }

    /**
     * To String method of the message messages
     * @return string with the message
     */

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
