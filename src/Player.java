import java.util.*;

/**
 * Physical PLayer.
 * -Contains their hand, pos & if they've lost
 */
public class Player {
    Game.Players name;
    ArrayList<Card<?>> hand;
    boolean hasLost;
    Position newPos;
    Position oldPos;

    Player(Game.Players name, Position startPos) {
        this.name = name;
        hand = new ArrayList<>();
        hasLost = false;
        newPos = startPos;
        oldPos = startPos;
    }

    /**
     * Adds a card to players hand
     * Use to Create a plaeyr
     * @param card
     */
    public void addToHand(Card<?> card){
        this.hand.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return hasLost == player.hasLost &&
                name == player.name &&
                Objects.equals(hand, player.hand) &&
                Objects.equals(newPos, player.newPos) &&
                Objects.equals(oldPos, player.oldPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hand, hasLost, newPos, oldPos);
    }

    /**
     * Player takes their turn.
     * Gets the choice to move if they have been moved to another room
     * Has the choice of suggest / accuse
     */
    public void takeTurn(Board board, List<Player> allPlayers) throws InvalidActionException {
        boolean willMove = true;
        //If they have been moved to a room, Player chooses if they want to move again
        if(oldPos != newPos){
            String response = Game.chooseFromArray(new String[]{"Yes", "No"},
                    "Would you like to move?.\n");
            if(response.equals("No")){ willMove = false; }
        }

        //Moving
        if(willMove){
            int numMove = (int)(Math.random() * 6) + 1;
            numMove += (int)(Math.random() * 6) + 1;

            while(numMove > 0){
                numMove = makeMove(numMove, board);
            }
        }

        //Checking if suggest or accuse
        String action = Game.chooseFromArray(new String[]{"Suggest", "Accuse"},
                "Would you like to Accuse or Suggest?.\n");

        //Getting player and weapon
        Game.Players player = Game.chooseFromArray(Game.Players.values(),
                "Please choose a Person:\n");
        Game.Weapons weapon = Game.chooseFromArray(Game.Weapons.values(),
                "Please choose a Weapon.\n");

        //Accuse / Suggest & getting room
        if(action.equals("Accuse")){
            Game.Rooms room = Game.chooseFromArray(Game.Rooms.values(),
                    "Please choose a Weapon.\n");
            makeAccuse(room, player, weapon);
        }else{
            Game.Rooms room = Game.Rooms.valueOf(board.board[newPos.y][newPos.x].room.name);
            makeSuggest(room, player, weapon, allPlayers);
        }
    }

    public int makeMove(int numMove, Board board){
        boolean hasMoved = false;
        String response = "";
        System.out.println("--------------------------------------------");
        System.out.println("Your turn to move: you have "+numMove+" moves.");

        //Creating new move
        while(!hasMoved){
            response = "";
            while(response.length() < 1) {
                System.out.println("Please type a valid number of moves. \n" +
                        "'L' for Left, 'R' for Right, 'U' for Up and 'D' for Down");
                response = new Scanner(System.in).nextLine().toLowerCase();
            }
            try {
                hasMoved = new Move(board, this, response.split(""), numMove).apply();
            }catch(InvalidActionException e){ System.out.println("Invalid move, try again."); }
        }
        return numMove-response.length();
    }

    public void makeSuggest(Game.Rooms room, Game.Players player, Game.Weapons weapon, List<Player> allPlayers){
        boolean hasSuggested = false;
        while(!hasSuggested){
            try {
                hasSuggested = new Suggest(room, player, weapon, this, allPlayers).apply();
            }catch(InvalidActionException e){ System.out.println("Invalid move, try again."); }
        }
    }

    public void makeAccuse(Game.Rooms room, Game.Players player, Game.Weapons weapon){
        boolean hasAccused = false;
        while(!hasAccused){
            hasAccused = new Accuse(room, player, weapon, this).apply();
        }
    }

    /**
     * @return Players hand
     */
    public ArrayList<Card<?>> getHand(){ return hand; }

    public Game.Players getName() {
        return name;
    }

    @Override
    public String toString() {
        return name.toString().substring(0, 2);
    }

    /**
     * Look through this hand for any matches
     * @param room Game.Rooms
     * @param accused Game.Players
     * @param weapon Game.Weapons
     * @return arraylist of the matches
     */
    public ArrayList<Card> addMatches(Card room, Card accused, Card weapon) {
        ArrayList<Card> matches = new ArrayList<>();
        if (hand.contains(room)) matches.add(room);
        if (hand.contains(accused)) matches.add(accused);
        if (hand.contains(weapon)) matches.add(weapon);

        return matches;
    }
}
