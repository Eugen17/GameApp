package util;

import static java.lang.Math.abs;

public class InputField extends Label {
    
    private boolean active;
    
    public InputField(float x, float y, float w, float h){
        super(x, y, w, h, "");
    }
    
    public void setActive(){
        active = true;
    }
    
    public void setInactive(){
        active = false;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void addChar(Character ch){
        t = t.concat(ch.toString());
        text.setText(t);
        text.setLocalTranslation(
                abs(x + w/2 - text.getFont().getLineWidth(text.getText())/2), 
                abs(y + h - 17/2), 
                1f);
    }
    
    public void backspace(){
        t = t.substring(0, t.length() == 1 ? 0 : t.length()-2);
        text.setText(t);
        System.out.println(t);
        text.setLocalTranslation(
                abs(x + w/2 - text.getFont().getLineWidth(text.getText())/2), 
                abs(y + h - 17/2), 
                1f);
    }
}
