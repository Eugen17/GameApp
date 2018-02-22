package mygame;

import com.jme3.app.SimpleApplication;
import state.Game;
import state.MainMenu;
import DAO.*;

public class Main extends SimpleApplication {

    public Main(){
        super(new Game());
    }
    
    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Game game = new Game();
    }
}