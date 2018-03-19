package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import state.MainMenu;

public class Main extends SimpleApplication {

    public final static Main app = new Main();
    public AppSettings appSettings;
    
    public Main(){
        super(new MainMenu());
    }
    
    public static void main(String[] args) {        
        app.setShowSettings(false);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        appSettings = this.settings;
        MainMenu game = new MainMenu();
    }
}