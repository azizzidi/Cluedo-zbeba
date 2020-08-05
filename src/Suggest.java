import java.util.ArrayList;

public class Suggest implements Action {
    private final Card<Game.Players> suspect;
    private final Card<Game.Rooms> room;
    private final Card<Game.Weapons> weapon;
    private final Player player;

    Suggest(Game.Rooms room, Game.Players suspect, Game.Weapons weapon, Player player) {
        this.suspect = new Card<>(suspect);
        this.room = new Card<>(room);
        this.weapon = new Card<>(weapon);
        this.player = player;
    }

    @Override
    public void apply() {
        // Move the suggested player to the room
        Player suspectPlayer = Game.playerMap.get(suspect.getEnum());
        Position pos = suspectPlayer.getPos();
        Game.board.getTile(pos).removePlayer();
        Board.rooms.get(room.getEnum()).addPlayer(suspectPlayer);

        Game.clearOutput();

        // Go through each players hand after this player looking for a match
        int indexOfPlayer = Game.players.indexOf(player);
        for (int i = indexOfPlayer+1; i != indexOfPlayer; i = (i+1) % Game.players.size()) {
            Player otherPlayer = Game.players.get(i);
            if (otherPlayer.getHand().size() == 0) continue;
            ArrayList<Card<?>> cardOptions = otherPlayer.addMatches(room, suspect, weapon);

            // A match has been found in this hand
            if (!cardOptions.isEmpty()) {
                // Allow the player to choose a card without current player seeing
                System.out.println(otherPlayer.getName() + " can prove you wrong, let them choose a card to show you"
                        +"\nPress Enter if you are "+otherPlayer.getName());
                Game.input.nextLine();

                Card<?> toShow = Game.chooseFromArray(cardOptions.toArray(new Card<?>[]{}), "Choose a card to show to "+player.getName());

                // Go back to the current players turn and display the chosen card
                Game.clearOutput();
                System.out.println(otherPlayer.getName() + " has chosen to show you "+toShow
                        +"\nPress Enter if you are "+player.getName()+" to continue");
                Game.input.nextLine();

                return;
            } else {
                // Inform the player of who can't prove them wrong
                System.out.println(otherPlayer.getName() + " can't prove you wrong");
            }
        }
        // No matches were found
        System.out.println("No one can prove you wrong!");
        System.out.println("Press Enter to Continue");
        Game.input.nextLine();
    }
}
