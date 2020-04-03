package it.polimi.ingsw.model.deck;

import java.util.ArrayList;
import java.util.List;

public class Deck  {

    private final List<GodCard> godCards;
    private GodCard[] chosenCards;
    private int NumberOfPlayers;

    public Deck(int NumberOfPlayers) {
        this.NumberOfPlayers = NumberOfPlayers;
        GodCard cardApollo = new GodCard(God.APOLLO, God.APOLLO.GetGodDescription(), PowerType.YOUR_MOVE);
        GodCard cardArtemis = new GodCard(God.ARTEMIS, God.ARTEMIS.GetGodDescription(), PowerType.YOUR_MOVE);
        GodCard cardAthena = new GodCard(God.ATHENA, God.ATHENA.GetGodDescription(), PowerType.OPPONENT_TURN);
        GodCard cardAtlas = new GodCard(God.ATLAS, God.ATLAS.GetGodDescription(), PowerType.YOUR_BUILD);
        GodCard cardDemeter = new GodCard(God.DEMETER, God.DEMETER.GetGodDescription(), PowerType.YOUR_BUILD);
        GodCard cardHephaestus = new GodCard(God.HEPHAESTUS, God.HEPHAESTUS.GetGodDescription(), PowerType.YOUR_BUILD);
        GodCard cardMinotaur = new GodCard(God.MINOTAUR, God.MINOTAUR.GetGodDescription(), PowerType.YOUR_MOVE);
        GodCard cardPan = new GodCard(God.PAN, God.PAN.GetGodDescription(), PowerType.WIN_CONDITION);
        GodCard cardPrometheus = new GodCard(God.PROMETHEUS, God.PROMETHEUS.GetGodDescription(), PowerType.YOUR_TURN);
        godCards = new ArrayList<>(List.of(cardApollo, cardArtemis, cardAthena, cardAtlas, cardDemeter, cardHephaestus, cardMinotaur, cardPan, cardPrometheus));
        chosenCards = new GodCard[NumberOfPlayers];
    }


    public void PlayerGodLikeChoose(GodCard Card){
        if(Card==null) throw new NullPointerException("Card can't be null");
        Card.setisChoose(true);
    }

    

    public void showCards(){
        String out;
        for (GodCard godCard : godCards) {
            out = godCard.toString();
            System.out.println(out);
        }
    }

    public void showChosenCards(){
        String out;
        for (GodCard chosenCard : chosenCards) {
            out = chosenCard.toString();
            System.out.println(out);
        }
    }

    public void setChosenCards() throws NullPointerException, ArrayIndexOutOfBoundsException {
        int count=0;
        if(godCards.size()==0) throw new NullPointerException();

            for (int i = 0; i < godCards.size(); i++) {
                if (godCards.get(i).isSelected() && count <= NumberOfPlayers) {
                    chosenCards[count] = godCards.get(i);
                    count++;
                }
                if (i >= godCards.size() || count > chosenCards.length || count < 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }

    }

    public GodCard[] getChosenCards(){
        return chosenCards;
    }
}
