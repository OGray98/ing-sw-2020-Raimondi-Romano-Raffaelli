package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HeraDecoratorTest {

    private static Deck deck;
    CardInterface cardHera;
    PlayerInterface playerHera;

    @Before
    public void init() {
        deck = new Deck();
        cardHera = deck.getGodCard("Hera");
        playerHera = cardHera.setPlayer(new Player("jack", PlayerIndex.PLAYER0));

    }

    @Test
    public void setChosenGodTest() {
        cardHera.setChosenGod(true);
        assertTrue(cardHera.getBoolChosenGod());
    }
}
