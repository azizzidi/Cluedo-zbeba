import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class handles all of the drawing of the board
 */
public class GUI {
    JFrame window = new JFrame("Cluedo");
    CustomGrid baseLayout;

    int width = 1400;
    int height = 900;

    int widthFifths = width / 5;
    int heightSixths = height / 6;

    /**
     * These objects handle the four quadrants of the gui
     */
    ActionPanel actionPanel = new ActionPanel(new Dimension(widthFifths, heightSixths * 4));
    ConsolePanel consolePanel = new ConsolePanel(new Dimension(widthFifths, heightSixths * 4));
    BoardPanel boardPanel = new BoardPanel(ImageIO.read(new File("Assets/Test Files/Test 1.png")),  new Dimension(widthFifths * 3, heightSixths * 4));
    CardPanel cardPanel = new CardPanel();

    JPanel topBar = new JPanel();
    JPanel content = new JPanel();

    GUI() throws IOException {
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);// todo might update this at a later stage

        window.add(topBar);
        window.add(content);

        //Add the menu bar
        generateMenuBar(true);

        //Add the content
        baseLayout = new CustomGrid(content);
        setupGameLayout(baseLayout);

        //Display the window.
        window.pack();
        window.setVisible(true);
    }

    /**
     * This sets up the gui.
     * @param customGrid the grid layout
     */
    public void setupGameLayout(CustomGrid customGrid) throws IOException {
        customGrid.setLayout(new GridBagLayout());
        customGrid.setConstraints(new GridBagConstraints());

        actionPanel.setBackground(Color.cyan);
        customGrid.setFill(GridBagConstraints.HORIZONTAL);
        customGrid.setAnchor(GridBagConstraints.FIRST_LINE_START);
        customGrid.setWeight(0, 0);
        customGrid.setGrid(0, 0, 1, 1);
//        customGrid.setPadding(widthFifths, heightSixths * 4);
        customGrid.addElement(actionPanel);

        boardPanel.setBackground(Color.orange);
        customGrid.setFill(GridBagConstraints.HORIZONTAL);
        customGrid.setAnchor(GridBagConstraints.CENTER);
        customGrid.setWeight(0, 0);
        customGrid.setGrid(1, 0, 1, 1);
//        customGrid.setPadding(widthFifths * 3, heightSixths * 4);
        customGrid.addElement(boardPanel);

        consolePanel.setBackground(Color.magenta);
        customGrid.setFill(GridBagConstraints.CENTER);
        customGrid.setAnchor(GridBagConstraints.CENTER);
        customGrid.setWeight(0, 0);
        customGrid.setGrid(2, 0, 1, 1);
//        customGrid.setPadding(widthFifths, heightSixths * 4);
        customGrid.addElement(consolePanel);

        cardPanel.setBackground(Color.red);
        //cardPanel.initialiseDefaultText(100);
        customGrid.setFill(GridBagConstraints.VERTICAL);
        customGrid.setAnchor(GridBagConstraints.CENTER);
        customGrid.setWeight(0, 0);
        customGrid.setGrid(0, 2, 3, 1);
//        customGrid.setPadding(widthFifths * 5, heightSixths * 2);
        customGrid.addElement(cardPanel);
        try { cardPanel.setupCards();
        }catch(InvalidFileException e){}
    }

    /**
     * Generates the menu bar
     * @return
     */
    private void generateMenuBar(Boolean showInstructions) {
        topBar.removeAll();

        //choose instructions title
        String instructionTitle;
        if (showInstructions) instructionTitle = "Show Instructions";
        else instructionTitle = "Hide Instructions";

        //Menu Headings
        JMenuBar menuBar = new JMenuBar();
        JMenu playMenu = new JMenu("Game Options");
        JMenuItem debug = new JMenu("Debug");
        JMenuItem quit = new JMenuItem("Quit");

        //Items
        JMenuItem playGame = new JMenuItem("Play");
        JMenuItem restartGame = new JMenuItem("Restart");
        JMenuItem instructions = new JMenuItem(instructionTitle);
        JMenuItem printActivePlayer = new JMenuItem("Print Active Player");
        JMenuItem printActivePlayerCards = new JMenuItem("Print Active Player Cards");
        JMenuItem printGameRooms = new JMenuItem("Print Game Rooms");
        JMenuItem printFinal = new JMenuItem("Print Winning combo");
        JMenuItem printAllPlayers = new JMenuItem("Print All Players");
        JMenuItem printAllWeapons = new JMenuItem("Print All Weapons");


        //Add components
        playMenu.add(playGame);
        playMenu.add(restartGame);
        debug.add(printActivePlayer);
        debug.add(printActivePlayerCards);
        debug.add(printGameRooms);
        debug.add(printFinal);
        debug.add(printAllPlayers);
        debug.add(printAllWeapons);

        menuBar.add(playMenu);
        menuBar.add(instructions);
        menuBar.add(debug);
        menuBar.add(quit);
        topBar.add(menuBar);

        //Bind the menu items
        quit.addActionListener(actionEvent -> System.exit(0)); //exit the program
        instructions.addActionListener(actionEvent -> showInstructions());
    }

    /**
     * Show the instructions
     */
    private void showInstructions() {
        generateMenuBar(false);
        content.removeAll();

        


        redraw();
    }

    /**
     * Add a message to the console
     * @param message the message
     */
    public void addToConsole(String message) {consolePanel.addMessage(message);}

    /**
     * redraw the gui
     */
    public void redraw() {
        topBar.revalidate();
        consolePanel.redraw();
        boardPanel.repaint();
    }
}

