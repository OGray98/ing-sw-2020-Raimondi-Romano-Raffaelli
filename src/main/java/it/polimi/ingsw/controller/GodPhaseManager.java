package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.FullChosenCardsException;
import it.polimi.ingsw.exception.NotEnoughGodsForPlayerException;
import it.polimi.ingsw.exception.NotPutTwoWorkerInSamePositionException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;

import java.util.ArrayList;
import java.util.List;


public class GodPhaseManager {


    private List<String> godsChosen;
    private Game game;
    private final int playerGodLikeIndex;

    public GodPhaseManager(Game game){
        this.godsChosen = new ArrayList<>(game.getPlayers().size());
        this.game = game;
        this.playerGodLikeIndex = 0;
    }

    // The PlayerIndex.PLAYER0 is the godLike
    public int getGodLikePlayerIndex(){
       return this.playerGodLikeIndex;
    }

    public List<String> getGodsChosen(){
        return this.godsChosen;
    }

    public void GodLikeChooseCards(String godName){

        if(this.godsChosen.size() == game.getPlayers().size()){
            throw new FullChosenCardsException();
        }

        boolean thereIs = false;
        for (CardInterface god : game.getDeck().getGodCards()) {
            if (godName.equals(god.getGodName())){
                this.godsChosen.add(godName);
                thereIs = true;
            }
        }
        if (!thereIs)
            throw new WrongGodNameException(godName);
    }

    public void setBoolGodLike(){
        if(godsChosen.size() == game.getPlayers().size())
            game.setGodsChosenByGodLike(this.godsChosen);
        else
            throw new NotEnoughGodsForPlayerException((game.getPlayers().size() - godsChosen.size()));
    }

    public void playerChooseGod(String cardGodName){

        if(this.godsChosen.size() == 0){
            throw new NullPointerException("GodChosen is empty");
        }
        boolean thereIs = false;
        if(this.godsChosen.size() != 0){
            for(String name : this.godsChosen){
                if(name.equals(cardGodName)){
                    List<String> newGod = new ArrayList<>(this.godsChosen);
                    game.setPlayerCard(cardGodName);
                    newGod.remove(name);
                    this.godsChosen = newGod;
                    thereIs = true;
                }
            }
        }

        if(!thereIs){
            throw new WrongGodNameException(cardGodName);
        }
    }

    public void godLikeChooseFirstPlayer(PlayerIndex playerFirst){
        game.chooseFirstPlayer(playerFirst);
    }


    public void puttingWorkerInBoard(Position posWorkerOne, Position posWorkerTwo){

        if(posWorkerOne.equals(posWorkerTwo)){
            throw new NotPutTwoWorkerInSamePositionException(posWorkerOne.row,posWorkerOne.col);
        }
        game.putWorker(posWorkerOne);
        game.putWorker(posWorkerTwo);


    }






}
