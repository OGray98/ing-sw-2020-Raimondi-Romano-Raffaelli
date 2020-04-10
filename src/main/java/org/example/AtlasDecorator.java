package org.example;

public class AtlasDecorator extends PlayerBuildDecorator {


    public AtlasDecorator(){
        String godName = "Atlas";
        super.setGodName(godName);
        String description = "Your Worker may build a dome at any level.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
