package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class DeckTest {

    private static Deck deckTwoPlayers;
    private static Deck deckThreePlayers;
    private static Deck deckEmpty;

    @Before
    public void init(){
        deckTwoPlayers = new Deck(2);
        deckThreePlayers = new Deck(3);
        deckEmpty = new Deck(2);

    }


    @Test
    public void playerGodLikeChooseTest(){
        try{
            deckTwoPlayers.playerGodLikeChoose(null);
        }catch (NullPointerException e){
            assertEquals("Card is null",e.getMessage());
        }

        try{
            deckThreePlayers.playerGodLikeChoose(null);
        }catch (NullPointerException e){
            assertEquals("Card is null",e.getMessage());
        }

        for(CardInterface c : deckTwoPlayers.getGodCards()){
            deckTwoPlayers.playerGodLikeChoose(c);
            assertEquals(true,c.getBoolChosenGod());
        }
        for(CardInterface c : deckThreePlayers.getGodCards()){
            deckThreePlayers.playerGodLikeChoose(c);
            assertEquals(true,c.getBoolChosenGod());
        }
    }

    @Test
    public void setChosenCardTest(){
        // remove all cards in a deck to verify NullPointerException(deck.size == 0)
        List<String> notGod = new ArrayList<>();
        int twoPlayers = 2;
        try{
            deckEmpty.setChosenGodCards(null);
        }catch(NullPointerException e){
            assertEquals("gods",e.getMessage());
        }
        try{
            deckEmpty.setChosenGodCards(notGod);
        }catch(InvalidNumberCardsChosenException e){
            assertEquals("There are " + twoPlayers + " players, you chose " + notGod.size() + "cards",e.getMessage());
        }

        List<String> threeGodsOneWrong = new ArrayList<>(3);
        threeGodsOneWrong.add("Apollo");
        threeGodsOneWrong.add("Artemis");
        threeGodsOneWrong.add("Atheena");

        try {
            deckThreePlayers.setChosenGodCards(threeGodsOneWrong);
        }catch (WrongGodNameException e){
            assertEquals("There isn't a god named " + threeGodsOneWrong.get(2),e.getMessage());
        }

        List<String> threeGods = new ArrayList<>(3);
        threeGods.add("Apollo");
        threeGods.add("Artemis");
        threeGods.add("Athena");
        deckThreePlayers.setChosenGodCards(threeGods);
        assertTrue(deckThreePlayers.getGodCard("Apollo").getBoolChosenGod());
        assertTrue(deckThreePlayers.getGodCard("Artemis").getBoolChosenGod());
        assertTrue(deckThreePlayers.getGodCard("Athena").getBoolChosenGod());

    }

    @Test
    public void getGodCardTest(){
        try {
            deckThreePlayers.getGodCard(null);
        }catch(NullPointerException e){
            assertEquals("name",e.getMessage());
        }
        try{
            deckThreePlayers.getGodCard("Appollo");
        } catch (WrongGodNameException e){
            assertEquals("There isn't a god named " + "Appollo" , e.getMessage());
        }
        assertEquals("Apollo",deckThreePlayers.getGodCard("Apollo").getGodName());
        assertEquals("Artemis",deckThreePlayers.getGodCard("Artemis").getGodName());
        assertEquals("Athena",deckThreePlayers.getGodCard("Athena").getGodName());
        assertEquals("Minotaur",deckThreePlayers.getGodCard("Minotaur").getGodName());
        assertEquals("Demeter",deckThreePlayers.getGodCard("Demeter").getGodName());
        assertEquals("Pan",deckThreePlayers.getGodCard("Pan").getGodName());
        assertEquals("Prometheus",deckThreePlayers.getGodCard("Prometheus").getGodName());
        assertEquals("Atlas",deckThreePlayers.getGodCard("Atlas").getGodName());

    }





}