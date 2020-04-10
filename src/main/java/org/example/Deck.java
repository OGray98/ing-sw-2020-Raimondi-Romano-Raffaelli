package org.example;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<CardInterface> godCards;
    private CardInterface[] chosenGodCards;
    private int playersNumber;

    public Deck(int playersNumber){
        this.playersNumber = playersNumber;
        CardInterface cardApollo = new ApolloDecorator();
        CardInterface cardArtemis = new ArtemisDecorator();
        CardInterface cardAthena = new AthenaDecorator();
        CardInterface cardAtlas = new AtlasDecorator();
        CardInterface cardDemeter = new DemeterDecorator();
        CardInterface cardHephaestus = new HephaestusDecorator();
        CardInterface cardMinotaur = new MinotaurDecorator();
        CardInterface cardPan = new PanDecorator();
        CardInterface cardPrometheus = new PrometheusDecorator();
        godCards = new ArrayList<>(List.of(cardApollo,cardArtemis,cardAthena,cardAtlas,cardDemeter,cardHephaestus,cardMinotaur,cardPan,cardPrometheus));
        chosenGodCards = new CardInterface[this.playersNumber];
    }

    public void playerGodLikeChoose(CardInterface card) throws NullPointerException{
        if(card == null)
            throw new NullPointerException("Card is null");
        card.setChosenGod(true);
    }

    public void setChosenGodCards() throws NullPointerException, ArrayIndexOutOfBoundsException{
        int count = 0;
        if(godCards.size() == 0)
            throw new NullPointerException("Deck is empty");

        for(int i = 0;i < godCards.size();i++){
            if(godCards.get(i).getBoolChosenGod() && count < this.playersNumber){
                chosenGodCards[count] = godCards.get(i);
                count++;
            }
            if( i >= godCards.size() || count > chosenGodCards.length || count < 0)
                throw new ArrayIndexOutOfBoundsException();
        }
    }





}
