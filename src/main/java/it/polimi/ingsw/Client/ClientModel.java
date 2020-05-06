package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPutWorkerException;
import it.polimi.ingsw.exception.NotSelectedGodException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the model which contains data used by clients
 */
public class ClientModel extends Observable<Message> {

    //TODO MANCANO TUTTE LE NOTIFY ALLA VIEW
    //TODO RAPPRESENTAZIONE DEI GOD UN PO' SCHIFOSA, FARE UN' INTERFACCIA/CLASSE COMUNE CON CARDINTERFACE?
    //TODO FARE INTERFACCE/CLASSI COMUNI CON I VARI ELEMENTI DEL MODEL? ESEMPIO BOARD IMPLEMENT BOARDINTERFACE E CLIENTBOARD IMPLEMENT BOARDINTERFACE?
    private final Map<Position, Integer> levelsPositions = new HashMap<>(Board.NUM_COLUMNS * Board.NUM_ROW);
    private final List<Position> domesPositions = new ArrayList<>(0);
    private final Map<PlayerIndex, List<Position>> playersPositions = new HashMap<>(2);
    private final Map<String, String> gods = new HashMap<>(
            Map.of(
                    "Apollo", "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.",
                    "Artemis", "Your Worker may move one additional time, but not back to its initial space.",
                    "Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.",
                    "Atlas", "Your Worker may build a dome at any level.",
                    "Demeter", "Your Worker may build one additional time, but not on the same space.",
                    "Hephaestus", "Your Worker may build one additional block (not dome) on top of your first block.",
                    "Minotaur", "our Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.",
                    "Pan", "You also win if your Worker moves down two or more levels.",
                    "Prometheus", "If your Worker does not move up, it may build both before and after moving."
            )
    );
    private final List<String> chosenGods = new ArrayList<>(0);
    private String clientGod;
    private String playerNickname;
    private PlayerIndex playerIndex;
    private GameState currentState = GameState.NULL;
    private GameState powerGodState = GameState.NULL;

    public ClientModel() {
        for (int i = 0; i < Board.NUM_ROW; i++)
            for (int j = 0; j < Board.NUM_COLUMNS; j++)
                this.levelsPositions.put(new Position(i, j), 0);
    }

    /**
     * Increment level of a cell in passed Position
     *
     * @param pos Position where build a level
     * @throws NullPointerException if pos is null
     */
    public void incrementLevel(Position pos) throws NullPointerException {
        if (pos == null) throw new NullPointerException("pos");
        this.levelsPositions.put(pos, this.levelsPositions.get(pos) + 1);
    }

    /**
     * Add a new Position in list of dome
     *
     * @param pos Position where build a dome
     * @throws NullPointerException if pos is null
     */
    public void addDome(Position pos) throws NullPointerException {
        if (pos == null) throw new NullPointerException("pos");
        this.domesPositions.add(pos);
    }

    /**
     * Move a worker of playerIndex from oldPos to newPos
     *
     * @param playerIndex playerIndex of worker that have to move
     * @param oldPos      old Position of worker
     * @param newPos      new Position of worker
     * @throws NullPointerException     if oldPos or newPos is null
     * @throws IllegalArgumentException if oldPos is equal to newPos
     */
    public void movePlayer(PlayerIndex playerIndex, Position oldPos, Position newPos) throws NullPointerException, IllegalArgumentException {
        if (oldPos == null) throw new NullPointerException("oldPos");
        if (newPos == null) throw new NullPointerException("newPos");
        if (oldPos.equals(newPos)) throw new IllegalArgumentException();

        playersPositions.get(playerIndex).remove(oldPos);
        playersPositions.get(playerIndex).add(newPos);
    }

    /**
     * Put a worker of playerIndex in position pos
     *
     * @param playerIndex player who put the worker
     * @param pos         Position where put the worker
     * @throws NullPointerException      if pos is null
     * @throws InvalidPutWorkerException if the player has already had 2 worker on board
     */
    public void putWorker(PlayerIndex playerIndex, Position pos) throws NullPointerException, InvalidPutWorkerException {
        if (pos == null) throw new NullPointerException("pos");
        if (this.playersPositions.containsKey(playerIndex) && this.playersPositions.get(playerIndex).size() >= 2)
            throw new InvalidPutWorkerException(pos.row, pos.col, playerIndex);

        if (this.playersPositions.containsKey(playerIndex))
            this.playersPositions.get(playerIndex).add(pos);
        else
            this.playersPositions.put(playerIndex, new ArrayList<>(List.of(pos)));
    }


    /**
     * Add a new god name in list of god chosen
     *
     * @param name name of god chosen by god like
     * @throws NullPointerException if name is null
     */
    public void addGodChosen(String name) throws NullPointerException {
        if (name == null) throw new NullPointerException("name");
        this.chosenGods.add(name);
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public GameState getPowerGodState() {
        return powerGodState;
    }


    public String getClientGod() {
        return clientGod;
    }

    /**
     * Set god of this client's player
     *
     * @param clientGod name of god
     * @throws NullPointerException    if clientGod is null
     * @throws NotSelectedGodException if clientGod isn't a chosen god by god like
     */
    public void setClientGod(String clientGod) throws NullPointerException, NotSelectedGodException {
        if (clientGod == null) throw new NullPointerException("clientGod");
        if (!chosenGods.contains(clientGod)) throw new NotSelectedGodException(clientGod);
        this.clientGod = clientGod;

        //TODO DA CAMBIARE FA SCHIFO STO SWITCH

        switch (clientGod) {
            case "Apollo":
            case "Athena":
            case "Prometheus":
            case "Pan":
            case "Minotaur":
                this.powerGodState = GameState.MOVE;
                break;
            case "Artemis":
            case "Atlas":
                this.powerGodState = GameState.BUILD;
                break;
            case "Demeter":
            case "Hephaestus":
                this.powerGodState = GameState.ENDPHASE;
                break;
            default:
                throw new WrongGodNameException(clientGod);
        }
    }
}
