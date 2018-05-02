package util;

import java.util.function.Consumer; 

public class Button extends Label{
    public final Consumer<Integer> foo;
   
    public Button(float x, float y, float w, float h, String t, Consumer<Integer> foo){
        super(x, y, w, h, t);
        
        this.foo = foo;
    }
    
    public void Act(){
        foo.accept(0);
    }
}
