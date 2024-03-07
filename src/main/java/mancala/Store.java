package mancala;

import java.io.Serializable;

/**
 * The Store class implements the Serializable and Countable interfaces.
 */
public class Store implements Serializable, Countable{
    private static final long serialVersionUID = 1L;
    private int stoneCount;
    private Player owner;

    // is a constructor for the `Store` class. It initializes the `stoneCount`
    // variable to 0 when a new `Store` object is created.
    public Store(){
        stoneCount = 0;
    }

    /**
     * The addStones function increases the stoneCount variable by the specified amount.
     * 
     * @param amount The amount parameter represents the number of stones to be added to the
     * stoneCount.
     */
    @Override
    public void addStones(final int amount){
        stoneCount += amount;
    }

    /**
     * The function "getOwner" returns the owner of a player.
     * 
     * @return The method is returning an object of type Player.
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * The function sets the owner of an object to a specified player.
     * 
     * @param player The "player" parameter is an instance of the Player class.
     */
    public void setOwner(final Player player){
        owner = player;
    }

    /**
     * The function returns the value of the stoneCount variable.
     * 
     * @return The method is returning the value of the variable "stoneCount".
     */
    @Override
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * The addStone() function increments the stoneCount variable by 1.
     */
    @Override
    public void addStone() {
        stoneCount++;
    }

    /**
     * The function removes all stones and returns the count of stones that were removed.
     * 
     * @return The method is returning the value of the variable "count", which represents the number
     * of stones that were removed.
     */
    @Override
    public int removeStones() {
        final int count = stoneCount;
        stoneCount = 0;
        return count;
    }
}