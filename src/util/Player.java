package util;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import java.util.Random;

public class Player {

    private int x, y;
    private final Maze maze;
    private final Geometry geom = new Geometry("Box", new Quad(1, 1));

    public Player(Maze maze, Material mat) {
        this.maze = maze;
        x = 0;
        y = 0;
        Random rand = new Random();
        while (maze.getTile(x, y) != Tile.pass) {
            x = rand.nextInt(maze.getWidth());
            y = rand.nextInt(maze.getHeight());
        }
        geom.setMaterial(mat);
        geom.setLocalTranslation(x, y, 1);
    }

    public Geometry getGeom() {
        return geom;
    }

    public Vector3f getPosition() {
        return new Vector3f(x, y, 50);
    }
    
    public void Step(String dir) {
        switch (dir.charAt(0)) {
            case 'u':
                if(maze.getTile(x, y + 1) == Tile.unlitAltar)
                    maze.Light(x, y + 1);
                if(maze.getTile(x, y + 1) == Tile.pass)
                    y++;
                break;
            case 'r':
                if(maze.getTile(x + 1, y) == Tile.unlitAltar)
                    maze.Light(x + 1, y);
                if(maze.getTile(x + 1, y) == Tile.pass)
                    x++;
                break;
            case 'd':
                if(maze.getTile(x, y - 1) == Tile.unlitAltar)
                    maze.Light(x, y - 1);
                if(maze.getTile(x, y - 1) == Tile.pass)
                    y--;
                break;
            case 'l':
                if(maze.getTile(x - 1, y) == Tile.unlitAltar)
                    maze.Light(x - 1, y);
                if(maze.getTile(x - 1, y) == Tile.pass)
                    x--;
                break;
        }
        geom.setLocalTranslation(x, y, 1);
    }
}
