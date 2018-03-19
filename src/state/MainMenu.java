package state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.renderer.RenderManager;
import java.util.ArrayList;
import java.util.function.Consumer;
import util.Button;
import mygame.Main;

public class MainMenu extends AbstractAppState {
    
    private final ArrayList<Button> buttons = new ArrayList<>();
    
    private enum Actions {up, down, left, right, click};
    private boolean isPressed = false;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        
        int width = Main.app.appSettings.getWidth(), height = Main.app.appSettings.getHeight();
        
        Material basic = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        basic.setColor("Color", ColorRGBA.White);

        Material hover = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        hover.setColor("Color", ColorRGBA.LightGray);

        Material click = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        click.setColor("Color", ColorRGBA.Gray);
        
        buttons.add(new Button(width/3, height*0.65f, width/3, height*0.08f, "Start game", 
            app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                cleanup();
                Main.app.getStateManager().attach(new Game());
            }
            }));
        
        buttons.add(new Button(width/3, height*0.55f, width/3, height*0.08f, "Results", 
            app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                
            }
            }));
        
        buttons.add(new Button(width/3, height*0.45f, width/3, height*0.08f, "Quit", 
            app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"),
            new Consumer<Integer>(){
            @Override
            public void accept(Integer t) {
                Main.app.stop();
            }
            }));
        
        for (Button button : buttons) {
            button.setMaterial(basic, hover, click);
            button.initialize(Main.app.getGuiNode());
        }
        
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
        for (Button button : buttons) 
            button.cleanup(Main.app.getGuiNode());
            
        super.cleanup();
    }
    
    @Override
    public void render(RenderManager rm){
        
    }
    
    @Override
    public void update(float dt){
        for (Button b : buttons)
            b.updateMaterial();
    }
    
    private final ActionListener actionListener = new ActionListener(){
        @Override
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals(Actions.click.name()))
                isPressed = pressed;
            for (Button b : buttons)
                if (b.isHovered(Main.app.getInputManager().getCursorPosition()) && !pressed)
                    b.Act();
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener(){
        @Override
        public void onAnalog(String name, float value, float tpf) {
            for (Button b : buttons){
                if (b.isHovered(Main.app.getInputManager().getCursorPosition()))
                    if (isPressed){
                        b.current = b.click;
                        b.updateMaterial();
                    } else
                        b.current = b.hover;
                else 
                    b.current = b.basic;
            }
        }
    };
}
