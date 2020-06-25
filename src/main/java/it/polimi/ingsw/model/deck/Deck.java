package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.exception.InvalidNumberCardsChosenException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private static final List<CardInterface> godCards = new ArrayList<>(
            List.of(
                    new ApolloDecorator(),
                    new ArtemisDecorator(),
                    new AthenaDecorator(),
                    new AtlasDecorator(),
                    new DemeterDecorator(),
                    new HephaestusDecorator(),
                    new MinotaurDecorator(),
                    new PanDecorator(),
                    new PrometheusDecorator(),
                    new ZeusDecorator(),
                    new HestiaDecorator(),
                    new TritonDecorator(),
                    new CharonDecorator(),
                    new HeraDecorator()
            )
    );
    public final static int size = 14;
    private int playersNumber;

    public Deck() {
        this.playersNumber = 3;
    }

    public void setPlayersNumber(boolean threePlayer) {
        this.playersNumber = threePlayer ? 3 : 2;
    }

    /**
     * Check if the
     * @return true if name is correct
     * @param name is a correct god name
     * @throws NullPointerException if name is null
     */
    public static boolean isCorrectedName(String name) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("name");
        return godCards.stream().anyMatch(card -> card.getGodName().equals(name));
    }

    /**
     * Set the CardInterface
     * @param card as a card chosen by a player
     * @throws NullPointerException if card is null
     * */
    public void playerGodLikeChoose(CardInterface card) throws NullPointerException {
        if (card == null)
            throw new NullPointerException("Card is null");
        card.setChosenGod(true);
    }

    /**
     * Set the cards chosen by the godlike player in the beginning of the game
     * @param gods is the list of the cards chosen
     * @throws NullPointerException if gods is null
     * @throws InvalidNumberCardsChosenException if the number of cards chosen and the number of players are different
     * @throws  WrongGodNameException if in the list given there is a god not present
     * */
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
        return godCards;
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
