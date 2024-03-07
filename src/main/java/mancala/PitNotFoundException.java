package mancala;

/**
 * The PitNotFoundException class is a custom exception that is thrown when a pit is not found.
 */
public class PitNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    // is a constructor for the
    // `PitNotFoundException` class.
    public PitNotFoundException(){
        super("Pit Not Found");
    }

    // is a constructor for
    // the `PitNotFoundException` class that takes a `String` parameter `message`.
    public PitNotFoundException(final String message) {
        super(message);
    }
}