package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Game {

    private static Game gameInstance;

    private List<PlayerInterface> players;
    private int contEffect;
    private Board board;
    private Deck deck;
    private int numPlayer;
    private PlayerInterface currentPlayer;
    private Position currentPosition;
    private int contCurrentPlayer;

    private Game(List<PlayerInterface> players) throws NullPointerException {
        if (players == null)
            throw new NullPointerException("players");
        if (players.size() > 3 || players.size() == 1)
            throw new IllegalArgumentException("There can be only 2 or 3 player, you want "
                    + players.size() + " players");
        this.players = new ArrayList<>(players);
        board = new Board();
        deck = new Deck(players.size());
        numPlayer = players.size();
        contEffect = 0;
        contCurrentPlayer = 0;
        currentPlayer = players.get(0);
    }

    public static Game getInstance(List<PlayerInterface> players) {
        if (gameInstance != null)
            throw new AlreadyPresentGameException();
        gameInstance = new Game(players);
        return gameInstance;
    }

    public static Game getInstance() {
        if (gameInstance == null)
            throw new NullPointerException("Call getInstance(List<PlayerInterface>)");
        return gameInstance;
    }

    /* Method called when is necessary to init game (before first turn).
     * It decorated the players with selected CardInterface and reorder list using PlayerIndex.
     * Requires a Map<PlayerIndex, CardInterface> not null with the association PlayerIndex <-> CardInterface
     * Modify players and contCurrentPlayer and contEffect
     */
    public void initGame(Map<PlayerIndex, CardInterface> playersCards) throws NullPointerException {
        if (playersCards == null)
            throw new NullPointerException("playersCards");

        //Create list of PlayerDecorator and reorder the list
        List<PlayerInterface> aus = new ArrayList<>(players);
        players.clear();
        for (Map.Entry<PlayerIndex, CardInterface> entry : playersCards.entrySet()) {
            players.add(entry.getValue().setPlayer(aus.get(entry.getKey().ordinal())));
        }
        players.sort(Comparator.comparing(PlayerInterface::getPlayerNum));

        contCurrentPlayer = 2;
        contEffect = 0;
    }


    /* Method that put a worker of currentPlayer in board
     * Requires a not null Position where put the worker
     */
    public void putWorker(Position putPosition) throws NullPointerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");
        board.putWorker(putPosition, currentPlayer.getPlayerNum());
        currentPlayer.setStartingWorkerSituation(board.getCell(putPosition), false);
    }


    /* Method called a turn start and do the setup of turn.
     * Modify contCurrentPlayer, currentPlayer and contEffect.
     */
    public void startTurn() {
        contCurrentPlayer = (contCurrentPlayer + 1) % 3;
        currentPlayer = players.get(contCurrentPlayer);
        contEffect++;
        if (contEffect == 3)
            board.updateAfterPower(new BoardChange(false));
    }


    /* Method that check if a worker of currentPlayer can move in movePos
     * Requires a not null Position where move the worker
     * Modifies currentPosition
     */
    public boolean canMoveWorker(Position movePos) throws NullPointerException {
        if (movePos == null)
            throw new NullPointerException("movePos");
        currentPosition = currentPlayer.getCellOccupied().getPosition();
        currentPlayer.setStartingWorkerSituation(
                board.getCell(currentPosition),
                board.isCantGoUp()
        );
        return currentPlayer.canMove(
                board.getAdjacentCells(currentPosition),
                board.getAdjacentPlayers(currentPosition),
                movePos
        );
    }


    /* Method that move worker of currentPlayer in movePos, before change the worker position in board, then the cell in
     * currentPlayer
     * Requires a not null Position where move the worker
     * Modifies currentPosition
     */
    public void moveWorker(Position movePos) throws NullPointerException {
        if (movePos == null)
            throw new NullPointerException("movePos");
        board.changeWorkerPosition(currentPosition, movePos);
        currentPlayer.move(board.getCell(movePos));
        currentPosition = movePos;
    }

    /* Method that check if a worker of currentPlayer can build in buildPos
     * Requires a not null Position where the worker want build
     */
    public boolean canBuild(Position buildPos) throws NullPointerException {
        if (buildPos == null)
            throw new NullPointerException("buildPos");
        return currentPlayer.canBuild(
                board.getAdjacentCells(currentPosition),
                board.getAdjacentPlayers(currentPosition),
                buildPos
        );
    }

    /* Method that build a block in buildPos and check if the player can activate his power after build
     * Requires a not null Position where build
     */
    //TODO il controller controlla che la posizione sia adiacente al worker
    public void build(Position buildPos) throws NullPointerException {
        if (buildPos == null)
            throw new NullPointerException("buildPos");
        board.constructBlock(buildPos);
        currentPlayer.activePowerAfterBuild();
    }

    //Method that check if the current player has won
    public boolean hasWonCurrentPlayer() {
        return currentPlayer.hasWin();
    }

    /* Method that return if current worker of currentPlayer can use power in powerPos
     * Requires a not null Position where use the power
     * Modifies currentPosition
     */
    public boolean canUsePowerWorker(Position powerPos) throws NullPointerException {
        if (powerPos == null)
            throw new NullPointerException("powerPos");
        boolean res = currentPlayer.canUsePower(
                board.getAdjacentCells(currentPosition),
                board.getAdjacentPlayers(currentPosition),
                powerPos
        );
        currentPosition = currentPlayer.getCellOccupied().getPosition();
        return res;
    }

    public List<PlayerInterface> getPlayers() {
        return new ArrayList<>(players);
    }

    public Board getBoard() {
        return new Board(board);
    }
}
