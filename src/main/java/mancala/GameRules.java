package mancala;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable{
    private static final long serialVersionUID = 1L;
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    /* default */ protected final int middle = 5;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        gameBoard.setUpPits();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    public MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Returns the current player.
     * 
     * @return The method is returning the value of the variable "currentPlayer".
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    /* default */ boolean isSideEmpty(final int pitNum) {
        // This method can be implemented in the abstract class.
        final int pit = pitNum - 1;
        if (pit <= middle){
            for (int i = 1; i<=6; i++){
                if (getNumStones(i) > 0){
                    return false;
                }
            }
        } else if (pit > middle){
            for (int i = 7; i<=12; i++){
                if (getNumStones(i) > 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    /* default */ abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    /* default */ abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.
        final Store store1 = new Store();
        final Store store2 = new Store();

        store1.setOwner(one);
        store2.setOwner(two);

        one.setStore(store1);
        two.setStore(store2);

        gameBoard.setStore(store1, 1);
        gameBoard.setStore(store2, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    protected void validateMove(final int pit, final int player) throws InvalidMoveException {
        if (!isValidPit(pit)) {
            throw new InvalidMoveException("Invalid start pit: " + (pit + 1));
        }

        if (isSideEmpty(pit+1)) {
            throw new InvalidMoveException("Game is over");
        }
        
        if (isPlayerAssociatedWithPit(pit, player)) {
            throw new InvalidMoveException("Player not associated with this pit");
        }

        if (isPitEmpty(pit)) {
            throw new InvalidMoveException("Invalid move: This pit has 0 stones");
        }
    }

    private boolean isValidPit(final int pit) {
        return pit >= 0 && pit <= 11;
    }
    
    private boolean isPitEmpty(final int pit) {
        return getNumStones(pit+1) == 0;
    }
    
    private boolean isPlayerAssociatedWithPit(final int pit, final int player) {
        return pit > 5 && player == 1 || pit <= 5 && player == 2;
    }

    protected int lastStonePit(final int startingPit, final boolean skipStartPit){
        final int six = 6;
        final int thirteen = 13;
        gameBoard.setIterator(startingPit, currentPlayer, skipStartPit);
        final int totalAmount = gameBoard.getNumStones(startingPit);
        for (int i = 0; i<totalAmount; i++){
            gameBoard.next();
        }
        int lastPitIndex = gameBoard.getIteratorPos();

        if (lastPitIndex == six){
            lastPitIndex = -1;
        } else if (lastPitIndex == thirteen){
            lastPitIndex = -2;
        }

        if (lastPitIndex < 6 && lastPitIndex >= 0) {
            lastPitIndex++;
        }

        return lastPitIndex;
    }

    protected void gameOver(){
        for (int i = 7; i<=12; i++){
            gameBoard.addToStore(2, gameBoard.removeStones(i));
        }

        for (int i = 1; i<=6; i++){
            gameBoard.addToStore(1, gameBoard.removeStones(i));
        } 
    }

    protected int determinWinner(){
        if (gameBoard.getStoreCount(1) > gameBoard.getStoreCount(2)){
            return 0;
        } else if (gameBoard.getStoreCount(2) > gameBoard.getStoreCount(1)){
            return 1;
        } else {
            return -1;
        }
    }

    protected int getStoneCount(final int player){
        return gameBoard.getStoreCount(player);
    }

    protected int getStoreCount(final int player){
        return gameBoard.getStoreCount(player);
    }

    protected int removeStones(final int pit){
        return gameBoard.removeStones(pit);
    }

    protected void setIterator(final int startPos, final int playerNum, final boolean skipStartPit){
        gameBoard.setIterator(startPos, playerNum, skipStartPit);
    }

    protected Countable next(){
        return gameBoard.next();
    }

    protected int addToStore(final int playerNum, final int stones){
        return gameBoard.addToStore(playerNum, stones);
    }

    protected int getIterator(){
        return gameBoard.getIteratorPos();
    }
    
    /**
     * The toString() method returns a string representation of the game board state, including the
     * number of stones in each pit and the store count for each player.
     * 
     * @return The toString() method is returning a string representation of the game board..
     */
    @Override
    public String toString() {
        // Implement toString() method logic here.
        String returnString = "";
        int num = 1;
        for (int i = 1; i<=12; i++){
            returnString += "Pit " + num + ": " + gameBoard.getNumStones(i) + "\n";
            num++;
        }
        returnString += "Player 1: " + gameBoard.getStoreCount(1) + "\n";
        returnString += "Player 2: " + gameBoard.getStoreCount(2) + "\n";
        return returnString;
    }
}
