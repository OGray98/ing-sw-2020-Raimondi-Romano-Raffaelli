package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Game contains every object of model
 */
public class Game extends Observable<Message> {

    private final List<PlayerInterface> players;
    private final Board board;
    private final Deck deck;
    private final int numPlayer;
    private PlayerInterface currentPlayer;
    private Position currentPosition;
    private int contCurrentPlayer;
    private boolean cantGoUp;
    private int contEffect;
    private int contCurrentWorker;
    private GameState currentState;

    public Game(int numPlayer) throws IllegalArgumentException {
        if (numPlayer < 2 || numPlayer > 3)
            throw new IllegalArgumentException("You must have 2 or 3 player, not " + numPlayer);

        this.players = new ArrayList<>(2);
        this.board = new Board();
        this.deck = new Deck(numPlayer);

        this.numPlayer = numPlayer;
        this.cantGoUp = false;
        this.contEffect = 0;
        this.contCurrentWorker = 0;
        this.contCurrentPlayer = 0;
        this.currentState = GameState.START_GAME;

    }

    /**
     * @return number of players
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * Add a Player in players
     *
     * @param index    PlayerIndex of new Player
     * @param nickname nickname of new Player
     * @throws NullPointerException if nickname is null
     * @throws MaxPlayersException  if there already is max number of Player
     */
    public void addPlayer(PlayerIndex index, String nickname) throws NullPointerException, MaxPlayersException {
        if (nickname == null)
            throw new NullPointerException("nickname");
        if (this.players.size() == this.numPlayer)
            throw new MaxPlayersException();

        this.players.add(new Player(nickname, index));
        notify(new NicknameMessage(PlayerIndex.ALL, nickname));
        if (this.players.size() == this.numPlayer) {
            this.players.sort(Comparator.comparing(PlayerInterface::getPlayerNum));
            this.contCurrentPlayer = 0;
            this.currentPlayer = players.get(0);
            this.cantGoUp = false;
            this.contEffect = 0;
            this.contCurrentWorker = 0;
            setCurrentState(GameState.GOD_PLAYER_CHOOSE_CARDS);
        }

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

    /*Method that returns the powerState of the current player, to check if he is in a state where usePower is allowed*/
    public GameState getCurrentPlayerPowerState() {
        return currentPlayer.getPowerState();
    }

    /*Method that returns the nextState of the current player after he has used a power*/
    public GameState getCurrentPlayerNextState() {
        return currentPlayer.getNextState();
    }

    /*Method that returns the name of the god chosen by the player*/
    public String getCurrentPlayerGodName(){
        return currentPlayer.getGodName();
    }

    public GameState getCurrentState() {
        return this.currentState;
    }

    /*Change the currentState in the nextState given*/
    public void setCurrentState(GameState nextState) {
        this.currentState = nextState;
    }


    /**
     * Notify to remoteView of currentPlayer every possible action that currentPlayer
     * can do when currentState is GameState.MOVE
     *
     * @throws InvalidStateException if currentState isn't GameState.MOVE
     */
    public void sendPossibleActionMoveState() throws InvalidStateException {
        if (this.currentState != GameState.MOVE)
            throw new InvalidStateException(GameState.MOVE, this.currentState);

        //Send position where you can move
        this.board.workerPositions(currentPlayer.getPlayerNum())
                .forEach(
                        pos -> notify(
                                new ActionMessage(
                                        currentPlayer.getPlayerNum(),
                                        pos,
                                        board.getAdjacentCells(pos).stream()
                                                .filter(cell -> canMoveWorker(cell.getPosition()))
                                                .map(Cell::getPosition)
                                                .collect(Collectors.toList()),
                                        ActionType.MOVE
                                )
                        )
                );

        //Send position where use power if the currentPlayer can
        if (this.currentPlayer.getPowerState() == GameState.MOVE) {
            this.board.workerPositions(currentPlayer.getPlayerNum())
                    .forEach(
                            pos -> notify(
                                    new ActionMessage(
                                            currentPlayer.getPlayerNum(),
                                            pos,
                                            board.getAdjacentCells(pos).stream()
                                                    .filter(cell -> canUsePowerWorker(cell.getPosition()))
                                                    .map(Cell::getPosition)
                                                    .collect(Collectors.toList()),
                                            ActionType.POWER
                                    )
                            )
                    );
        }
    }

    /**
     * Notify to remoteView of currentPlayer every possible action that currentPlayer
     * can do when currentState is GameState.BUILD
     *
     * @throws InvalidStateException if currentState isn't GameState.BUILD
     */
    public void sendPossibleActionMoveBuild() throws InvalidStateException {
        if (this.currentState != GameState.BUILD)
            throw new InvalidStateException(GameState.MOVE, this.currentState);

        //Send position where you can move
        notify(
                new ActionMessage(
                        currentPlayer.getPlayerNum(),
                        currentPosition,
                        board.getAdjacentCells(currentPosition).stream()
                                .filter(cell -> canBuild(cell.getPosition()))
                                .map(Cell::getPosition)
                                .collect(Collectors.toList()),
                        ActionType.BUILD
                )
        );

        //Send position where use power if the currentPlayer can
        if (this.currentPlayer.getPowerState() == GameState.BUILD) {
            notify(
                    new ActionMessage(
                            currentPlayer.getPlayerNum(),
                            currentPosition,
                            board.getAdjacentCells(currentPosition).stream()
                                    .filter(cell -> canUsePowerWorker(cell.getPosition()))
                                    .map(Cell::getPosition)
                                    .collect(Collectors.toList()),
                            ActionType.POWER
                    )
            );
        }
    }

    /**
     * Method that set the chosen cards in deck.
     *
     * @param godNames is required as a not null List<String> which contains the name of the god chosen by player god like
     * @throws IllegalArgumentException if List<String> contains equal values.
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
        notify(new GodLikeChoseMessage(PlayerIndex.ALL, godNames));

        //The player who start to chose the card is the first after godLike
        contCurrentPlayer = 1;
        currentPlayer = players.get(contCurrentPlayer);
    }

    /**
     *  Method that decorate this.currentPlayer with his divinity.
     * Modifies currentPlayer and contCurrentPlayer
     * @param godName is required as a not null String which contains the god's name
     */
    public void setPlayerCard(String godName) throws NullPointerException, WrongGodNameException {
        if (godName == null)
            throw new NullPointerException("godName");

        if (deck.getChosenGodCards().stream().noneMatch(card -> card.getGodName().equals(godName)))
            throw new NotSelectedGodException(godName);

        CardInterface card = deck.getGodCard(godName);
        currentPlayer = card.setPlayer(currentPlayer);
        players.set(contCurrentPlayer, currentPlayer);

        if (!currentPlayer.getPlayerNum().equals(players.get(0).getPlayerNum()))
            updateCurrentPlayer();
    }

    /**
     * Method which set first player and ordinate the list this.players
     *
     * @param playerIndex is the PlayerIndex of the first player
     */
    public void chooseFirstPlayer(PlayerIndex playerIndex) {
        Collections.rotate(players, -playerIndex.ordinal());
        contCurrentPlayer = 0;
        currentPlayer = players.get(contCurrentPlayer);
    }

    public boolean canPutWorker(Position putPosition) throws NullPointerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");
        return board.isFreeCell(putPosition);
    }


