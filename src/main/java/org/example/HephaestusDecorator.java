package org.example;

import java.util.List;
import java.util.Map;

public class HephaestusDecorator extends PlayerBuildDecorator {


    private Position buildPosition;

    public HephaestusDecorator(){
        String godName = "Hephaestus";
        super.setGodName(godName);
        String description = "Your Worker may build one additional block (not dome) on top of your first block.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }



    @Override
    public void activePowerAfterBuild() {
        super.setActivePower(true);
    }

    
}
