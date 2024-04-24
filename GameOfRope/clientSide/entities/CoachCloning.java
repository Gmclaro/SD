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

    public void setEntityState(int state);

    /**
     * Get the state of the coach
     * 
     * @return the state of the coach
     */

    public int getEntityState();

    /**
     * Set the team of the coach
     * 
     * @param team the team of the coach
     */

    public void setTeam(int team);

    /**
     * Get the team of the coach
     * 
     * @return the team of the coach
     */

    public int getTeam();
}