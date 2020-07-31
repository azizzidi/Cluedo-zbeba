import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Construct a board of positions
 * -Draws board for user
 * -Contains locations of players
 */
public class Board {
   public static HashMap<Game.Rooms, Room> rooms = new HashMap<>();
   Tile[][] board = new Tile[25][24];

   public Board(){

      for(int i =0; i< board.length; i++){
         for(int j =0; j< board[i].length; j++){
            board[i][j] = new Tile();
         }
      }

      //sets room tiles
      ArrayList<Tile> Kitchen = new ArrayList<>();
      Kitchen = allocateTiles(Kitchen, 0,5,0,6);
      rooms.put(Game.Rooms.KITCHEN, new Room(Kitchen, Game.Rooms.KITCHEN));

      ArrayList<Tile> BallRoom = new ArrayList<>();
      BallRoom = allocateTiles(BallRoom, 10,13,0,1);
      BallRoom = allocateTiles(BallRoom, 8,15,2,7);
      rooms.put(Game.Rooms.BALLROOM, new Room(BallRoom, Game.Rooms.BALLROOM));

      ArrayList<Tile> Conservatory = new ArrayList<>();
      Conservatory = allocateTiles(Conservatory, 18,23,0,4);
      Conservatory = allocateTiles(Conservatory, 19,23,5,5);
      rooms.put(Game.Rooms.CONSERVATORY, new Room(Conservatory, Game.Rooms.CONSERVATORY));

      ArrayList<Tile> DinningRoom = new ArrayList<>();
      DinningRoom = allocateTiles(DinningRoom, 0,4,9,9);
      DinningRoom = allocateTiles(DinningRoom, 0,7,10,15);
      rooms.put(Game.Rooms.DINING_ROOM, new Room(DinningRoom, Game.Rooms.DINING_ROOM));

      ArrayList<Tile> BilliardRoom = new ArrayList<>();
      BilliardRoom = allocateTiles(BilliardRoom, 18,23,8,12);
      rooms.put(Game.Rooms.BILLARD_ROOM, new Room(BilliardRoom, Game.Rooms.BILLARD_ROOM));

      ArrayList<Tile> Library = new ArrayList<>();
      Library = allocateTiles(Library, 17,17,15,17);
      Library = allocateTiles(Library, 18,23,14,18);
      rooms.put(Game.Rooms.LIBRARY, new Room(Library, Game.Rooms.LIBRARY));

      ArrayList<Tile> Lounge = new ArrayList<>();
      Lounge = allocateTiles(Lounge, 0,6,19,24);
      rooms.put(Game.Rooms.LOUNGE, new Room(Lounge, Game.Rooms.LOUNGE));

      ArrayList<Tile> Hall = new ArrayList<>();
      Hall = allocateTiles(Hall, 9,14,18,24);
      rooms.put(Game.Rooms.HALL, new Room(Hall, Game.Rooms.HALL));

      ArrayList<Tile> Study = new ArrayList<>();
      Study = allocateTiles(Study, 17,23,21,24);
      rooms.put(Game.Rooms.STUDY, new Room(Study, Game.Rooms.STUDY));

      //blocked tiles
      ArrayList<Tile> Blocked = new ArrayList<>();
      Blocked = allocateTiles(Blocked, 6,6,0,1);
      Blocked = allocateTiles(Blocked, 7,8,0,0);
      Blocked = allocateTiles(Blocked, 15,16,0,0);
      Blocked = allocateTiles(Blocked, 17,17,0,1);
      Blocked = allocateTiles(Blocked, 10,14,10,16);
      Blocked.add(board[8][0]);
      Blocked.add(board[7][23]);
      Blocked.add(board[13][23]);
      Blocked.add(board[16][0]);
      Blocked.add(board[18][0]);
      Blocked.add(board[20][23]);
      Blocked.add(board[24][8]);
      Blocked.add(board[24][15]);
      rooms.put(null, new Room(Blocked, null));

      //sets doors
      //Kitchen
      board[7][4].setDoor(rooms.get(Game.Rooms.KITCHEN),new Position(4,7));

      //ball room
      board[5][7].setDoor(rooms.get(Game.Rooms.BALLROOM),new Position(7,5));
      board[8][9].setDoor(rooms.get(Game.Rooms.BALLROOM),new Position(9,8));
      board[8][14].setDoor(rooms.get(Game.Rooms.BALLROOM),new Position(14,8));
      board[5][16].setDoor(rooms.get(Game.Rooms.BALLROOM),new Position(16,5));

      //conservatory
      board[5][18].setDoor(rooms.get(Game.Rooms.CONSERVATORY),new Position(18,5));

      //Dinning room
      board[12][8].setDoor(rooms.get(Game.Rooms.DINING_ROOM),new Position(8,12));
      board[16][6].setDoor(rooms.get(Game.Rooms.DINING_ROOM),new Position(6,16));

      //Billiard room
      board[9][17].setDoor(rooms.get(Game.Rooms.BILLARD_ROOM),new Position(17,9));
      board[13][22].setDoor(rooms.get(Game.Rooms.BILLARD_ROOM),new Position(22,13));
      board[13][22].setRoomName(Game.Rooms.BILLARD_ROOM);

      //Library room
      board[13][20].setDoor(rooms.get(Game.Rooms.LIBRARY),new Position(20,13));
      board[13][20].setRoomName(Game.Rooms.LIBRARY);
      board[16][16].setDoor(rooms.get(Game.Rooms.LIBRARY),new Position(16,16));

      //Lounge room
      board[18][6].setDoor(rooms.get(Game.Rooms.LOUNGE),new Position(6,18));

      //Hall room
      board[17][11].setDoor(rooms.get(Game.Rooms.HALL),new Position(11,17));
      board[17][11].setRoomName(Game.Rooms.HALL);
      board[17][12].setDoor(rooms.get(Game.Rooms.HALL),new Position(12,17));
      board[17][12].setRoomName(Game.Rooms.HALL);
      board[20][15].setDoor(rooms.get(Game.Rooms.HALL),new Position(15,20));

      //Study room
      board[20][17].setDoor(rooms.get(Game.Rooms.STUDY),new Position(17,20));

      //sets rooms standing spots
      rooms.get(Game.Rooms.KITCHEN).setSeats(this,1,4);
      rooms.get(Game.Rooms.BALLROOM).setSeats(this,10,5);
      rooms.get(Game.Rooms.CONSERVATORY).setSeats(this,19,3);
      rooms.get(Game.Rooms.DINING_ROOM).setSeats(this,3,13);
      rooms.get(Game.Rooms.BILLARD_ROOM).setSeats(this,19,9);
      rooms.get(Game.Rooms.LIBRARY).setSeats(this,19,16);
      rooms.get(Game.Rooms.LOUNGE).setSeats(this,2,20);
      rooms.get(Game.Rooms.HALL).setSeats(this,10,20);
      rooms.get(Game.Rooms.STUDY).setSeats(this,19,22);
   }

