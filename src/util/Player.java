package util;

public class Player {
   private int x, y;
   private final Maze maze;
   
   public Player(int x, int y, Maze maze){
       this.x = x;
       this.y = y;
       this.maze = maze;
   }
   
   private boolean isPass(String dir, Maze maze){
       switch(dir.charAt(0)){
           case 'u': return maze.getTile(x, y-1) == Tile.pass;
           case 'r': return maze.getTile(x+1, y) == Tile.pass;
           case 'd': return maze.getTile(x, y+1) == Tile.pass;
           case 'l': return maze.getTile(x-1, y) == Tile.pass;
           default: return false;
       }
   }
   
   private boolean isAltar(String dir, Maze maze){
       switch(dir.charAt(0)){
           case 'u': return maze.getTile(x, y-1) == Tile.unlitAltar;
           case 'r': return maze.getTile(x+1, y) == Tile.unlitAltar;
           case 'd': return maze.getTile(x, y+1) == Tile.unlitAltar;
           case 'l': return maze.getTile(x-1, y) == Tile.unlitAltar;
           default: return false;
       }
   }
   
   public void Step(String dir){
       if(isPass(dir, maze))
           switch(dir.charAt(0)){
               case 'u': y--;
               case 'r': x++;
               case 'd': y++;
               case 'l': x--;
           }
       if(isAltar(dir, maze))
           switch(dir.charAt(0)){
               case 'u': maze.Light(x, y-1);
               case 'r': maze.Light(x+1, y);
               case 'd': maze.Light(x, y+1);
               case 'l': maze.Light(x-1, y);
           }
   }
}
