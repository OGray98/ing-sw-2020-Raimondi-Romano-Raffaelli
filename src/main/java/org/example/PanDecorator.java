package org.example;

public class PanDecorator extends PlayerWinConditionDecorator {


    public PanDecorator(){
        String godName = "Pan";
        super.setGodName(godName);
        String description = "You also win if your Worker moves down two or more levels.";
        super.setGodDescription(description);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