   /**Helper method to set room tiles
    *
    * @param tiles
    * @param x1
    * @param x2
    * @param y1
    * @param y2
    * @return
    */

   public ArrayList<Tile> allocateTiles(ArrayList<Tile> tiles, int x1, int x2, int y1, int y2){
      for(int i=y1; i<=y2; i++){
         for(int j=x1; j<=x2; j++){
            tiles.add(board[i][j]);
         }
      }
      return tiles;
   }

    /**
     * Checks to see if this move in the board class is valid.
     * @param current the x pos
     * @param next the y pos
     * @return boolean indicating valid/invalid.
     */
    public boolean isValidMove(Position current, Position next) {
       //checks if the next position is outside of the board
       if(next.x < 0 || next.x > 23 || next.y < 0 || next.y > 24)
          return false;

       Tile nextTile = board[next.y][next.x];
       Tile currentTile = board[current.y][current.x];

       //checks if another player is already on that tile
       if(nextTile.player != null)
          return false;

       //checks non room to non room move
       if(!currentTile.isRoom() && !nextTile.isRoom())
          return true;

       //checks non room to room move
       if(!currentTile.isRoom() && nextTile.isRoom()){
          if(currentTile.isDoor()){
             //checks player can use door to enter the room
             return currentTile.doorRoom == null || currentTile.doorRoom.equals(nextTile.roomName);
          } return false;
       }

       //checks room to non room move
       if(currentTile.isRoom() && !nextTile.isRoom()){
          if(nextTile.isDoor()) {
             if(nextTile.doorRoom==null || nextTile.doorRoom.equals(currentTile.roomName)){
                return true;
             }
          }
       }
       System.out.println("3");
        return false;
    }

   /**gets Tile from x and y
    *
    * @param x
    * @param y
    * @return
    */
    public Tile getTile(int x, int y){
       return board[y][x];
    }

   /**moves player on board
    *
    * @param currentPos
    * @param nextPos
    */
    public void movePlayer(Position currentPos, Position nextPos){
       Tile currentTile = board[currentPos.y][currentPos.x];
       Tile nextTile = board[nextPos.y][nextPos.x];
       Player player = currentTile.player;
       nextTile.addPlayer(player);
       currentTile.removePlayer(player);

    }
    
    //todo implement this properly
   @Override
   public String toString() {
      StringBuilder toReturn = new StringBuilder();

      try {
         Scanner sc = new Scanner(new File("Assets/textcluedo.txt"));
         int posY=0;
         while(sc.hasNextLine()){
            Scanner line = new Scanner(sc.nextLine());
            line.useDelimiter("");
            int posX=0;
            while(line.hasNext()){
               String next = line.next();
               if(next.equals("•")){
                  toReturn.append(board[posY-1][((posX-1)/3)].toString());
                  line.next();
                  posX++;
               }else{
                  toReturn.append(next);
               }
               posX++;
            }
            toReturn.append("\n");
            posY++;
         }
      } catch(FileNotFoundException e){
         System.out.println("File error: " + e);
      }
   return toReturn.toString();
   }
}
