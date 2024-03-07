package mancala;

import java.io.Serializable;

/**
 * The Player class is serializable.
 */
public class Player implements Serializable{
    private static final long serialVersionUID = 1L;
    private Store playerStore;
    final private String playerName;
    private UserProfile userProfile;
    
    // is a constructor
    // for the Player class.
    public Player(){
        userProfile = new UserProfile();
        playerName = "";
    }

    //is creating a new instance of the `Player`
    // class with a specified name.
    public Player(final String name){
        userProfile = new UserProfile();
        userProfile.setUserName(name);
        playerName = name;
    }

    /**
     * The function "getProfile" returns the user profile.
     * 
     * @return The method is returning a UserProfile object.
     */
    public UserProfile getProfile(){
        return userProfile;
    }

    /**
     * The function sets the userProfile variable to the given profile.
     * 
     * @param profile The "profile" parameter is of type UserProfile.
     */
    public void setProfile(final UserProfile profile){
        userProfile = profile;
    }

    /**
     * The getName() function returns the value of the playerName variable.
     * 
     * @return The method is returning the value of the variable "playerName".
     */
    public String getName(){
        return playerName;
    }

    /**
     * The function returns the stone count of the player's store, or 0 if the store is null.
     * 
     * @return The method is returning the count of stones in the player's store.
     */
    public int getStoreCount(){
        int count = 0;
        if (playerStore != null){
            count = playerStore.getStoneCount();
        }
        return count;
    }

    /**
     * The function sets the value of the playerStore variable to the given Store object.
     * 
     * @param store The "store" parameter is an object of the class "Store".
     */
    public void setStore(final Store store){
        playerStore = store;
    }

    /**
     * The toString() function returns a string representation of a player object, including the
     * player's name and whether they have a store or not.
     * 
     * @return returning a string representation of the player object. The
     * string includes the player's name and whether or not they have a store.
     */
    @Override
    public String toString() {
        final String storeInfo = (playerStore != null) ? " with a store" : " with no store";
        return "Player: " + playerName + storeInfo;
    }
}