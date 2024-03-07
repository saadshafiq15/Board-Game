package mancala;

import java.io.Serializable;

/**
 * The UserProfile class is serializable.
 */
public class UserProfile implements Serializable{
    private static final long serialVersionUID = 1L;
    private Player player;
    private String userName;
    private int numKalah = 0;
    private int numAyo = 0;
    private int numKalahWon = 0;
    private int numAyoWon = 0;

    protected void setUserName(final String name){
        userName = name;
    }

    protected void setPlayer(final Player setPlayer){
        player = setPlayer;
    }

    /**
     * The function returns the player object.
     * 
     * @return The method is returning an object of type Player.
     */
    public Player getPlayer() {
        return player;
    }

    private String getUserName(){
        return userName;
    }

    private int getNumKalah(){
        return numKalah;
    }

    private int getNumAyo(){
        return numAyo;
    }

    private int getNumKalahWon(){
        return numKalahWon;
    }

    private int getNumAyoWon(){
        return numAyoWon;
    }

    /**
     * The function increments the value of the variable numKalahWon by 1.
     */
    public void addNumKalahWon(){
        numKalahWon++;
    }

    /**
     * The function increments the value of the variable numAyoWon by 1.
     */
    public void addNumAyoWon(){
        numAyoWon++;
    }

    /**
     * The function "addNumKalah" increments the value of the variable "numKalah" by 1.
     */
    public void addNumKalah(){
        numKalah++;
    }

    /**
     * The function "addNumAyo" increments the value of the variable "numAyo" by 1.
     */
    public void addNumAyo(){
        numAyo++;
    }

    /**
     * The function sets the values of a user's profile based on a loaded profile, if the loaded
     * profile is not null.
     * 
     * @param loadedProfile The loadedProfile parameter is an instance of the UserProfile class.
     */
    public void setValuesFromProfile(final UserProfile loadedProfile) {
        if (loadedProfile != null) {
            this.userName = loadedProfile.getUserName();
            this.numKalah = loadedProfile.getNumKalah();
            this.numAyo = loadedProfile.getNumAyo();
            this.numKalahWon = loadedProfile.getNumKalahWon();
            this.numAyoWon = loadedProfile.getNumAyoWon();
        }
    }

    /**
     * The toString() function returns a string representation of the object, including the values of
     * its instance variables.
     * 
     * @return is returning a string representation of the object. The returned
     * string includes the values of the instance variables userName, numKalah, numAyo, numKalahWon,
     * and numAyoWon.
     */
    @Override
    public String toString() {
        return 
            "\n" + userName + ":" +
            "\n  numKalah=" + numKalah +
            "\n  numAyo=" + numAyo +
            "\n  numKalahWon=" + numKalahWon +
            "\n  numAyoWon=" + numAyoWon +
            "\n";
    } 
}
