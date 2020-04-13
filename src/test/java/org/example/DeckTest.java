package org.example;

import org.junit.Before;
import org.junit.Test;

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
        for(int i = 0;i<deckEmpty.getGodCards().size();i++){
            deckEmpty.getGodCards().remove(i);
        }
        try{
            deckEmpty.setChosenGodCards(null);
        }catch(NullPointerException e){
            assertEquals("gods",e.getMessage());
        }

        deckTwoPlayers.playerGodLikeChoose(deckTwoPlayers.getGodCards().get(2));
        deckTwoPlayers.playerGodLikeChoose(deckTwoPlayers.getGodCards().get(3));
        assertEquals(2,deckTwoPlayers.getChosenGodCards().length);

        deckThreePlayers.playerGodLikeChoose(deckThreePlayers.getGodCards().get(2));
        deckThreePlayers.playerGodLikeChoose(deckThreePlayers.getGodCards().get(3));
        deckThreePlayers.playerGodLikeChoose(deckThreePlayers.getGodCards().get(4));
        assertEquals(3,deckThreePlayers.getChosenGodCards().length);

        // verify that if there are more true GodCard of players in any case the deck is set correctly
        deckThreePlayers.playerGodLikeChoose(deckThreePlayers.getGodCards().get(5));
        assertEquals(3,deckThreePlayers.getChosenGodCards().length);

    }





}