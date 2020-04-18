package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.exception.InvalidNumberCardsChosenException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private final List<CardInterface> godCards;
    public final static int size = 9;
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
        godCards = new ArrayList<>(List.of(cardApollo, cardArtemis, cardAthena, cardAtlas, cardDemeter, cardHephaestus, cardMinotaur, cardPan, cardPrometheus));
    }

    public void playerGodLikeChoose(CardInterface card) throws NullPointerException {
        if (card == null)
            throw new NullPointerException("Card is null");
        card.setChosenGod(true);
    }

    public void setChosenGodCards(List<String> gods) throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (gods == null)
            throw new NullPointerException("gods");
        if (gods.size() != playersNumber)
            throw new InvalidNumberCardsChosenException(playersNumber, gods.size());

        for (String name : gods) {
            boolean thereIs = false;
            for (CardInterface god : godCards) {
                if (name.equals(god.getGodName())){
                    god.setChosenGod(true);
                    thereIs = true;
                }
            }
            if (!thereIs)
                throw new WrongGodNameException(name);
        }
    }

    public List<CardInterface> getGodCards() {
        return this.godCards;
    }

    public CardInterface getGodCard(String name) throws NullPointerException, WrongGodNameException {
        if (name == null)
            throw new NullPointerException("name");
        for (CardInterface card : godCards) {
            if (card.getGodName().equals(name))
                return card;
        }
        throw new WrongGodNameException(name);
    }

    public List<CardInterface> getChosenGodCards() {
        return godCards.stream()
                .filter(CardInterface::getBoolChosenGod)
                .collect(Collectors.toList());
    }

}
