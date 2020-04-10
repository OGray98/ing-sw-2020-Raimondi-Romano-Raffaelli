package org.example;

public class PrometheusDecorator extends PlayerYourTurnDecorator {


    public PrometheusDecorator(){
        String godName = "Prometheus";
        super.setGodName(godName);
        String description = "If your Worker does not move up, it may build both before and after moving.";
        super.setGodDescription(description);
    }




    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
