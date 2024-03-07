package mancala;

/**
 * The class AyoRules extends the GameRules class.
 */
public class AyoRules extends GameRules{
    private static final long serialVersionUID = 1L;

    /**
     * The function moves stones from a specified pit and returns the difference in stone count for the
     * player.
     * 
     * @param startPit The starting pit from which the stones will be moved.
     * @param playerNum The `playerNum` parameter represents the player number.
     * @return Returning the difference between the current stone count in the player's
     * store and the initial stone count before the move.
     * @throws InvalidMoveException If game move is not valid.
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
     * The function distributes stones from a starting pit to the subsequent pits, capturing stones if
     * certain conditions are met.
     * 
     * @param startPit It is the pit where the distribution of stones will begin.
     * @return The method is returning an integer value, which is the total amount of stones
     * distributed.
     */
    @Override
    /* default */ int distributeStones(final int startPit) {
        final int pit = startPit - 1;
        int totalAmount = 0;
        final int playerNum = getCurrentPlayer();

        int pitAmount = removeStones(startPit);
        totalAmount += pitAmount;

        Countable currentSpot;
        setIterator(startPit, playerNum, true);

        while (pitAmount > 0) {
            currentSpot = next();
            currentSpot.addStone();
            pitAmount--;
            if (pitAmount == 0 && currentSpot instanceof Pit && currentSpot.getStoneCount() != 1) {
                pitAmount = currentSpot.removeStones();
                totalAmount += pitAmount;
            }
        }

        final int lastPit = getIteratorPosition();
        initiateCapture(pit, lastPit);
        return totalAmount;
    }

    private int getIteratorPosition(){
        int lastPitIndex = getIterator();

        if (lastPitIndex == 6){
            lastPitIndex = -1;
        } else if (lastPitIndex == 13){
            lastPitIndex = -2;
        }

        if (lastPitIndex < 6 && lastPitIndex >= 0) {
            lastPitIndex++;
        }

        return lastPitIndex;
    }

    private int initiateCapture(final int pit, final int lastPit) {
        final int lastPitModified = lastPit - 1;
        int stonesCaptured = 0;
        if (pit <= middle && lastPitModified <= middle || pit > middle && lastPitModified > middle){
                stonesCaptured = captureStones(lastPit);
            }
        return stonesCaptured;
    }

    /**
     * The function captures stones from a pit in a game and adds them to the player's store based on
     * the pit's position.
     * 
     * @param stoppingPoint The stoppingPoint parameter represents the index of the pit where the last
     * stone was placed.
     * @return The method `captureStones` is returning the number of captured stones as an integer.
     */
    @Override
    /* default */ int captureStones(final int stoppingPoint) {
        final int pit = stoppingPoint - 1;    
        final int oppositePit = 11 - pit;
        final int capturedStones = removeStones(oppositePit+1);
        if (pit <= middle){
            addToStore(1, capturedStones);
        } else if (pit > middle){
            addToStore(2, capturedStones);
        }
        return capturedStones;
    }
    
}
