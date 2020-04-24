package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.FullChosenCardsException;
import it.polimi.ingsw.exception.NotEnoughGodsForPlayerException;
import it.polimi.ingsw.exception.NotPutTwoWorkerInSamePositionException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.ArrayList;
import java.util.List;


public class GodPhaseManager {


    private List<String> godsChosen;
    private final Game game;
    private final PlayerIndex playerGodLikeIndex;

    public GodPhaseManager(Game game){
        this.godsChosen = new ArrayList<>(game.getPlayers().size());
        this.game = game;
        this.playerGodLikeIndex = PlayerIndex.PLAYER0;
    }

    // The PlayerIndex.PLAYER0 is the godLike
    public PlayerIndex getGodLikePlayerIndex() {
        return this.playerGodLikeIndex;
    }

    public List<String> getGodsChosen(){
        return this.godsChosen;
    }

    /**
     * @param godName name of god that godLike want to choose
     */
    public void godLikeChooseCards(String godName) {

        if (this.godsChosen.size() == game.getPlayers().size()) {
            throw new FullChosenCardsException();
        }

        boolean thereIs = false;
        for (CardInterface god : game.getDeck().getGodCards()) {
            if (godName.equals(god.getGodName())) {
                this.godsChosen.add(godName);
                thereIs = true;
            }
        }
        if (!thereIs)
            throw new WrongGodNameException(godName);
    }

    /**
     * Take the godLike chosen cards and set the boolean of the Gods
     */
    public void setBoolGodLike(){
        if(godsChosen.size() == game.getPlayers().size())
            game.setGodsChosenByGodLike(this.godsChosen);
        else
            throw new NotEnoughGodsForPlayerException((game.getPlayers().size() - godsChosen.size()));
    }


    /**
     * The player God like take the last card remained
     * @param cardGodName name of the God that the current Player want
     *
     */
    public void playerChooseGod(String cardGodName) {

        if (this.godsChosen.size() == 0) {
            throw new NullPointerException("GodChosen is empty");
        }
        boolean thereIs = false;
        for (String name : this.godsChosen) {
            if (name.equals(cardGodName)) {
                List<String> newGod = new ArrayList<>(this.godsChosen);
                game.setPlayerCard(cardGodName);
                newGod.remove(name);
                this.godsChosen = newGod;
                thereIs = true;
            }
        }

        if (!thereIs) {
            throw new WrongGodNameException(cardGodName);
        }
    }

    /**
     * @param playerFirst PlayerIndex of the first Player
     */
    public void godLikeChooseFirstPlayer(PlayerIndex playerFirst){
        game.chooseFirstPlayer(playerFirst);
    }


    /**
     * @param posWorkerOne Position (row,col) of the first worker
     * @param posWorkerTwo Position (row,col) of the second worker
     */
    public void puttingWorkerInBoard(Position posWorkerOne, Position posWorkerTwo){

        if(posWorkerOne.equals(posWorkerTwo)){
            throw new NotPutTwoWorkerInSamePositionException(posWorkerOne.row,posWorkerOne.col);
        }
        game.putWorker(posWorkerOne);
        game.putWorker(posWorkerTwo);


    }






}