    /**
     * Method that put a worker of this.currentPlayer in board
     *
     * @param putPosition is a not null Position where put the worker
     */
    public void putWorker(Position putPosition) throws NullPointerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");
        board.putWorker(putPosition, currentPlayer.getPlayerNum());
        currentPlayer.setStartingWorkerSituation(board.getCell(putPosition), false);
        contCurrentWorker++;
        if (contCurrentWorker == 2) {
            List<Position> workersPos = board.workerPositions(currentPlayer.getPlayerNum());
            notify(new PutWorkerMessage(PlayerIndex.ALL, workersPos.get(0), workersPos.get(1)));
            contCurrentWorker = 0;
            updateCurrentPlayer();
        }

    }


    /**
     *  Method calls a turn start and do the setup of turn.
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

    /**
     *  Method that do initial operation for player before call his method
     *  In particular it sets this.currentPosition
     * @throws NotPresentWorkerException if in startPos there is not a worker
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


    /**
     *  Method that check if a worker of this.currentPlayer can move in movePos
     * @param movePos is a not null Position where move the worker
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


    /**
     *  Method that move worker of currentPlayer in movePos. Before change the worker position in board, then the cell in
     * currentPlayer
     * @param movePos is a not null Position where move the worker
     * Modifies this.currentPosition
     */
    public void moveWorker(Position movePos) throws NullPointerException {
        if (movePos == null)
            throw new NullPointerException("movePos");
        board.changeWorkerPosition(currentPosition, movePos);
        currentPlayer.move(board.getCell(movePos));
        notify(new MoveMessage(PlayerIndex.ALL, currentPosition, movePos));
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


    /**
     *  Method that check if a worker of currentPlayer can build in buildPos
     * @param buildPos is a not null Position where the worker want build
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

    /**
     *  Method that build a block in buildPos and check if the player can activate his power after build
     * @param buildPos is a not null Position where build
     */
    public void build(Position buildPos) throws NullPointerException {
        if (buildPos == null)
            throw new NullPointerException("buildPos");
        board.constructBlock(buildPos);
    }

    /**
     * Method that check if the current player has won
     * It simply calls the method PlayerInterface.hasWin()
     * */
    public boolean hasWonCurrentPlayer() {
        return currentPlayer.hasWin();
    }

    /**
     *  Method that return if current worker of currentPlayer can use power in powerPos
     * @param powerPos is a not null Position where use the power
     * Modifies this.currentPosition
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

    /**
     *  Method that use currentPlayer power and update the board.
     * @param powerPos is a not null Position where use the power.
     * Modifies this.currentPosition
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

    /**
     * Method called when finished turn to change the currentPlayer
     * */
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

    public String getCurrentPlayerNick(){
        return currentPlayer.getNickname();
    }

    /**
     *  Private method that returns a List<Cell> which will be used in Player::canUsePower()
     * @param powerPos is a not null powerPos
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

    /**
     *  Private method that returns a Map<Position, PlayerIndex> which will be used in Player::canUsePower()
     * @param powerPos is a not null powerPos
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

    /**
     *  Private method called when change current player.
     * Modifies contCurrentPlayer and currentPlayer
     */
    private void updateCurrentPlayer() {
        contCurrentPlayer = (contCurrentPlayer + 1) % players.size();
        currentPlayer = players.get(contCurrentPlayer);
    }

    public Deck getDeck() {
        return this.deck;
    }

    /**
     * Remove current player and delete his workers from the board.
     * Notify this change to remoteView
     */
    public void removeCurrentPlayer() {
        notify(new LoserMessage(currentPlayer.getPlayerNum()));
        this.board.removePlayerWorkers(currentPlayer.getPlayerNum());
        this.players.remove(currentPlayer);
    }
}