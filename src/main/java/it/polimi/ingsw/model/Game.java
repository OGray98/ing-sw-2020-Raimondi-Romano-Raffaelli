package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyPresentGameException;
import it.polimi.ingsw.exception.NotPresentWorkerException;
import it.polimi.ingsw.exception.NotSelectedGodException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Game is a singleton which contains every object of model
 */
public class Game {

    private static Game gameInstance;

    private List<PlayerInterface> players;
    private Board board;
    private Deck deck;
    private int numPlayer;
    private PlayerInterface currentPlayer;
    private Position currentPosition;
    private int contCurrentPlayer;
    private boolean cantGoUp;
    private int contEffect;
    private int contCurrentWorker;


    private Game(List<PlayerInterface> players) throws NullPointerException, IllegalArgumentException {
        if (players == null)
            throw new NullPointerException("players");
        if (players.size() > 3 || players.size() == 1)
            throw new IllegalArgumentException("There can be only 2 or 3 player, you want "
                    + players.size() + " players");

        this.players = new ArrayList<>(players);
        board = new Board();
        deck = new Deck(players.size());
        numPlayer = players.size();
        contCurrentPlayer = 0;
        currentPlayer = players.get(0);
        cantGoUp = false;
        contEffect = 0;
        contCurrentWorker = 0;
    }

    private Game(List<PlayerInterface> players, Board board) throws NullPointerException, IllegalArgumentException {
        if (players == null)
            throw new NullPointerException("players");
        if (board == null)
            throw new NullPointerException("board");
        if (players.size() > 3 || players.size() == 1)
            throw new IllegalArgumentException("There can be only 2 or 3 player, you want "
                    + players.size() + " players");

        this.players = new ArrayList<>(players);
        this.board = new Board(board);
        deck = new Deck(players.size());
        numPlayer = players.size();
        contCurrentPlayer = 0;
        currentPlayer = players.get(0);
        cantGoUp = false;
        contEffect = 0;
        contCurrentWorker = 0;
    }

    public static Game getInstance(List<PlayerInterface> players) {
        if (gameInstance != null)
            throw new AlreadyPresentGameException();
        gameInstance = new Game(players);
        return gameInstance;
    }

    public static Game createInstance(List<PlayerInterface> players, Board board) {
        if (gameInstance != null)
            throw new AlreadyPresentGameException();
        gameInstance = new Game(players, board);
        return gameInstance;
    }

    public static Game getInstance() {
        if (gameInstance == null)
            throw new NullPointerException("Call getInstance(List<PlayerInterface>)");
        return gameInstance;
    }

    public static void deleteInstance() {
        gameInstance = null;
    }

    /* Method that return a map with of all cards in deck
     */
    public Map<String, String> getCards() {
        return deck.getGodCards().stream()
                .collect(Collectors.toMap(
                        CardInterface::getGodName,
                        CardInterface::getGodDescription,
                        (a, b) -> b,
                        HashMap::new
                ));
    }

    /* Method that set the chosen cards in deck.
     * Requires a not null List<String> which contains the name of the god chosen by player god like
     * Throw IllegalArgumentException if List<String> contains equal values.
     */
    public void setGodsChosenByGodLike(List<String> godNames) throws NullPointerException, IllegalArgumentException {
        if (godNames == null)
            throw new NullPointerException("godNames");
        for (int i = 0; i < godNames.size(); i++) {
            for (int j = 0; j < godNames.size(); j++) {
                if (j != i && godNames.get(i).equals(godNames.get(j)))
                    throw new IllegalArgumentException("There are two element with name " + godNames.get(i));
            }
        }
        deck.setChosenGodCards(godNames);

        //The player who start to chose the card is the first after godLike
        contCurrentPlayer = 1;
        currentPlayer = players.get(contCurrentPlayer);
    }

    /* Method that decorate currentPlayer with his divinity.
     * Modifies currentPlayer and contCurrentPlayer
     * Requires not null String which contains the god's name
     */
    public void setPlayerCard(String godName) throws NullPointerException, WrongGodNameException {
        if (godName == null)
            throw new NullPointerException("godName");

        if (deck.getChosenGodCards().stream().noneMatch(card -> card.getGodName().equals(godName)))
            throw new NotSelectedGodException(godName);

        CardInterface card = deck.getGodCard(godName);
        currentPlayer = card.setPlayer(currentPlayer);
        players.set(contCurrentPlayer, currentPlayer);
        updateCurrentPlayer();
    }

