package it.polimi.ingsw;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Deck  {

    private List<GodCard> godCards;
    private GodCard[] chosenCards;
    private int NumberOfPlayers;

    public Deck(int NumberOfPlayers){
        this.NumberOfPlayers=NumberOfPlayers;
        godCards = new ArrayList<GodCard>();
        chosenCards = new GodCard[NumberOfPlayers];
    }

    public void AddCard(GodCard Card){
        if(Card==null) throw new NullPointerException("Card can't be null");
        godCards.add(Card);
    }

    public void PlayerGodLikeChoose(GodCard Card){
        if(Card==null) throw new NullPointerException("Card can't be null");
        Card.setisChoose(true);
    }

    public void showCards(){
        String out;
        for(int i=0;i<godCards.size();i++){
            out = godCards.get(i).toString();
            System.out.println(out);
        }
    }

    public void showChosenCards(){
        String out;
        for(int i=0;i<chosenCards.length;i++){
            out = chosenCards[i].toString();
            System.out.println(out);
        }
    }

    public void setChosenCards() throws NullPointerException, ArrayIndexOutOfBoundsException {
        int count=0;
        if(godCards.size()==0) throw new NullPointerException();
        for(int i=0;i<godCards.size();i++){
            if(godCards.get(i).isSelected() && count <= NumberOfPlayers){
                chosenCards[count] = godCards.get(i);
                count++;
            }
            if(i >= godCards.size() || count > chosenCards.length || i < 0 || count < 0){
                throw new ArrayIndexOutOfBoundsException();
            }
        }

    }
}
