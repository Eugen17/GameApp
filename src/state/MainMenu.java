package state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.function.Consumer;
import util.Button;

public class MainMenu extends AbstractAppState {
    
    private Node rootNode;
    private final Node localNode = new Node("Main Menu");
    private final ArrayList<Button> buttons = new ArrayList<>();
    
    private enum Actions {up, down, left, right, click};
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        
        rootNode = ((SimpleApplication)app).getRootNode();
        rootNode.attachChild(localNode);
        
        Material basic = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        basic.setColor("Color", ColorRGBA.White);

        Material hover = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        hover.setColor("Color", ColorRGBA.LightGray);

        Material click = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        click.setColor("Color", ColorRGBA.DarkGray);

        buttons.add(new Button(-2, 1, 4, 0.8f, "Press me!", 
            new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Console.fnt")),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                System.out.println("1");
            }
            }));
        buttons.get(buttons.size()-1).setMaterial(basic, hover, click);
        buttons.get(buttons.size()-1).initilize(localNode);
        
        buttons.add(new Button(-2, 0, 4, 0.8f, "Press me!", 
            new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Console.fnt")),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                System.out.println("2");
            }
            }));
        buttons.get(buttons.size()-1).setMaterial(basic, hover, click);
        buttons.get(buttons.size()-1).initilize(localNode);
        
        buttons.add(new Button(-2, -1, 4, 0.8f, "Press me!", 
            new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Console.fnt")),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                System.out.println("3");
            }
            }));
        buttons.get(buttons.size()-1).setMaterial(basic, hover, click);
        buttons.get(buttons.size()-1).initilize(localNode);
        
        app.getInputManager().clearMappings();
        app.getInputManager().addMapping(Actions.up.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        app.getInputManager().addMapping(Actions.down.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        app.getInputManager().addMapping(Actions.right.name(), new MouseAxisTrigger(MouseInput.AXIS_X, false));
        app.getInputManager().addMapping(Actions.left.name(), new MouseAxisTrigger(MouseInput.AXIS_X, true));
        app.getInputManager().addMapping(Actions.click.name(), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        app.getInputManager().addListener(analogListener, Actions.up.name());
        app.getInputManager().addListener(analogListener, Actions.down.name());
        app.getInputManager().addListener(analogListener, Actions.left.name());
        app.getInputManager().addListener(analogListener, Actions.right.name());
        app.getInputManager().addListener(actionListener, Actions.click.name());
    }
    
    @Override
    public void cleanup(){
        rootNode.detachChild(localNode);
        
        super.cleanup();
    }
    
    @Override
    public void update(float dt){
        
    }
    
    private final ActionListener actionListener = new ActionListener(){
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener(){
        @Override
        public void onAnalog(String name, float value, float tpf) {
            
        }
    };
}
