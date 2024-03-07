package mancala;

/**
 * The KalahRules class extends the GameRules class and defines the rules for playing the game of
 * Kalah.
 */
public class KalahRules extends GameRules{
    private static final long serialVersionUID = 1L;

    /**
     * The function moves stones from a specified pit and returns the difference in stone count for the
     * player.
     * 
     * @param startPit The starting pit from which the stones will be moved. It is 1-based, so the
     * valid range is from 1 to the number of pits in the game.
     * @param playerNum The `playerNum` parameter represents the player number. It is used to identify
     * the player who is making the move.
     * @return The method is returning the difference between the current stone count in the player's
     * store and the initial stone count before the move.
     * 
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        final int pit = startPit - 1;
        validateMove(pit, playerNum);

        final int initialStoneCount = getStoneCount(playerNum);
        
        distributeStones(startPit);

        return getStoreCount(playerNum) - initialStoneCount;
    }

    /**
     * The function distributes stones from a specified pit in a game, updates the game state, and returns
     * the total number of stones distributed.
     * 
     * @param startPit The startPit parameter represents the pit number from which the stones will be
     * distributed.
     * @return The method is returning an integer value, which is the total amount of stones that were
     * distributed.
     */
    @Override
    /* default */ int distributeStones(final int startPit) {
        final int pit = startPit - 1;
        int lastPitAmount = -1;
        final int lastPit = lastStonePit(startPit, false);
        int bonus = 0;
        final int one = 1;
        final int two = 2;
        
        if (lastPit > 0){
            lastPitAmount = getNumStones(lastPit);
        } else if (lastPit == -1){
            bonus = 1;
        } else if (lastPit == -2){
            bonus = 2;
        }

        final int totalAmount = removeStones(startPit);
        final int whichPlayer = getCurrentPlayer();

        Countable currentSpot;
        setIterator(startPit, whichPlayer, false);
        for (int i = 0; i<totalAmount; i++){
            currentSpot = next();
            currentSpot.addStone();
        }
        
        initiateCapture(pit, lastPit, lastPitAmount);

        if (bonus == one){
            setPlayer(2);
        } else if (bonus == two){
            setPlayer(1);
        }

        return totalAmount;
    }

    private int initiateCapture(final int pit, final int lastPit, final int lastPitAmount) {
        final int lastPitModified = lastPit - 1;
        int stonesCaptured = 0;
        if ((pit <= middle && lastPitModified <= middle || pit > middle && lastPitModified > middle) && lastPitAmount == 0 && lastPit > 0){
                stonesCaptured = captureStones(lastPit);
            }
        return stonesCaptured;
    }

    /**
     * The function captures stones from two pits and adds them to the player's store based on the
     * stopping point.
     * 
     * @param stoppingPoint The stoppingPoint parameter represents the index of the pit where the
     * player's turn ends. 
     * @return The method is returning the number of captured stones.
     */
    @Override
    /* default */ int captureStones(final int stoppingPoint) {
        final int pit = stoppingPoint - 1;    
        final int oppositePit = 11 - pit;
        final int capturedStones = removeStones(oppositePit+1) + removeStones(stoppingPoint);
        if (pit <= middle){
            addToStore(1, capturedStones);
        } else if (pit > middle){
            addToStore(2, capturedStones);
        }
        return capturedStones;
    }
}