class ActionPanel extends JPanel {
    JPanel container;

    ActionPanel(Dimension size) {
        container = new JPanel();
        container.setPreferredSize(size);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            container.add(container.add(new JLabel(new ImageIcon(ImageIO.read(
                    new File("Assets/Other/CLUEDO_LOGO.png"))))));
        }catch(IOException e){ }
        container.add(new Button("I'm a button"));
        container.add(new Button("I'm also a button"));
        this.add(container);
    }

    public void drawButtons(Player.Actions[] actions, Player player) {
        container.removeAll();
        try {
            container.add(container.add(new JLabel(new ImageIcon(ImageIO.read(
                    new File("Assets/Other/CLUEDO_LOGO.png"))))));
        }catch(IOException e){ }
        JTextArea textArea = new JTextArea(1, 1);
        textArea.setFont(textArea.getFont().deriveFont(18f));
        textArea.append(player.getSuspect() + "  |  " + player.getName());
        container.add(textArea);
        for(int i=0; i< actions.length; i++){
            System.out.println(actions[i].toString());
            container.add(new Button(actions[i].toString()));
        }
        this.add(container);
        revalidate();
    }
}

/**
 * This class handles displaying the console.
 * The console contains the last 30 actions of the game
 */
class ConsolePanel extends JPanel {
    ArrayList<String> consoleMessages = new ArrayList<>();
    JTextArea textArea = new JTextArea();
    JScrollPane scroll;

    ConsolePanel(Dimension size) {
        this.setPreferredSize(size);

        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BorderLayout(0, 0));

        textArea = new JTextArea(1, 5);
        textArea.setEditable(false);
        textArea.setBackground(Color.YELLOW);

        buildMessages();
        this.add(textArea, BorderLayout.CENTER);

        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
    }

    /**
     * Add all of the console messages to the gui
     */
    void buildMessages () {
        for (String str: consoleMessages)
            textArea.append(str+"\n");
    }

    /**
     * Add a message to the console. Max len of console is 30
     * @param message the message
     */
    void addMessage(String message) {
        if (consoleMessages.size() >= 30) consoleMessages.remove(29);
        consoleMessages.add(0, message);
    }

    /**
     * redraw the console
     */
    public void redraw() {
        textArea.setText(null);
        buildMessages();
    }
}

/**
 * This class handles displaying the board
 */
class BoardPanel extends JPanel {
    private BufferedImage board;

    BoardPanel(BufferedImage image, Dimension size) {
        this.board = image;
        this.setPreferredSize(size);
    }

    /**
     * Change to a new image of the board
     * @param newImage
     */
    public void updateImage(BufferedImage newImage) {
        this.board = newImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = (this.getWidth() / 2) - ((board.getWidth() * 26) / 2);
        int yOffset = (this.getHeight() / 2) - ((board.getWidth() * 25) / 2);
        for (int i = xOffset; i < (board.getWidth() * 26) + xOffset; i += 20) {
            for (int j = yOffset; j < (board.getWidth() * 25) + yOffset; j += 20) {
                g.drawImage(board, i, j, this);
            }
        }
    }
}

class CardPanel extends JPanel {

    /**
     * Sets up cards with default image
     * Populates container for start usage
     *
     * @throws InvalidFileException if default card file is not found
     */
    public void setupCards() throws InvalidFileException {
        //Setup variables
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BorderLayout(0, 0));

        //Draws cards with a 7px strut between cards
        try {
            for (int i = 0; i < 9; i++) {
                container.add(container.add(new JLabel(new ImageIcon(ImageIO.read(
                        new File("Assets/Cards/DEFAULT.png"))))));
                if (i < 8) container.add(Box.createHorizontalStrut(6));
            }
        }catch(IOException e){ throw new InvalidFileException("Assets/Cards/DEFAULT.png"); }
        this.add(container, BorderLayout.CENTER);
    }

    /**
     * Draws players cards on their turn
     *
     * @param cards list in the players hand.
     */
    public void drawCards(ArrayList<Card<?>> cards) {
        //Setup variables
        this.removeAll();
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BorderLayout(0, 0));

        //Draws each card with a strut
        for (int i = 0; i < cards.size(); i++) {
            container.add(container.add(cards.get(i).getImage()));
            if(i<cards.size()-1) container.add(Box.createHorizontalStrut((12-cards.size())*2));
        }
        this.add(container, BorderLayout.CENTER);

    }
}


/**
 * This class handles customs grids.
 */
class CustomGrid {
    Container gridContainer;
    GridBagConstraints constraints;

    CustomGrid(Container gridContainer) {
        this.gridContainer = gridContainer;
    }

    public void addElement(JPanel testPanel) {gridContainer.add(testPanel, constraints);}
    public void setFill(int fill) {constraints.fill = fill;}
    public void setAnchor(int anchor) {constraints.anchor = anchor;}
    public void setWeight(int weightX, int weightY) {
        constraints.weightx = weightX;
        constraints.weighty = weightY;
    }
    public void setGrid(int gridX, int gridY, int gridWidth, int gridHeight) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
    }
    public void setPadding(int xPad, int yPad) {
        constraints.ipadx = xPad;
        constraints.ipady = yPad;
    }

    /**
     * Set the layout
     * @param layout layout obj
     */
    public void setLayout(GridBagLayout layout) {
        gridContainer.setLayout(layout);
    }

    /**
     * Set the constraints
     * @param gridBagConstraints constraints obj
     */
    public void setConstraints(GridBagConstraints gridBagConstraints) {
        this.constraints = gridBagConstraints;
    }
}