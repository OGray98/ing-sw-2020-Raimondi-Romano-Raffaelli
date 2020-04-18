package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.exception.InvalidNumberCardsChosenException;
import it.polimi.ingsw.exception.WrongGodNameException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
    public void testIfConstructorIsCorrect(){

        assertEquals("Apollo",deckEmpty.getGodCards().get(0).getGodName());
        assertEquals("Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.",deckEmpty.getGodCards().get(0).getGodDescription());
        assertEquals("Artemis",deckEmpty.getGodCards().get(1).getGodName());
        assertEquals("Your Worker may move one additional time, but not back to its initial space.",deckEmpty.getGodCards().get(1).getGodDescription());
        assertEquals("Athena",deckEmpty.getGodCards().get(2).getGodName());
        assertEquals("If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.",deckEmpty.getGodCards().get(2).getGodDescription());
        assertEquals("Atlas",deckEmpty.getGodCards().get(3).getGodName());
        assertEquals("Your Worker may build a dome at any level.",deckEmpty.getGodCards().get(3).getGodDescription());
        assertEquals("Demeter",deckEmpty.getGodCards().get(4).getGodName());
        assertEquals("Your Worker may build one additional time, but not on the same space.",deckEmpty.getGodCards().get(4).getGodDescription());
        assertEquals("Hephaestus",deckEmpty.getGodCards().get(5).getGodName());
        assertEquals("Your Worker may build one additional block (not dome) on top of your first block.",deckEmpty.getGodCards().get(5).getGodDescription());
        assertEquals("Minotaur",deckEmpty.getGodCards().get(6).getGodName());
        assertEquals("our Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.",deckEmpty.getGodCards().get(6).getGodDescription());
        assertEquals("Pan",deckEmpty.getGodCards().get(7).getGodName());
        assertEquals("You also win if your Worker moves down two or more levels.",deckEmpty.getGodCards().get(7).getGodDescription());
        assertEquals("Prometheus",deckEmpty.getGodCards().get(8).getGodName());
        assertEquals("If your Worker does not move up, it may build both before and after moving.",deckEmpty.getGodCards().get(8).getGodDescription());



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
            assertTrue(c.getBoolChosenGod());
        }
        for(CardInterface c : deckThreePlayers.getGodCards()){
            deckThreePlayers.playerGodLikeChoose(c);
            assertTrue(c.getBoolChosenGod());
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
        threeGods.add("Demeter");
        threeGods.add("Artemis");
        threeGods.add("Pan");
        deckThreePlayers.setChosenGodCards(threeGods);
        assertTrue(deckThreePlayers.getGodCard("Demeter").getBoolChosenGod());
        assertTrue(deckThreePlayers.getGodCard("Artemis").getBoolChosenGod());
        assertTrue(deckThreePlayers.getGodCard("Pan").getBoolChosenGod());

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