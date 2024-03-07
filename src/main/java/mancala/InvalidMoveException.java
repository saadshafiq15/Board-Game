package mancala;

/**
 * The InvalidMoveException class is a custom exception that can be thrown when an invalid move is made
 * in a game.
 */
public class InvalidMoveException extends Exception {
    private static final long serialVersionUID = 1L;
    // is a constructor for
    // the `InvalidMoveException` class.
    public InvalidMoveException(){
        super("Invalid Move");
    }

    // is a constructor for
    // the `InvalidMoveException` class.
    public InvalidMoveException(final String message) {
        super(message);
    }
}