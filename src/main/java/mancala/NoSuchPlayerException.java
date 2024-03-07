package mancala;

/**
 * The NoSuchPlayerException class is a custom exception that is thrown when a player does not exist.
 */
public class NoSuchPlayerException extends Exception {
    private static final long serialVersionUID = 1L;
    // is a constructor for
    // the `NoSuchPlayerException` class.
    public NoSuchPlayerException(){
        super("No such player found");
    }

    // is a constructor for
    // the `NoSuchPlayerException` class.
    public NoSuchPlayerException(final String message) {
        super(message);
    }
}