package state;

import DAO.*;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
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
import com.jme3.font.BitmapText;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import java.util.Date;
import java.util.EnumMap;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import mygame.Main;

public class Game extends AbstractAppState {

    private final Node localNode = new Node("Game");
    private Camera cam;
    private final EnumMap<Actions, Boolean> actions = new EnumMap<>(Actions.class);
    private final Maze maze = new Maze(31, 31);
    private Player player;
    public final static Session session = new Session("");
    private final BitmapText pauseText = 
            new BitmapText(Main.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
    
    private enum Actions {
        up, right, down, left, escape, cheat
    };
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
       
        actions.put(Actions.up, false);
        actions.put(Actions.down, false);
        actions.put(Actions.right, false);
        actions.put(Actions.left, false);
        actions.put(Actions.escape, false);
        actions.put(Actions.cheat, false);
       
        cam = app.getCamera();
        mygame.Main.app.getRootNode().attachChild(localNode);
        

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
        app.getInputManager().addMapping(Actions.cheat.name(), new KeyTrigger(KeyInput.KEY_O));
        app.getInputManager().addListener(actionListener, 
                new String[]{Actions.left.name(), Actions.up.name(), Actions.right.name(), 
                Actions.down.name(), Actions.escape.name(), Actions.cheat.name()});
        
        session.setType("solo");
        session.setDuration(new Date(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).getTime());
        
        pauseText.setText("Pause");
        pauseText.setColor(ColorRGBA.Red);
        pauseText.setSize(17);
        pauseText.setLocalTranslation(
                Main.app.appSettings.getWidth()/2 - pauseText.getFont().getLineWidth("Pause")/2, 
                Main.app.appSettings.getHeight()/2 - 17/2, 
                1f);
    }

    @Override
    public void cleanup() {
        Main.app.getRootNode().detachChild(localNode);
        
        super.cleanup();
    }

    @Override
    public void update(float tpf) {
        if (!actions.get(Actions.escape)) {
            player.updateTime(tpf);
            boolean timeToGo = false;

            int dirX = 0, dirY = 0;

            if (player.isTime()) {
                if (actions.get(Actions.down)) {
                    dirY--;
                }
                if (actions.get(Actions.up)) {
                    dirY++;
                }
                if (actions.get(Actions.right)) {
                    dirX++;
                }
                if (actions.get(Actions.left)) {
                    dirX--;
                }
                timeToGo = player.Step(0, dirY);
                timeToGo = timeToGo || player.Step(dirX, 0);
                if (actions.get(Actions.cheat)) {
                    timeToGo = true;
                }
                if (actions.get(Actions.escape)) {

                }
            }
            cam.setLocation(player.getLocation());

            if (timeToGo) {
                Game.session.setDuration(
                        new Date(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).getTime()
                        - Game.session.getDuration());
                // Write your code here
                // session is set and done, just transfer to DB
                
                //SessionDAO dao = new DAOFactory().getSessionDAO();
                //dao.insertSession(session);
                
                // End your code here
                System.out.println(Game.session.getDuration());
                cleanup();
                Main.app.getStateManager().detach(this);
                Main.app.getStateManager().attach(new MainMenu());
            }
        }
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(Actions.escape.name())) {
                if (isPressed) {
                actions.put(Actions.escape, !actions.get(Actions.escape));
                if (actions.get(Actions.escape))
                    Main.app.getGuiNode().attachChild(pauseText);
                else
                    Main.app.getGuiNode().detachChild(pauseText);
                }
            }
            else
                actions.put(Actions.valueOf(name), isPressed);
        }
    };
}
