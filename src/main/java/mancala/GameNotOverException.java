package mancala;

/**
 * The GameNotOverException class is a custom exception that can be thrown when the game is not yet
 * over.
 */
public class GameNotOverException extends Exception {
    private static final long serialVersionUID = 1L;
    // Is a constructor for the
    // `GameNotOverException` class.
    public GameNotOverException(){
        super("Game not over");
    }

    // Is a constructor for
    // the `GameNotOverException` class.
    public GameNotOverException(final String message) {
        super(message);
    }
}