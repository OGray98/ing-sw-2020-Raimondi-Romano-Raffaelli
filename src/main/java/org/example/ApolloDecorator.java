package org.example;

public class ApolloDecorator extends PlayerMoveDecorator{


    public ApolloDecorator(){
        String godName = "Apollo";
        super.setGodName(godName);
        String description = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
