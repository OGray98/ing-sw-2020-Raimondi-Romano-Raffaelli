package org.example;

public class MinotaurDecorator extends PlayerMoveDecorator {


    public MinotaurDecorator(){
        String godName = "Minotaur";
        super.setGodName(godName);
        String description = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level";
        super.setGodDescription(description);
    }


    @Override
    public int getPowerListDimension(){
        return 2;
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


}
