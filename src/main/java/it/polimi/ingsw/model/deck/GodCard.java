package it.polimi.ingsw.model.deck;

// Class representing the attributes of godcard.
public class GodCard {

    private God godName;
    private String powerDescription;
    private PowerType powerType;
    private boolean isChoose;

// manca metodo per inserire un'eventuale immagine che rappresenti la carta della divinità

    public GodCard(God godName,String powerDescription,PowerType powertype){
        this.godName = godName;
        this.powerDescription = powerDescription;
        this.powerType = powertype;
        isChoose = false;

    }
    public void setisChoose(boolean choose){
        this.isChoose = choose;
    }

    public boolean isSelected(){
        return this.isChoose;
    }

    public God getName(){
        return  this.godName;
    }

    public String getPowerDescription(){
        return this.powerDescription;
    }

    public PowerType getPowerType(){
        return this.powerType;
    }

    public String toString(){
        return "GodCard { " +
                "God = " + godName +
                "PowerDescription = " + powerDescription +
                "PowerType = " + powerType +
                "}" ;
    }

}
