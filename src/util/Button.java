package util;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import static java.lang.Math.abs;
import java.util.function.Consumer; 

public class Button {
    private final float x, y, w, h;
    public Material basic, hover, click, current;
    private final Quad quad;
    private final Geometry geom;
    private final BitmapText text;
    public final Consumer<Integer> foo;
   
    public Button(float x, float y, float w, float h, String t, BitmapFont font, Consumer<Integer> foo){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = new BitmapText(font);
        this.foo = foo;
        this.text.setText(t);
        this.text.setColor(ColorRGBA.Black);
        this.text.setSize(17);
        this.text.setLocalTranslation(
                abs(x + w/2 - font.getLineWidth(t)/2), 
                abs(y + h - 17/2), 
                1f);
        quad = new Quad(w, h);
        geom = new Geometry(text.getText(), quad);
        geom.setLocalTranslation(abs(x), abs(y), 0);
    }
    
    public void updateMaterial(){
        geom.setMaterial(current);
    }
    
    public void setMaterial(Material basic, Material hover, Material click){
        this.basic = basic;
        this.hover = hover;
        this.click = click;
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
    
    public void Act(){
        foo.accept(0);
    }
    
    public void LostFocus(){
        current = basic;
    }
    
    public void Hover(){
        current = hover;
    }
    
    public void Click(){
        current = click;
    }
}