    /* Method which set first player and ordinate the list of players
     *
     */
    public void chooseFirstPlayer(PlayerIndex playerIndex) {
        Collections.rotate(players, -playerIndex.ordinal());
        contCurrentPlayer = 0;
        currentPlayer = players.get(contCurrentPlayer);
    }


    /* Method that put a worker of currentPlayer in board
     * Requires a not null Position where put the worker
     */
    public void putWorker(Position putPosition) throws NullPointerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");
        board.putWorker(putPosition, currentPlayer.getPlayerNum());
        currentPlayer.setStartingWorkerSituation(board.getCell(putPosition), false);
        contCurrentWorker++;
        if (contCurrentWorker == 2) {
            contCurrentWorker = 0;
            updateCurrentPlayer();
        }
    }


    /* Method called a turn start and do the setup of turn.
     * Modify contCurrentPlayer, currentPlayer, contEffect and currentPosition.
     */
    public void startTurn() {
        if (contEffect > 0) {
            contEffect--;
            if (contEffect == 0)
                cantGoUp = false;
        }
    }

    public List<Position> getCurrentPlayerWorkersPosition() {
        return this.board.workerPositions(currentPlayer.getPlayerNum());
    }


    /**
     * @return List which contains position of currentPlayer's worker able to move
     */
    public List<Position> canPlayerMoveAWorker() {
        List<Position> workersPos = board.workerPositions(currentPlayer.getPlayerNum());

        return workersPos.stream()
                .filter(workerPos -> {
                            List<Cell> adjacentCells = board.getAdjacentCells(workerPos);
                            return adjacentCells.stream()
                                    .anyMatch(cell -> currentPlayer.canMoveWithPowers(
                                            //board.getPlayersOccupations(new ArrayList<>(List.of(cell.getPosition()))),
                                            this.getPowerPlayerOccupations(cell.getPosition()),
                                            this.getPowerCellList(cell.getPosition()),
                                            board.getCell(workerPos),
                                            this.cantGoUp
                                    ));
                        }
                )
                .collect(Collectors.toList());
    }

    /* Method that do initial operation for player before call his method
     */
    public void setStartingWorker(Position startPos) throws NullPointerException, NotPresentWorkerException {
        if (startPos == null)
            throw new NullPointerException("startPos");
        if (board.getOccupiedPlayer(startPos).compareTo(currentPlayer.getPlayerNum()) != 0)
            throw new NotPresentWorkerException(startPos.row, startPos.col, currentPlayer.getPlayerNum());
        currentPosition = startPos;
        currentPlayer.setStartingWorkerSituation( // setWorkerSituation
                board.getCell(currentPosition),
                cantGoUp
        );
    }


    /* Method that check if a worker of currentPlayer can move in movePos
     * Requires a not null Position where move the worker
     * Modifies currentPosition
     */
    public boolean canMoveWorker(Position movePos) throws NullPointerException, NotPresentWorkerException {
        if (movePos == null)
            throw new NullPointerException("movePos");

        return currentPlayer.canMove(
                board.getPlayersOccupations(new ArrayList<>(List.of(movePos))),
                board.getCell(movePos)
        );
    }


    /**
     * Method that return a list of Position where the player can move
     *
     * @param workerPos Position occupied by worker that player want to move
     * @return List of Position that contains Position where the player can move
     * @throws NullPointerException if workerPos is null
     */
    public List<Position> getMovePositions(Position workerPos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        return this.board.getAdjacentCells(workerPos).stream()
                .filter(cell -> currentPlayer.canMoveWithPowers(
                        //board.getPlayersOccupations(new ArrayList<>(List.of(cell.getPosition()))),
                        this.getPowerPlayerOccupations(cell.getPosition()),
                        this.getPowerCellList(cell.getPosition()),
                        board.getCell(workerPos),
                        this.cantGoUp
                        )
                )
                .map(Cell::getPosition)
                .collect(Collectors.toList());
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


    /**
     * @return true iff currentPlayer can build with his selected worker in an adjacent cell.
     */
    public boolean canPlayerBuildWithSelectedWorker() {
        return board.getAdjacentCells(currentPlayer.getCellOccupied().getPosition()).stream()
                .anyMatch(cell -> currentPlayer.canBuild(
                        board.getPlayersOccupations(new ArrayList<>(List.of(cell.getPosition()))),
                        cell));
    }

    /**
     * Method that return a list of Position where the player can build
     *
     * @param workerPos Position occupied by worker that player want to build
     * @return List of Position that contains Position where the player can build
     * @throws NullPointerException if workerPos is null
     */
    public List<Position> getBuildPositions(Position workerPos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        return this.board.getAdjacentCells(workerPos).stream()
                .filter(cell -> currentPlayer.canBuild(
                        board.getPlayersOccupations(new ArrayList<>(List.of(cell.getPosition()))),
                        cell
                        )
                )
                .map(Cell::getPosition)
                .collect(Collectors.toList());
    }


    /* Method that check if a worker of currentPlayer can build in buildPos
     * Requires a not null Position where the worker want build
     */
    public boolean canBuild(Position buildPos) throws NullPointerException {
        if (buildPos == null)
            throw new NullPointerException("buildPos");
        //currentPosition = currentPlayer.getCellOccupied().getPosition(); //TODO guarda la modifica
        return currentPlayer.canBuild(
                board.getPlayersOccupations(new ArrayList<>(List.of(buildPos))),
                board.getCell(buildPos)
        );
    }

    /* Method that build a block in buildPos and check if the player can activate his power after build
     * Requires a not null Position where build
     */
    public void build(Position buildPos) throws NullPointerException {
        if (buildPos == null)
            throw new NullPointerException("buildPos");
        board.constructBlock(buildPos);
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

        return currentPlayer.canUsePower(
                getPowerCellList(powerPos),
                getPowerPlayerOccupations(powerPos)
        );
    }

    /**
     * Method that return a list of Position where the player can use his power
     *
     * @param workerPos Position occupied by worker that player want to use his power
     * @return List of Position that contains Position where the player can use his power
     * @throws NullPointerException if workerPos is null
     */
    public List<Position> getPowerPositions(Position workerPos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        return this.board.getAdjacentCells(workerPos).stream()
                .filter(cell -> currentPlayer.canUsePower(
                        getPowerCellList(cell.getPosition()),
                        getPowerPlayerOccupations(cell.getPosition())
                        )
                )
                .map(Cell::getPosition)
                .collect(Collectors.toList());
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
        if (!changes.isCantGoUpNull()) {
            cantGoUp = changes.getCantGoUp();
            contEffect = numPlayer;
        }
    }

    //Method called when finished turn to change the currentPlayer
    public void endTurn() {
        updateCurrentPlayer();
    }

    public List<PlayerInterface> getPlayers() {
        return new ArrayList<>(players);
    }

    public Board getBoard() {
        return new Board(this.board); //TODO non funziona costruttore copia board
    }

    public PlayerIndex getCurrentPlayerIndex() {
        return currentPlayer.getPlayerNum();
    }

    /* Private method that return a List<Cell> which will be used in Player::canUsePower()
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
            int newRow = powerPos.row + diffRow;
            int newCol = powerPos.col + diffCol;
            if (!(newRow < 0 || newRow > 4 || newCol < 0 || newCol > 4))
                cells.add(board.getCell(new Position(newRow, newCol)));
        }
        return cells;
    }

    /* Private method that return a Map<Position, PlayerIndex> which will be used in Player::canUsePower()
     * Requires a not null powerPos
     */
    private Map<Position, PlayerIndex> getPowerPlayerOccupations(Position powerPos) throws NullPointerException {
        if (powerPos == null)
            throw new NullPointerException("powerPos");

        int sizeMap = currentPlayer.getPowerListDimension();
        List<Position> positions = new ArrayList<>();
        positions.add(powerPos);
        if (sizeMap == 2) {
            int diffRow = powerPos.row - currentPosition.row;
            int diffCol = powerPos.col - currentPosition.col;
            int newRow = powerPos.row + diffRow;
            int newCol = powerPos.col + diffCol;
            if (!(newRow < 0 || newRow > 4 || newCol < 0 || newCol > 4))
                positions.add(new Position(newRow, newCol));
        }
        return board.getPlayersOccupations(positions);
    }

    /* Private method called when change current player.
     * Modifies contCurrentPlayer and currentPlayer
     */
    private void updateCurrentPlayer() {
        contCurrentPlayer = (contCurrentPlayer + 1) % 3;
        currentPlayer = players.get(contCurrentPlayer);
    }

    public Deck getDeck() {
        return this.deck;
    }
}