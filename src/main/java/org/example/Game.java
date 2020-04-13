package org.example;

import java.util.*;

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

    /* Method that return a map with of all cards in deck
     */
    public Map<String, String> getCards() {
        Map<String, String> cards = new HashMap<>(Deck.size);
        for (CardInterface card : deck.getGodCards()) {
            cards.put(card.getGodName(), card.getGodDescription());
        }
        return cards;
    }

    public void setGodsChosen(List<String> godNames) throws NullPointerException {
        if (godNames == null)
            throw new NullPointerException("godNames");

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
     * Modify contCurrentPlayer, currentPlayer, contEffect and currentPosition.
     */
    public void startTurn() {
        contCurrentPlayer = (contCurrentPlayer + 1) % 3;
        currentPlayer = players.get(contCurrentPlayer);
        currentPosition = currentPlayer.getCellOccupied().getPosition();
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
                board.getAdjacentPlayers(currentPosition),
                board.getCell(movePos)
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
                board.getAdjacentPlayers(currentPosition),
                board.getCell(buildPos)
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
                getPowerCellList(powerPos),
                board.getAdjacentPlayers(currentPosition)
        );
        currentPosition = currentPlayer.getCellOccupied().getPosition();
        return res;
    }

    /* Method that use currentPlayer power and update the board.
     * Requires a not null Position where use the power.
     * Modifies currentPosition
     */
    public void usePowerWorker(Position powerPos) throws NullPointerException {
        if (powerPos == null)
            throw new NullPointerException("powerPos");

        BoardChange changes = currentPlayer.usePower(board.getCell(powerPos));
        board.updateAfterPower(changes);
        if (!changes.isPlayerChangesNull()) {
            Map<PositionContainer, PlayerIndex> positionsUpdated = changes.getChanges();
            for (Map.Entry<PositionContainer, PlayerIndex> entry : positionsUpdated.entrySet()) {
                if (entry.getValue().compareTo(currentPlayer.getPlayerNum()) == 0)
                    currentPosition = entry.getKey().getOccupiedPosition();
            }
        }
    }

    public List<PlayerInterface> getPlayers() {
        return new ArrayList<>(players);
    }

    public Board getBoard() {
        return new Board(board);
    }

    /* Private method that return a List<Cell> which will be used in Player::canUsePower() and Player::UsePower()
     * Requires a not null powerPos
     */
    private List<Cell> getPowerCellList(Position powerPos) throws NullPointerException {
        if (powerPos == null)
            throw new NullPointerException("powerPos");

        int sizeList = currentPlayer.getPowerListDimension();
        List<Cell> cells = new ArrayList<>(sizeList);
        cells.add(board.getCell(powerPos));
        if (sizeList == 2) {
            int diffRow = powerPos.row - currentPosition.row;
            int diffCol = powerPos.col - currentPosition.col;
            cells.add(board.getCell(new Position(powerPos.row + diffRow, powerPos.col + diffCol)));
        }
        return cells;
    }
}
