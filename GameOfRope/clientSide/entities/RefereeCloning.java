package clientSide.entities;


/**
 *  Referee cloning
 *  It specifies the Referee cloning interface.
 */
public interface RefereeCloning {
    
    /**
     * Set the state of the referee
     * 
     * @param state the state of the referee
     */
    public void setEntityState(int state);

    /**
     * Get the state of the referee
     * 
     * @return the state of the referee
     */
    public int getEntityState();
}
