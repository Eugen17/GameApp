package util;

import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import java.util.function.Consumer; 

public class Button {
    private final float x, y, w, h;
    private Material basic, hover, click, current;
    private final Quad quad;
    private final Geometry geom;
    private final BitmapText text;
    public Consumer<Integer> foo;
   
    public Button(float x, float y, float w, float h, String t, BitmapText text, Consumer<Integer> foo){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.text.setText(t);
        this.text.setColor(ColorRGBA.Green);
        this.text.setSize(0.3f);
        this.text.setLocalTranslation(x, y+h, 1.5f);
        quad = new Quad(w, h);
        geom = new Geometry(text.getText(), quad);
        geom.setLocalTranslation(x, y, 1);
    }
    
    public void setMaterial(Material basic, Material hover, Material click){
        this.basic = basic;
        this.hover = hover;
        this.click = click;
        current = basic;
        geom.setMaterial(current);
    }
    
    public void initilize(Node node){
        node.attachChild(geom);
        //node.attachChild(text);
    }
    
    public boolean isHovered(int x, int y){
        return ((x > this.x) && (x < this.x + w) && (y > this.y) && (x < this.y + h));
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
