package org.example;

public class AthenaDecorator extends PlayerOpponentTurnDecorator {


    public AthenaDecorator(){
        String godName = "Athena";
        super.setGodName(godName);
        String description = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
