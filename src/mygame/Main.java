package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import state.Game;

public class Main extends SimpleApplication {

    static Main app;
    public static void main(String[] args) {
        app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Game game = new Game(app);
        game.initialize(stateManager, app);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
