package util;

public enum Tile {
    pass, wall, unlitAltar, litAltar;
    
    @Override
    public String toString(){
        switch(this){
            case pass: return " ";
            case wall: return "0";
            case unlitAltar: return "O";
            case litAltar: return "@";
            default: return "!";
        }
    }
}