package it.polimi.ingsw;


// Enum God with descriptions of their powers
public enum God {

    APOLLO("Your Worker may move into an opponent Worker’s space by forcing their Worker to\n" +
            "the space yours just vacated"),
     ARTEMIS("Your Worker may move one additional time, but not back to its initial space"),
     ATHENA("If one of your Workers moved up on your last turn, opponent Workers cannot\n" +
             "move up this turn"),
    ATLAS("Your Worker may build a dome at any level"),
    DEMETER("Your Worker may build one additional time, but not on the same space"),
    HEPHAESTUS("Your Worker may build one additional block (not dome) on top of your first block"),
    MINOTAUR("Your Worker may move into an opponent Worker’s space, if their Worker can be\n" +
            "forced one space straight backwards to an unoccupied space at any level"),
    PAN("You also win if your Worker moves down two or more levels"),
    PROMETHEUS("If your Worker does not move up, it may build both before and after moving");

    private String Description;

    God(String Description){
        this.Description = Description;
    }

    public String GetGodDescription(){
        return this.Description;
    }
}
