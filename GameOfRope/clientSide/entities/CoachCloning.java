package clientSide.entities;

/**
 * Coach Clone
 * It specifies the Coach methods for the clone
 */
public interface CoachCloning {
    /**
     * Set the state of the coach
     * 
     * @param state the state of the coach
     */

    public void setCoachState(int state);

    /**
     * Get the state of the coach
     * 
     * @return the state of the coach
     */

    public int getCoachState();

    /**
     * Set the team of the coach
     * 
     * @param team the team of the coach
     */

    public void setCoachTeam(int team);

    /**
     * Get the team of the coach
     * 
     * @return the team of the coach
     */

    public int getCoachTeam();
}