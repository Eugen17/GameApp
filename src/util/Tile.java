package util;

public enum Tile {
    pass, wall, unlitAltar, litAltar;
    
    @Override
    public String toString(){
        switch(this){
            case pass: return " ";
            case wall: return "0";
            case unlitAltar: return "2";
            case litAltar: return "3";
            default: return "0";
        }
    }
}