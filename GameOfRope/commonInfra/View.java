package commonInfra;

/**
 * This class represents the view of caracteristics of a player and behaves as
 * an element of a map.
 */
public class View {
    /**
     * The key of the view.
     */
    private int key;

    /**
     * The value of the view.
     */
    private int value;

    /**
     * Constructor to create the view.
     * 
     * @param key   The key of the view.
     * @param value The value of the view.
     */
    public View(int key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key of the view.
     * 
     * @return The key of the view.
     */
    public int getKey() {
        return key;
    }

    /**
     * Get the value of the view.
     * 
     * @return The value of the view.
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the key of the view.
     * 
     * @param key The key of the view.
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Set the value of the view.
     * 
     * @param value The value of the view.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
