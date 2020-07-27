import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    public enum Players {
        SCARLET,
        PLUM,
        WHITE,
        PEACOCK,
        GREEN,
        MUSTARD;

    }

    public enum Rooms{
        KITCHEN,
        BALLROOM,
        STUDY,
        BILLARD_ROOM,
        CONSERVATORY,
        DINING_ROOM,
        HALL,
        LIBRARY,
        LOUNGE;

    }

    public enum Weapons{
        CANDLESTICK,
        DAGGER,
        LEAD_PIPE,
        REVOLVER,
        ROPE,
        SPANNER;


    }

    /**
     * Shows the initial instructions.
     * Ask if the user just wants to play or view the instructions
     */
    private static void showInitialInstructions() throws FileNotFoundException {
        System.out.println("Welcome to Cluedo!\n" +
                "To view the instructions type 'instructions'\n" +
                "To play the game type 'play'");

        String response = new Scanner(System.in).nextLine();

        if (response.equals("instructions")) showInstructions();
        else if (response.equals("play")) playGame();
        else {
            System.out.println("Invalid response, try again!");
            showInitialInstructions();
        }
    }

    /**
     * Play the game
     */
    private static void playGame() {
        System.out.println("You are playing the game");
    }

    /**
     * Show the instructions
     */
    private static void showInstructions() throws FileNotFoundException {
        File instructions = new File("Assets/Instructions.txt");
        Scanner scanner = new Scanner(instructions);
        while (scanner.hasNextLine()) System.out.println(scanner.nextLine());
    }

    public static void main(String[] args) throws FileNotFoundException {
        showInitialInstructions();
    }
}
