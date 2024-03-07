package mancala;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * The MancalaGame class is a serializable class that represents a game of Mancala.
 */
public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    final private ArrayList<Player> players;
    private Player currentPlayer;
    private GameRules board;

    // The code  is a no-argument constructor for
    // the `MancalaGame` class. 
    public MancalaGame(){
        players = new ArrayList<>();
    }

    // Is a constructor for the `MancalaGame`
    // class that takes a `GameRules` object as a parameter.
    public MancalaGame(final GameRules newBoard){
        setBoard(newBoard);
        players = new ArrayList<>();
    }

    /**
     * The function sets two players, adds them to a list, registers them on a board, and sets the
     * current player to the first player in the list.
     * 
     * @param onePlayer The first player object to be added to the list of players.
     * @param twoPlayer The parameter "twoPlayer" is an instance of the Player class. It represents the
     * second player in the game.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer){
        players.add(onePlayer);
        players.add(twoPlayer);
        board.registerPlayers(onePlayer, twoPlayer);
        currentPlayer = players.get(0);
    }

    /**
     * The getCurrentPlayer() function returns the current player.
     * 
     * @return The method is returning the currentPlayer object of type Player.
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * The function sets the current player to the specified player.
     * 
     * @param player The "player" parameter is an instance of the Player class.
     */
    public void setCurrentPlayer(final Player player){
        currentPlayer = player;
    }

    /**
     * The function sets the board for the game with the provided GameRules object.
     * 
     * @param theBoard The parameter "theBoard" is of type GameRules.
     */
    public void setBoard(final GameRules theBoard){
        board = theBoard;
    }

    /**
     * The function returns the GameRules object representing the current state of the game board.
     * 
     * @return The method is returning an object of type GameRules.
     */
    public GameRules getBoard(){
        return board;
    }

    /**
     * The function returns the number of stones in a specified pit on a board, throwing an exception
     * if the pit number is invalid.
     * 
     * @param pitNum The parameter "pitNum" represents the number of the pit from which we want to get
     * the number of stones.
     * @return The method is returning the number of stones in the specified pit.
     * @throws PitNotFoundException If the pit is invalid.
     */
    public int getNumStones(final int pitNum) throws PitNotFoundException{
        final int pit = pitNum - 1;
        if (pit < 0 || pit > 11) {
            throw new PitNotFoundException("Invalid starting point: " + pitNum);
        }
        return board.getNumStones(pitNum);
    }

    /**
     * The function moves stones from a specified pit on the board and returns the total number of
     * stones moved.
     * 
     * @param startPit The startPit parameter represents the pit number from which the move is being
     * made. It is a 1-based index, so if startPit is 1, it refers to the first pit on the board.
     * @return The method is returning the total amount of stones in the pits of the current player
     * after the move has been made.
     * @throws InvalidMoveException If the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException {
        final int pit = startPit - 1;
        board.validateMove(pit, board.getCurrentPlayer());
        int totalAmount = 0;
            board.moveStones(startPit, board.getCurrentPlayer());
            if (currentPlayer.equals(players.get(0))){
                for (int i = 1; i<=6; i++){
                    totalAmount += board.getNumStones(i);
                }
            } else if (currentPlayer.equals(players.get(1))){
                for (int i = 7; i<=12; i++){
                    totalAmount += board.getNumStones(i);
                }
            }
        if (board.getCurrentPlayer() == 1){
            setCurrentPlayer(players.get(1));
            board.setPlayer(2);
        } else {
            setCurrentPlayer(players.get(0));
            board.setPlayer(1);
        }
        return totalAmount;
    }

    /**
     * The function returns the store count of a player, but throws an exception if the player is not
     * found in the list of players.
     * 
     * @param player The "player" parameter is of type Player, which represents a player in a game.
     * @return The method is returning the store count of the specified player.
     * @throws NoSuchPlayerException If no player exists.
     */
    public int getStoreCount(final Player player) throws NoSuchPlayerException{
        if (players.isEmpty() || !player.equals(players.get(0)) && !player.equals(players.get(1))){
            throw new NoSuchPlayerException("Player cannot be found.");
        }

        return player.getStoreCount();
    }

    /**
     * The function returns the winner of the game if it is over, otherwise it throws a
     * GameNotOverException.
     * 
     * @return The method is returning the Player object that represents the winner of the game.
     * @throws GameNotOverException If game is not over.
     */
    public Player getWinner() throws GameNotOverException{
        Player winner = null;
        if (!isGameOver()){
            throw new GameNotOverException();
        } else if (board.determinWinner() > -1){
            winner = players.get(board.determinWinner());
        } 
        return winner;
    }

    /**
     * The function checks if either side of the board is empty and if so, it triggers the game over
     * state and returns true.
     * 
     * @return The method is returning a boolean value, either true or false.
     */
    public boolean isGameOver(){
        boolean yes = false;
        if (board.isSideEmpty(2)){
            board.gameOver();
            yes = true;
        } else if (board.isSideEmpty(8)){
            board.gameOver();
            yes = true;
        } 
        return yes;
    }

    /**
     * Resets the game board and ends the game.
     */
    public void resetBoard(){
        board.gameOver();
        board.resetBoard();
    }

    /**
     * The startNewGame function sets the board for a new game.
     */
    public void startNewGame(){
        setBoard(board);
    }

    /**
     * The toString() function returns a string representation of the game state, including the winner
     * if the game is over.
     * 
     * @return The method is returning a string representation of the game state. If the game is over,
     * it will return a string indicating the winner and the current state of the board. If the game is
     * not over, it will return the string "Game not over".
     */
    @Override
    public String toString(){
        String name;
        String returnString = "";
        if (isGameOver()){
            try {
                name = getWinner().getName();
                returnString += "Game over, " + name + " won!\n";
                returnString += board.toString() + returnString + "\n";
            } catch (GameNotOverException e) {
                returnString = "Game not over";
            }
        }
        return returnString; 
    }
}