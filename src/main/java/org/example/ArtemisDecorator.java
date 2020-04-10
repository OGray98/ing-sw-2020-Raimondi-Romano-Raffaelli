package org.example;

public class ArtemisDecorator extends PlayerMoveDecorator {


    public ArtemisDecorator(){
        String godName = "Artemis";
        super.setGodName(godName);
        String description = "Your Worker may move one additional time, but not back to its initial space.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
