package clientSide.entities;

/**
 * Contestant Clone
 * It specifies the contestant cloning.
 */

public interface ContestantCloning {

    /**
     * Set the state of the contestant
     * @param state the state of the contestant
     */

    public void setContestantState(int state);

    /**
     * Get the state of the contestant
     * @return the state of the contestant
     */

    public int getContestantState();

    /**
     * Set the team of the contestant
     * @param team the team of the contestant
     */

    public void setContestantTeam(int team);


    /**
     * Get the team of the contestant
     * @return the team of the contestant
     */

    public int getContestantTeam();


    /**
     * Set the id of the contestant
     * @param id the id of the contestant
     */

    public void setID(int id);

    /**
     * Get the id of the contestant
     * @return the id of the contestant
     */

    public int getID();

    /**
     * Get the strength of the contestant
     * @return the strength of the contestant
     */

    public int getStrength();

    /**
     * Set the strength of the contestant
     * @param strength the strength of the contestant
     */

    public void setStrength(int strength);



    
}
