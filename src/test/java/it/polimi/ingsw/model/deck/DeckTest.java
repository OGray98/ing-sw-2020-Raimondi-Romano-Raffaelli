package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.deck.God;
import it.polimi.ingsw.model.deck.GodCard;
import it.polimi.ingsw.model.deck.PowerType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeckTest {

    private List<GodCard> godCards;
    private GodCard[] chosenCards;
    private GodCard cardApolloTest;
    private GodCard cardArtemisTest;
    private GodCard cardAthenaTest;
    private GodCard cardAtlasTest;
    private GodCard cardDemeterTest;
    private GodCard cardHephaestusTest;
    private GodCard cardMinotaurTest;
    private GodCard cardPanTest;
    private GodCard cardPrometheusTest;
    private Deck TwoPlayer;
    private Deck ThreePlayer;



    @Before
    public void setUp() throws Exception {

         TwoPlayer = new Deck(2);
         ThreePlayer = new Deck(3); // creato per verificare chosenCard però prima vedere dove va implementato la fase di setup delle carte scelte dal godlike
         cardApolloTest = new GodCard(God.APOLLO, God.APOLLO.GetGodDescription(), PowerType.YOUR_MOVE);
         cardArtemisTest = new GodCard(God.ARTEMIS, God.ARTEMIS.GetGodDescription(), PowerType.YOUR_MOVE);
         cardAthenaTest = new GodCard(God.ATHENA, God.ATHENA.GetGodDescription(), PowerType.OPPONENT_TURN);
         cardAtlasTest = new GodCard(God.ATLAS, God.ATLAS.GetGodDescription(), PowerType.YOUR_BUILD);
         cardDemeterTest = new GodCard(God.DEMETER, God.DEMETER.GetGodDescription(), PowerType.YOUR_BUILD);
         cardHephaestusTest = new GodCard(God.HEPHAESTUS, God.HEPHAESTUS.GetGodDescription(), PowerType.YOUR_BUILD);
         cardMinotaurTest = new GodCard(God.MINOTAUR, God.MINOTAUR.GetGodDescription(), PowerType.YOUR_MOVE);
         cardPanTest = new GodCard(God.PAN, God.PAN.GetGodDescription(), PowerType.WIN_CONDITION);
         cardPrometheusTest = new GodCard(God.PROMETHEUS, God.PROMETHEUS.GetGodDescription(), PowerType.YOUR_TURN);
         godCards = new ArrayList<>(List.of(cardApolloTest, cardArtemisTest, cardAthenaTest, cardAtlasTest, cardDemeterTest, cardHephaestusTest, cardMinotaurTest, cardPanTest, cardPrometheusTest));

    }

    @Test
    public void PlayerGodLikeChooseTest() { // verify that playergodlikechoose card are changed into true
        for(GodCard c : godCards){
            TwoPlayer.PlayerGodLikeChoose(c);
            assertTrue(c.isSelected());
        }

    }

    @Test
    public void SetChosenCardTwoPlayersTest() {

       // Random rand = new Random();
        chosenCards = new GodCard[2];
        //int firstGod = rand.nextInt(8);
       // int secondGod = rand.nextInt(8);
        TwoPlayer.setChosenCards();
        assertEquals(2,chosenCards.length);

    }

    @Test
    public void SetChosenCardThreePlayerTest() {

        //Random rand = new Random();
        chosenCards = new GodCard[3];
        //int firstGod = rand.nextInt(8);
        //int secondGod = rand.nextInt(8);
        //int thirdGod = rand.nextInt(8);
        ThreePlayer.setChosenCards();
        assertEquals(3,chosenCards.length);

    }
}