/**
 * Accuse Action
 *  -On a players 2nd action after move they can choose to accuse
 *  -Check if user has correct guess
 *  -End game if correct, current player loses if incorrect
 */
public class Accuse implements Action {
    private final Game.Rooms room;
    private final Game.Suspects suspect;
    private final Game.Weapons weapon;
    private final Player player;

    Accuse(Game.Rooms room, Game.Suspects suspect, Game.Weapons weapon, Player player) {
        this.room = room;
        this.suspect = suspect;
        this.weapon = weapon;
        this.player = player;
    }

    @Override
    public void apply() {
        //Check to see if the accused matches
        if (Game.murderRoom.equals(room) && Game.murderer.equals(suspect) && Game.murderWeapon.equals(weapon)) {
            Game.gameOver = true;
            Game.print(player.getName()+" is correct and has won!");
            System.out.println("You have won!");
        } else {
            player.setHasLost(true);
            Game.print(player.getName()+" is wrong and has lost!");
            System.out.println("Your accusation is incorrect. You have lost.");
        }
        Game.print(suspect+" of using the "+weapon+" in the "+room);
        Game.print("\n"+player.getName()+" accused:");

        System.out.println("Press 'enter' to continue.");
        Game.input.nextLine();
    }
}
