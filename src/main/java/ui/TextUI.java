package ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.GameRules;
import mancala.KalahRules;
import mancala.UserProfile;

/**
 * The MancalaUI class is a JFrame that represents the user interface for a Mancala game.
 */
public class TextUI extends JFrame {
    private JPanel gameContainer;
    private JLabel statusLabel;
    private JMenuBar menuBar;
    private PositionAwareButton[][] buttons;
    private MancalaGame game;
    private Player playerOne;
    private Player playerTwo;
    Saver saver = new Saver();

    // This constructor sets up
    // the user interface for a Mancala game. 
    public TextUI(String title) {
        super();
        basicSetUp(title);
        showWelcomeDialog();
        setupGameContainer();
        add(gameContainer, BorderLayout.CENTER);
        add(makeButtonPanel(), BorderLayout.WEST);
        add(makeTurnInfo(), BorderLayout.SOUTH);
        add(makeButtonGrid(8, 3));
        newGame();
        makeMenu();
        setJMenuBar(menuBar);
        pack();
    }

    /**
     * The main function creates and displays a MancalaUI object.
     */
    public static void main(String[] args) {
        TextUI example = new TextUI("Mancala Game");
        example.setVisible(true);
    }

    private void showWelcomeDialog() {
        JDialog welcomeDialog = new JDialog(this, "Welcome to Mancala Game!", true);
        welcomeDialog.setSize(300, 150);
        welcomeDialog.setLayout(new BorderLayout());
    
        JLabel titleLabel = new JLabel("Welcome to Mancala Game!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeDialog.add(titleLabel, BorderLayout.NORTH);
    
        JTextField playerOneTextField = new JTextField("Player 1");
        JTextField playerTwoTextField = new JTextField("Player 2");
    
        JButton kalahButton = new JButton("Start Kalah Game");
        JButton ayoButton = new JButton("Start Ayoayo Game");
    
        kalahButton.addActionListener(e -> {
            welcomeDialog.dispose();
            startNewGame(new KalahRules(), playerOneTextField.getText(), playerTwoTextField.getText());
        });
    
        ayoButton.addActionListener(e -> {
            welcomeDialog.dispose();
            startNewGame(new AyoRules(), playerOneTextField.getText(), playerTwoTextField.getText());
        });
    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Player 1 Name:"));
        inputPanel.add(playerOneTextField);
        inputPanel.add(new JLabel("Player 2 Name:"));
        inputPanel.add(playerTwoTextField);
        inputPanel.add(kalahButton);
        inputPanel.add(ayoButton);
    
        welcomeDialog.add(inputPanel, BorderLayout.CENTER);
    
        // Center the dialog on the screen
        welcomeDialog.setLocationRelativeTo(null);
    
        welcomeDialog.setVisible(true);
    }
    
    private void askToSaveOrLoadProfiles() {
        Object[] options = {"Save Profiles", "Load Profiles", "Continue to game"};
        int result = JOptionPane.showOptionDialog(
                this,
                "Do you want to save or load player profiles?",
                "Save or Load Profiles",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    
        switch (result) {
            case 0: // Save Profiles
                savePlayerProfiles();
                break;
            case 1: // Load Profiles
                loadPlayerProfiles();
                break;
            default:
                // Cancel
                break;
        }
    }
    
    private void loadPlayerProfiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // Allow the user to select multiple files
        int userSelection = fileChooser.showOpenDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] filesToLoad = fileChooser.getSelectedFiles();
    
            // Load player profiles
            for (File file : filesToLoad) {
                String fileName = file.getName();
                UserProfile loadedProfile = (UserProfile) saver.loadObject(fileName);
    
                // Ask the user which player to assign the loaded profile to
                Object[] playerOptions = {"Player 1", "Player 2"};
                int playerChoice = JOptionPane.showOptionDialog(
                        this,
                        "Assign profile to which player?",
                        "Select Player",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        playerOptions,
                        playerOptions[0]);
    
                if (playerChoice == 0) {
                    playerOne = loadedProfile.getPlayer();
                    playerOne.setProfile(loadedProfile);
                } else {
                    playerTwo = loadedProfile.getPlayer();
                    playerTwo.setProfile(loadedProfile);
                }
            }
    
            makePlayerInfo(); // Update the displayed player info
        }
    }
    
    private void savePlayerProfiles() {                          //TODO jj
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // Allow the user to select multiple files
        int userSelection = fileChooser.showSaveDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File[] filesToSave = fileChooser.getSelectedFiles();
    
            // Ask the user which player's profile to save
            Object[] playerOptions = {"Player 1", "Player 2"};
            int playerChoice = JOptionPane.showOptionDialog(
                    this,
                    "Save profile of which player?",
                    "Select Player",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    playerOptions,
                    playerOptions[0]);
    
            UserProfile profileToSave;
    
            if (playerChoice == 0) {
                profileToSave = playerOne.getProfile();
            } else {
                profileToSave = playerTwo.getProfile();
            }
    
            // Save player profile
            for (File file : filesToSave) {
                String fileName = file.getName();
                saver.saveObject(profileToSave, fileName);
            }
        }
    }

    private void startNewGame(GameRules newGame, String playerOneName, String playerTwoName){
        game = new MancalaGame(newGame);
        playerOne = new Player(playerOneName);
        playerTwo = new Player(playerTwoName);
        game.getBoard().registerPlayers(playerOne, playerTwo);
        game.setPlayers(playerOne, playerTwo);
        askToSaveOrLoadProfiles();
    }
       
    private void basicSetUp(String title){
        this.setTitle(title);
        gameContainer = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());   
    }

    private JPanel startupMessage() {
        JPanel temp = new JPanel();
       // Customize the message as desired
        temp.add(new JLabel("Welcome to Mancala!"));
        return temp;
       }

    private void setupGameContainer(){
        gameContainer.add(startupMessage());
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeMancalaButton());
        buttonPanel.add(makeNewGameButton());
        buttonPanel.add(makeQuitButton());
        buttonPanel.add(makePlayerInfo());
        return buttonPanel;
    }

    private JButton makeNewGameButton() {
        JButton button = new JButton("New Game");
        button.addActionListener(e -> {
            resetGame();
        });
        
        return button;
    }

    private JButton makeMancalaButton() {
        JButton button = new JButton("Kakuro");
        return button;
    }

    private JLabel makePlayerInfo() {
        JLabel playerInfo = new JLabel();
    
        String playerOneInfo = playerOne.getProfile().toString().replace("\n", "<br>");
        String playerTwoInfo = playerTwo.getProfile().toString().replace("\n", "<br>");
    
        playerInfo.setText("<html>" + playerOneInfo + "<br><br><br>" + playerTwoInfo + "</html>");
    
        return playerInfo;
    }
    
    

    private JPanel makeTurnInfo(){
        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.X_AXIS));
        turnPanel.add(makeStatusLabel());
        return turnPanel;
        
    }

    private JLabel makeStatusLabel(){
        // JLabel for displaying player turns and errors
        statusLabel = new JLabel("Player 1's turn");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setVerticalAlignment(JLabel.BOTTOM);
        statusLabel.setForeground(Color.BLUE);
        return statusLabel;
    }

    private JButton makeQuitButton() {
        JButton button = new JButton("Quit Game & Exit");
        askToSaveOrLoadProfiles();
        button.addActionListener(e -> super.dispose());
        return button;
    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[wide][tall]; // array of buttons
        panel.setLayout(new GridLayout(wide, tall));
    
        for (int y = 0; y < wide; y++) {
            for (int x = 0; x < tall; x++) {
                // Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1); // 1-based array
                buttons[y][x].setDown(y + 1);
                buttons[y][x].setBackground(Color.BLACK);
    
                panel.add(buttons[y][x]);
            }
        }
        addColorToGrid(buttons);
        return panel;
    }
    
    private void handleButtonClick(int x) {
        try {
            game.move(x);
            updateView();
            displayStatus((game.getCurrentPlayer().getName()) + "'s Turn", Color.BLUE);
        } catch (Exception exception) {
            displayStatus("Error: " + exception.getMessage(), Color.RED);
            updateView();
        }
    }
    
    private void displayStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    private void addColorToGrid(PositionAwareButton[][] buttons) {
        for (int x = 1; x < 7; x++) {
            buttons[0][x].setBackground(Color.WHITE);
            buttons[2][x].setBackground(Color.WHITE);
        }

        for (int x = 1; x <= 6; x++){
            final int finalX = x;
            buttons[2][x].addActionListener(e -> {
                handleButtonClick(finalX);
            });
        }

        int pitNum = 12;
        for (int x = 1; x <= 6; x++){
            final int finalX = pitNum;
            buttons[0][x].addActionListener(e -> {
                handleButtonClick(finalX);
            });
            pitNum--;
        }

        buttons[1][0].setBackground(Color.WHITE);
        buttons[1][7].setBackground(Color.WHITE);
    }
    

    private void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        // Customize the menu item label
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Open");

        saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
       
        menu.add(saveItem);
        menu.add(loadItem);
        menuBar.add(menu);
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getName();

            saver.saveObject(game, fileName); // Assuming game is Serializable
        }
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String fileName = fileToLoad.getName();

            MancalaGame loadedGame = (MancalaGame) saver.loadObject(fileName);

            if (loadedGame != null) {
                this.game = loadedGame;
                updateView(); // Update the UI to reflect the loaded game state
            }
        }
    }

    private void newGame(){
        updateView();
    }

    private void resetGame(){
        game.resetBoard(); 
        game.setCurrentPlayer(playerOne);
        game.getBoard().setPlayer(1);
        updateView();
    }

    private void updateView() {
        makePlayerInfo();         //TODO
        if (game.isGameOver()) {
            try {
                if (game.getWinner() == null) {
                    displayStatus("Game is over, tie game", Color.GREEN);
                } else {
                    if (game.getBoard() instanceof KalahRules){
                        playerOne.getProfile().addNumKalah();
                        playerTwo.getProfile().addNumKalah();
                        game.getWinner().getProfile().addNumKalahWon();
                    } else if (game.getBoard() instanceof AyoRules){
                        playerOne.getProfile().addNumAyo();
                        playerTwo.getProfile().addNumAyo();
                        game.getWinner().getProfile().addNumAyoWon();
                    }
                    displayStatus("Game is over, winner is " + game.getWinner().getName(), Color.GREEN);
    
                    askToSaveOrLoadProfiles();
    
                    Object[] options = {"New Game", "Main Menu"};
                    int result = JOptionPane.showOptionDialog(
                            this,
                            "What would you like to do?",
                            "Game Over",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
    
                    switch (result) {
                        case 0: // New Game
                            resetGame();
                            break;
                        case 1: // Main Menu
                            showWelcomeDialog();
                            break;
                    }
                }
            } catch (GameNotOverException e) {
                e.printStackTrace();
            }
        }

        // Update pits for Player 1
        for (int x = 0; x < 6; x++) {
            buttons[2][x+1].setText(String.valueOf(game.getBoard().getNumStones(x+1)));
        }
    
        // Update store for Player 2
        buttons[1][0].setText(String.valueOf(game.getBoard().getDataStructure().getStoreCount(2)));
    
        // Update pits for Player 2
        for (int x = 1; x <= 6; x++) {
            buttons[0][x].setText(String.valueOf(game.getBoard().getNumStones(13-x)));
        }
    
        // Update store for Player 1
        buttons[1][7].setText(String.valueOf(game.getBoard().getDataStructure().getStoreCount(1)));
    }
    
}