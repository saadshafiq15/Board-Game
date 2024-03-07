package mancala;

import java.io.Serializable;

/**
 * The Pit class implements the Serializable and Countable interfaces.
 */
public class Pit implements Serializable, Countable{
    private static final long serialVersionUID = 1L;
    private int stoneCount;

    // is a constructor for the `Pit` class. It initializes the `stoneCount` variable
    // to 0 when a new `Pit` object is created.
    public Pit(){
        stoneCount = 0;
    }

    /**
     * The addStone() function increments the stoneCount variable by 1.
     */
    @Override
    public void addStone(){
        stoneCount++;
    }

    /**
     * The function returns the value of the stoneCount variable.
     * 
     * @return The method is returning the value of the variable "stoneCount".
     */
    @Override
    public int getStoneCount(){
        return stoneCount;
    }

    /**
     * The function removes all stones and returns the total number of stones that were removed.
     * 
     * @return The method is returning the total number of stones that were removed.
     */
    @Override
    public int removeStones(){
        final int totalStones = stoneCount;
        stoneCount = 0;
        return totalStones;
    }

    /**
     * The addStones function increases the stoneCount variable by the specified number.
     * 
     * @param numToAdd The parameter `numToAdd` is an integer representing the number of stones to add
     * to the `stoneCount`.
     */
    @Override
    public void addStones(final int numToAdd) {
        stoneCount = stoneCount + numToAdd;
    }
}