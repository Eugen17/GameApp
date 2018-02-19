package state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import util.Maze;
import util.Player;
import util.Tile;

public class Game extends AbstractAppState {

    private Node rootNode;
    private final Node localNode = new Node("Game");
    private final Node pauseNode = new Node("Pause");
    private Camera cam;
    private boolean flip = false;
    private float time = 0;
    private int dirX = 0, dirY = 0;

    private enum Actions {
        up, right, down, left, escape
    };
    
    private final Maze maze = new Maze(31, 31);
    private Player player;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        rootNode = ((SimpleApplication)app).getRootNode();
        cam = app.getCamera();

        rootNode.attachChild(localNode);

        maze.Generate(20);

        Material wall = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        wall.setColor("Color", ColorRGBA.White);

        Material lit = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        lit.setColor("Color", ColorRGBA.Green);
        maze.lit = lit;
        
        Material unlit = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        unlit.setColor("Color", ColorRGBA.Red);

        Material playerM = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        playerM.setColor("Color", ColorRGBA.Cyan);

        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (maze.getTile(j, i) != Tile.pass) {
                    Quad b = new Quad(1, 1);
                    Geometry geom = new Geometry(j + " " + i, b);
                    localNode.attachChild(geom);
                    geom.setLocalTranslation(j, i, 1);
                    switch(maze.getTile(j, i)){
                        case wall: geom.setMaterial(wall); break;
                        case unlitAltar: geom.setMaterial(unlit); break;
                    }
                    maze.setGeom(j, i, geom);
                }
            }
        }

        player = new Player(maze, playerM);
        localNode.attachChild(player.getGeom());
        cam.setLocation(player.getLocation());

        app.getInputManager().clearMappings();
        app.getInputManager().addMapping(Actions.left.name(), new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping(Actions.left.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        app.getInputManager().addMapping(Actions.up.name(), new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping(Actions.up.name(), new KeyTrigger(KeyInput.KEY_UP));
        app.getInputManager().addMapping(Actions.down.name(), new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping(Actions.down.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        app.getInputManager().addMapping(Actions.right.name(), new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addMapping(Actions.right.name(), new KeyTrigger(KeyInput.KEY_RIGHT));
        app.getInputManager().addMapping(Actions.escape.name(), new KeyTrigger(KeyInput.KEY_ESCAPE));
        app.getInputManager().addListener(analogListener, Actions.left.name());
        app.getInputManager().addListener(analogListener, Actions.up.name());
        app.getInputManager().addListener(analogListener, Actions.right.name());
        app.getInputManager().addListener(analogListener, Actions.down.name());
        app.getInputManager().addListener(actionListener, Actions.escape.name());
    }

    @Override
    public void cleanup() {
        rootNode.detachChild(localNode);

        super.cleanup();
    }

    @Override
    public void update(float dt) {
        flip = !flip;
        time += dt;
        cam.setLocation(player.getLocation());
        
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (time > 0.07){
                time -= 0.07;
                if (name.equals(Actions.down.name()))
                    player.Step(0, -1);
                if (name.equals(Actions.up.name()))
                    player.Step(0, 1);
                if (name.equals(Actions.right.name()))
                    player.Step(1, 0);
                if (name.equals(Actions.left.name()))
                    player.Step(-1, 0);
            }
        }  
    };
            
}
