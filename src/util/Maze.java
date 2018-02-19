package util;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private final ArrayList<Tile> content = new ArrayList();
    public final ArrayList<Geometry> geoms = new ArrayList();
    public Material lit;
    private final int height, width, exitX, exitY;
    private boolean sealed = true;
    private int altars;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        Random rand = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                content.add(Tile.wall);
                geoms.add(new Geometry());
            }
        }
        
        switch (rand.nextInt(4)) {
            case 0: // Up
                exitX = 2*rand.nextInt(width/2-1)+1;
                exitY = 0;
                break;
            case 1: // Right
                exitX = width-1;
                exitY = 2*rand.nextInt(height/2-1)+1;
                break;
            case 2: // Down
                exitX = 2*rand.nextInt(width/2-1)+1;
                exitY = height-1;
                break;
            case 3: // Left
                exitX = 0;
                exitY = 2*rand.nextInt(height/2-1)+1;
                break;
            default: exitX = 0; exitY = 0; break;
        }
    }

    public void Generate(int altars) {
        Random rand = new Random();
        int count = 0;

        for (int k = 0; k < altars && count < 10000; k++) {
            int rWidth = rand.nextInt(2), rHeight = rand.nextInt(2);
            int x = 2 * rand.nextInt(width / 2 - rWidth - 3) + 3 + 2 * rWidth;
            int y = 2 * rand.nextInt(height / 2 - rHeight - 3) + 3 + 2 * rHeight;
            if (rWidth == 0) {
                x++;
            }
            if (rHeight == 0) {
                y++;
            }
            rWidth = 3 + 2 * rWidth;
            rHeight = 3 + 2 * rHeight;
            count++;
            boolean avaliable = true;

            for (int i = y - rHeight / 2 - 2; i <= y + rHeight / 2 + 2 && avaliable; i++) {
                for (int j = x - rWidth / 2 - 2; j <= x + rWidth / 2 + 2 && avaliable; j++) {
                    avaliable = avaliable && (content.get(i * width + j) == Tile.wall);
                }
            }

            if (!avaliable) {
                k--;
                continue;
            }

            for (int i = y - rHeight / 2; i <= y + rHeight / 2; i++) {
                for (int j = x - rWidth / 2; j <= x + rWidth / 2; j++) {
                    content.set(i * width + j, Tile.litAltar);
                }
            }
            content.set(y * width + x, Tile.unlitAltar);
            this.altars++;
            switch (rand.nextInt(4)) {
                case 0:
                    content.set((y - rHeight / 2 + 2 * rand.nextInt(rHeight / 2)) * width + x + rWidth / 2 + 1, Tile.litAltar);
                    break;
                case 1:
                    content.set((y - rHeight / 2 + 2 * rand.nextInt(rHeight / 2)) * width + x - rWidth / 2 - 1, Tile.litAltar);
                    break;
                case 2:
                    content.set((y + rHeight / 2 + 1) * width + x - rWidth / 2 + 2 * rand.nextInt(rWidth / 2), Tile.litAltar);
                    break;
                case 3:
                    content.set((y - rHeight / 2 - 1) * width + x - rWidth / 2 + 2 * rand.nextInt(rWidth / 2), Tile.litAltar);
                    break;
            }
        }

        Generate();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (content.get(i * width + j) == Tile.litAltar) {
                    content.set(i * width + j, Tile.pass);
                }
            }
        }
    }

    public void Generate() {
        Random rand = new Random();

        int x = 2 * rand.nextInt(width / 2 - 1) + 1, y = 2 * rand.nextInt(height / 2 - 1) + 1;
        while (content.get(y * width + x) != Tile.wall) {
            x = 2 * rand.nextInt(width / 2 - 1) + 1;
            y = 2 * rand.nextInt(height / 2 - 1) + 1;
        }
        int count = 0;
        content.set(y * width + x, Tile.pass);

        while (count++ % 1000 != 0 || !isGenerated()) {

            if ((y == 1 || content.get((y - 2) * width + x) != Tile.wall)
                    && (x == width - 2 || content.get(y * width + x + 2) != Tile.wall)
                    && (y == height - 2 || content.get((y + 2) * width + x) != Tile.wall)
                    && (x == 1 || content.get(y * width + x - 2) != Tile.wall)) {
                x = 2 * rand.nextInt(width / 2) + 1;
                y = 2 * rand.nextInt(height / 2) + 1;
                while (content.get(y * width + x) != Tile.pass) {
                    x = 2 * rand.nextInt(width / 2) + 1;
                    y = 2 * rand.nextInt(height / 2) + 1;

                }
            }

            switch (rand.nextInt(4)) {
                case 0: // Up
                    if (y != 1 && content.get((y - 2) * width + x) == Tile.wall) {
                        content.set((y - 1) * width + x, Tile.pass);
                        y -= 2;
                    }
                    break;
                case 1: // Right
                    if (x != width - 2 && content.get(y * width + x + 2) == Tile.wall) {
                        content.set(y * width + (x + 1), Tile.pass);
                        x += 2;
                    }
                    break;
                case 2: // Down
                    if (y != height - 2 && content.get((y + 2) * width + x) == Tile.wall) {
                        content.set((y + 1) * width + x, Tile.pass);
                        y += 2;
                    }
                    break;
                case 3: // Left
                    if (x != 1 && content.get(y * width + x - 2) == Tile.wall) {
                        content.set(y * width + (x - 1), Tile.pass);
                        x -= 2;
                    }
                    break;
            }

            content.set(y * width + x, Tile.pass);
        }
    }

    private boolean isGenerated() {
        boolean ended = true;

        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                ended = ended && (content.get(i * width + j) != Tile.wall);
            }
        }
        return ended;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y) {
        return content.get(y * width + x);
    }

    public Geometry getGeom(int x, int y) {
        return geoms.get(y * width + x);
    }

    public void setGeom(int x, int y, Geometry g) {
        geoms.set(y * width + x, g);
    }
    
    public boolean isSealed(){
        return sealed;
    }

    public void Light(int x, int y) {
        content.set(y * width + x, Tile.litAltar);
        geoms.get(y * width + x).setMaterial(lit);
        if (--altars == 0) {
            geoms.get(exitY * width + exitX).removeFromParent();
            sealed = false;
        }
    }

    public void print() {
        for (int i = height - 1; i > -1; i--) {
            for (int j = 0; j < width; j++) {
                System.out.print(content.get(i * width + j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
