package state;

import DAO.DAOFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import util.Button;
import mygame.Main;
import util.Label;
import util.InputField;

public class MainMenu extends AbstractAppState {

    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<InputField> inputs = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();

    private enum Actions {
        up, down, left, right, click
    };
    private final HashMap<String, Label> elements = new HashMap<>();

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        new DAOFactory();

        int width = Main.app.appSettings.getWidth(), height = Main.app.appSettings.getHeight();

        elements.put("Start", new Button(width / 3, height * 0.65f, width / 3, height * 0.08f, "Start game",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                cleanup();
                Main.app.getStateManager().detach(Main.app.getStateManager().getState(MainMenu.class));
                Main.app.getStateManager().attach(new Game());
            }
        }));

        elements.put("Results", new Button(width / 3, height * 0.55f, width / 3, height * 0.08f, "Results",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {

            }
        }));

        elements.put("Quit", new Button(width / 3, height * 0.45f, width / 3, height * 0.08f, "Quit",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                Main.app.stop();
            }
        }));
        
        elements.put("Submit", new Button(width / 3, height * 0.35f, width / 3, height * 0.08f, "Submit",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                MenuInit();
            }
        }));

        elements.put("New user", new Button(5 * width / 6, height * 0.15f, width / 6, height * 0.08f, "New user",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                NewInit();
            }
        }));
        
        elements.put("Create", new Button(width / 3, height * 0.35f, width / 3, height * 0.08f, "Create",
                new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                MenuInit();
            }
        }));
        
        elements.put("Username", new InputField(width / 2, height * 0.55f, width / 3, height * 0.08f));
        elements.put("Password", new InputField(width / 2, height * 0.45f, width / 3, height * 0.08f));
        elements.put("Username label", new Label(width / 6, height * 0.55f, width / 3, height * 0.08f, "Username"));
        elements.put("Password label", new Label(width / 6, height * 0.45f, width / 3, height * 0.08f, "Password"));
        
        LoginInit();

        app.getInputManager().clearMappings();
        app.getInputManager().clearRawInputListeners();
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
        app.getInputManager().addRawInputListener(rawInput);
    }

    @Override
    public void cleanup() {
        for (Button b : buttons)
            b.cleanup(Main.app.getGuiNode());
        for (InputField f : inputs)
            f.cleanup(Main.app.getGuiNode());
        for (Label l : labels)
            l.cleanup(Main.app.getGuiNode());
        
        super.cleanup();
    }

    @Override
    public void update(float dt) {
        for (Button b : buttons) {
            b.updateMaterial();
        }
        for (InputField f : inputs) {
            f.updateMaterial();
        }
        
        for (Button b : buttons) {
                if (b.isHovered(Main.app.getInputManager().getCursorPosition())) {
                    b.current = b.hover;
                } else {
                    b.current = b.basic;
                }
            }

            for (InputField f : inputs) {
                if (f.isHovered(Main.app.getInputManager().getCursorPosition())) {
                    f.current = f.hover;
                } else if (!f.isActive()) {
                    f.current = f.basic;
                }
            }for (Button b : buttons) {
            if (b.isHovered(Main.app.getInputManager().getCursorPosition())) {
                b.current = b.hover;
            } else {
                b.current = b.basic;
            }
        }

        for (InputField f : inputs) {
            if (f.isHovered(Main.app.getInputManager().getCursorPosition())) {
                f.current = f.hover;
            } else if (!f.isActive()) {
                f.current = f.basic;
            }
        }
    }
    
    private void preInit(){
        for (Button b : buttons) {
            b.cleanup(Main.app.getGuiNode());
        }
        for (InputField b : inputs) {
            b.cleanup(Main.app.getGuiNode());
        }
        for (Label b : labels){
            b.cleanup(Main.app.getGuiNode());
        }
        buttons.clear();
        inputs.clear();
        labels.clear();
    }
    
    private void postInit(){
        for (Button b : buttons) {
            b.initialize(Main.app.getGuiNode());
        }
        for (InputField b : inputs) {
            b.initialize(Main.app.getGuiNode());
        }
        for (Label b : labels){
            b.initialize(Main.app.getGuiNode());
        }
    }

    private void MenuInit() {
        preInit();
        buttons.add((Button) elements.get("Start"));
        buttons.add((Button) elements.get("Results"));
        buttons.add((Button) elements.get("Quit"));
        postInit();
    }

    private void LoginInit() {
        preInit();
        buttons.add((Button) elements.get("Submit"));
        buttons.add((Button) elements.get("New user"));
        labels.add((Label) elements.get("Username label"));
        labels.add((Label) elements.get("Password label"));
        inputs.add((InputField) elements.get("Username"));
        inputs.add((InputField) elements.get("Password"));
        postInit();
    }
    
    private void NewInit() {
        preInit();
        buttons.add((Button) elements.get("Create"));
        labels.add((Label) elements.get("Username label"));
        labels.add((Label) elements.get("Password label"));
        inputs.add((InputField) elements.get("Username"));
        inputs.add((InputField) elements.get("Password"));
        postInit();
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean pressed, float tpf) {
            try{
            for (Button b : buttons) {
                if (b.isHovered(Main.app.getInputManager().getCursorPosition()) && !pressed) {
                    b.Act();
                    b.current = b.click;
                    b.updateMaterial();
                }
            }
            for (InputField f : inputs) {
                if (!f.isHovered(Main.app.getInputManager().getCursorPosition())) {
                    f.setInactive();
                } else if (!pressed) {
                    f.setActive();
                    f.current = f.click;
                    f.updateMaterial();
                }
            }
            } catch (Exception e){
                System.out.println(e);
            }
        }
    };

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            
        }
    };

    private final RawInputListener rawInput = new RawInputListener() {
        @Override
        public void beginInput() {
        }

        @Override
        public void endInput() {
        }

        @Override
        public void onKeyEvent(KeyInputEvent evt) {
            if (evt.isPressed()) {
                System.out.println(evt.getKeyCode());
                for (InputField i : inputs) {
                    if (i.isActive()) {
                        i.addChar(evt.getKeyChar());
                        if (evt.getKeyCode() == 14) {
                            i.backspace();
                        }
                    }
                }
            }
        }

        @Override
        public void onJoyAxisEvent(JoyAxisEvent evt) {
        }

        @Override
        public void onJoyButtonEvent(JoyButtonEvent evt) {
        }

        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
        }

        @Override
        public void onMouseButtonEvent(MouseButtonEvent evt) {
        }

        @Override
        public void onTouchEvent(TouchEvent evt) {
        }
    };
}
