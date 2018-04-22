package util;

import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import static java.lang.Math.abs;
import mygame.Main;

public class GenericTab {
    protected final float x, y, w, h;
    protected final Quad quad;
    protected final Geometry geom;
    protected final BitmapText text;
    public Material basic, hover, click, current;
    
    public GenericTab(float x, float y, float w, float h, String t){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        text = new BitmapText(Main.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        text.setText(t);
        text.setColor(ColorRGBA.Black);
        text.setSize(17);
        text.setLocalTranslation(
                abs(x + w/2 - text.getFont().getLineWidth(t)/2), 
                abs(y + h - 17/2), 
                1f);
        quad = new Quad(w, h);
        geom = new Geometry(text.getText(), quad);
        geom.setLocalTranslation(abs(x), abs(y), 0);
        
        basic = new Material(Main.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        basic.setColor("Color", ColorRGBA.White);
        hover = new Material(Main.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        hover.setColor("Color", ColorRGBA.LightGray);
        click = new Material(Main.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        click.setColor("Color", ColorRGBA.Gray);
        current = basic;
        geom.setMaterial(current);
    }
    
    public void initialize(Node node){
        node.attachChild(geom);
        node.attachChild(text);
    }
    
    public void cleanup(Node node){
        node.detachChild(geom);
        node.detachChild(text);
    }
    
    public boolean isHovered(Vector2f vec){
        return ((vec.x > this.x) && (vec.x < this.x + w) && (vec.y > this.y) && (vec.y < this.y + h));
    }
}
