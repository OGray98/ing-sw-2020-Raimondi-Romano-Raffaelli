package org.example;

public class DemeterDecorator extends PlayerBuildDecorator {


    public DemeterDecorator(){
        String godName = "Demeter";
        super.setGodName(godName);
        String description = "Your Worker may build one additional time, but not on the same space.";
        super.setGodDescription(description);
    }

    //@Override
    //public

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
