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
    private boolean isFinishSelectCardPhase;
    private int playersWithWorkerPut;

    public GodPhaseManager(Game game) {
        this.godsChosen = new ArrayList<>(game.getNumPlayer());
        this.game = game;
        this.playerGodLikeIndex = PlayerIndex.PLAYER0;
        this.isFinishSelectCardPhase = false;
        this.playersWithWorkerPut = 0;
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

        if (this.godsChosen.size() == game.getNumPlayer()) {
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
        if(godsChosen.size() == game.getNumPlayer())
            game.setGodsChosenByGodLike(this.godsChosen);
        else
            throw new NotEnoughGodsForPlayerException((game.getNumPlayer() - godsChosen.size()));
    }


    /**
     * The player God like take the last card remained
     * @param cardGodName name of the God that the current Player want
     *
     */
    public void playerChooseGod(String cardGodName) {


        boolean thereIs = false;
        for (String name : this.godsChosen) {
            if (name.equals(cardGodName)) {
                List<String> newGod = new ArrayList<>(this.godsChosen);
                game.setPlayerCard(cardGodName);
                newGod.remove(name);
                this.godsChosen = newGod;

                //If remain only one card, it must be given to the godlike player
                if(this.godsChosen.size() == 1){
                    game.setPlayerCard(this.godsChosen.get(0));
                    this.godsChosen.clear();
                    this.isFinishSelectCardPhase = true;
                }

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
    public void godLikeChooseFirstPlayer(PlayerIndex playerFirst) {
        game.chooseFirstPlayer(playerFirst);
    }


    public boolean canPutWorker(Position workerPos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        return game.canPutWorker(workerPos);
    }

    /**
     * @param posWorkerOne Position (row,col) of the first worker
     * @param posWorkerTwo Position (row,col) of the second worker
     * @throws IllegalArgumentException if positions given are not legal
     */
    public void puttingWorkerInBoard(Position posWorkerOne, Position posWorkerTwo) {

        if (posWorkerOne.equals(posWorkerTwo)) {
            throw new NotPutTwoWorkerInSamePositionException(posWorkerOne.row, posWorkerOne.col);
        }
        if (!game.getBoard().isFreeCell(posWorkerOne)) {
            throw new IllegalArgumentException("position [" + posWorkerOne.row + "][" + posWorkerOne.col + "] is not free");
        }
        if (!game.getBoard().isFreeCell(posWorkerTwo)) {
            throw new IllegalArgumentException("position [" + posWorkerTwo.row + "][" + posWorkerTwo.col + "] is not free");
        }
        game.putWorker(posWorkerOne);
        game.putWorker(posWorkerTwo);

        this.playersWithWorkerPut++;

    }

    public boolean isFinishSelectCardPhase() {
        return isFinishSelectCardPhase;
    }

    public int getPlayersWithWorkerPut() {
        return playersWithWorkerPut;
    }
}
