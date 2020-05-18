package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPutWorkerException;
import it.polimi.ingsw.exception.NotSelectedGodException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import it.polimi.ingsw.stub.StubView;

/**
 * This class is the model which contains data used by clients
 */
public class ClientModel extends Observable<MessageToView> {

    //TODO MANCANO TUTTE LE NOTIFY ALLA VIEW
    //TODO RAPPRESENTAZIONE DEI GOD UN PO' SCHIFOSA, FARE UN' INTERFACCIA/CLASSE COMUNE CON CARDINTERFACE?
    //TODO FARE INTERFACCE/CLASSI COMUNI CON I VARI ELEMENTI DEL MODEL? ESEMPIO BOARD IMPLEMENT BOARDINTERFACE E CLIENTBOARD IMPLEMENT BOARDINTERFACE?

    //TODO: provvisorio per testare le notifiche alla gui
    private ClientView gui;

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
    private final Map<PlayerIndex, String> chosenGods = new HashMap<>(2);
    private final Map<PlayerIndex, String> nicknames = new HashMap<>(2);
    private final List<String> godsChosenByGodLike = new ArrayList<>(2);
    private PlayerIndex playerIndex;
    private GameState currentState = GameState.NULL;
    private GameState powerGodState = GameState.NULL;


    List<Position> normalActionPositions = new ArrayList<>();
    List<Position> powerActionPositions = new ArrayList<>();

    boolean amICurrentPlayer = false;

    public ClientModel() {

        for (int i = 0; i < Board.NUM_ROW; i++)
            for (int j = 0; j < Board.NUM_COLUMNS; j++)
                this.levelsPositions.put(new Position(i, j), 0);
    }


    /**
     * Add nickname to list of players nicknames
     *
     * @param nickname nickname of new player
     * @throws NullPointerException if nickname is null
     */
    public void addNickname(PlayerIndex index, String nickname) throws NullPointerException {
        if (nickname == null) throw new NullPointerException("nickname");
        nicknames.put(index, nickname);
    }


    /**
     * Increment level of a cell in passed Position
     *
     * @param pos Position where build a level
     * @throws NullPointerException if pos is null
     */
    public void incrementLevel(Position pos) throws NullPointerException {
        if (pos == null) throw new NullPointerException("pos");

        int level = 0;
        for(Position p: levelsPositions.keySet()){
            if(p.equals(pos)){
                this.levelsPositions.replace(p,this.levelsPositions.get(p) + 1);
                //save the level to notify the view
                level = levelsPositions.get(p);
            }
        }

        //notify to view
        notify(new BuildViewMessage(playerIndex, pos, level));
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

        //notify to view
        notify(new BuildViewMessage(playerIndex, pos, 4));
    }

    /**
     * Move a worker of playerIndex from oldPos to newPos
     *
     * @param message MoveMessage received from ClientManager
     * @throws NullPointerException     if oldPos or newPos is null
     * @throws IllegalArgumentException if oldPos is equal to newPos
     */
    public void movePlayer(MoveMessage message) throws NullPointerException, IllegalArgumentException {
        if (message.getWorkerPosition() == null) throw new NullPointerException("pos1");
        if (message.getMovePosition() == null) throw new NullPointerException("pos2");
        if (message.getWorkerPosition().equals(message.getMovePosition())) throw new IllegalArgumentException();

        this.playersPositions.entrySet().stream()
                .filter(entry -> entry.getValue().contains(message.getWorkerPosition()))
                .forEach(entry -> {
                    entry.getValue().remove(message.getWorkerPosition());
                    entry.getValue().add(message.getMovePosition());
                });
        //notify to view
        notify(message);
    }

    /**
     * Put a worker of playerIndex in positions pos1 and pos2
     *
     * @param playerIndex player who put the worker
     * @param pos1         first Position where put worker
     * @param pos2         second Position where put worker
     * @throws NullPointerException      if pos is null
     * @throws InvalidPutWorkerException if the player has already had 2 worker on board
     */
    public void putWorker(PlayerIndex playerIndex, Position pos1, Position pos2) throws NullPointerException, InvalidPutWorkerException {
        if (pos1 == null) throw new NullPointerException("pos1");
        if (pos2 == null) throw new NullPointerException("pos2");
        if (this.playersPositions.containsKey(playerIndex) && this.playersPositions.get(playerIndex).size() != 0)
            throw new InvalidPutWorkerException(pos1.row, pos1.col, playerIndex);

        if (this.playersPositions.containsKey(playerIndex)){
            this.playersPositions.get(playerIndex).add(pos1);
            this.playersPositions.get(playerIndex).add(pos2);
        }
        else
            this.playersPositions.put(playerIndex, new ArrayList<>(List.of(pos1,pos2)));
        notify(new PutWorkerMessage(playerIndex, pos1, pos2));
    }


    /**
     * Add a new god name in list of god chosen
     *
     * @param name name of god chosen by god like
     * @throws NullPointerException if name is null
     */
    public void addGodChosenByGodLike(String name) throws NullPointerException {
        if (name == null) throw new NullPointerException("name");
        this.godsChosenByGodLike.add(name);
    }


    /**
     * Delete worker of indexLoser from board
     *
     * @param indexLoser PlayerIndex of player who lose
     */
    public void playerLose(PlayerIndex indexLoser) {
        playersPositions.remove(indexLoser);
    }


    public List<String> getChosenGodsByGodLike() {
        return this.godsChosenByGodLike;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public String getPlayerNickname() {
        return nicknames.get(playerIndex);
    }

    public void setPlayerNickname(String playerNickname) {
        nicknames.put(playerIndex, playerNickname);
    }

    public List<String> getNicknames() {
        return new ArrayList<>(this.nicknames.values());
    }

    public PlayerIndex getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
    }

    public List<Position> getDomesPositions(){
        return this.domesPositions;
    }

    public GameState getPowerGodState() {
        return powerGodState;
    }

    public int getLevelPosition(Position position) {
        for (Position pos : levelsPositions.keySet()) {
            if (pos.equals(position)) {
                return levelsPositions.get(pos);
            }
        }
        return -1;
    }

    public List<Position> getPlayerIndexPosition(PlayerIndex playerIndex) {
        for (PlayerIndex player : playersPositions.keySet()) {
            if (player.equals(playerIndex)) {
                return this.playersPositions.get(player);
            }
        }
        return null;
    }

    public boolean nicknameIsPresent(String nickname) throws NullPointerException {
        if (nickname == null) throw new NullPointerException("nickname");
        return nicknames.containsValue(nickname);
    }

    public boolean thePositionContainDome(Position position) {
        for (Position pos : domesPositions) {
            if (pos.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public String getClientGod() {
        return chosenGods.get(playerIndex);
    }

    /**
     * Return position useful for the next action of passed type
     *
     * @param type type of next action
     * @return position useful for the next action of passed type
     */
    public List<Position> getActionPositions(ActionType type) {
        if (type == ActionType.POWER)
            return this.powerActionPositions;
        return this.normalActionPositions;
    }

    /**
     * Set Position usable when you want do an action of passed ActionType
     * @param message positions passed by server
     */
    public void setActionPositions(ActionMessage message) {
        //TODO: le notify forse non vanno fatte (forse meglio ottenere quelle celle con un getter dalla view??)
        if (message.getActionType() == ActionType.POWER){
            this.powerActionPositions = message.getPossiblePosition();
            notify(message);
        }
        else{
            this.normalActionPositions = message.getPossiblePosition();
            notify(message);
        }
    }

    /**
     * Create the correlation playerIndex/God chosen
     *
     * @param message contains the GodName chosen and the playerIndex of the player who choosed
     * @throws NullPointerException    if god is null
     * @throws NotSelectedGodException if god isn't a chosen god by god like
     */
    public void setGodChosenByPlayer(PlayerSelectGodMessage message) throws NullPointerException, NotSelectedGodException {
        if (message.getGodName() == null) throw new NullPointerException("god");
        if (!godsChosenByGodLike.contains(message.getGodName())) throw new NotSelectedGodException(message.getGodName());
        this.chosenGods.put(message.getClient(), message.getGodName());

        //notify to View
        notify(message);

        if (message.getClient().equals(playerIndex))
            setClientPowerState();
    }

    public String getGodChosenByPlayer(PlayerIndex index) {
        return this.chosenGods.get(index);
    }


    /**
     * Set power state of this client
     */
    private void setClientPowerState() {

        //TODO DA CAMBIARE FA SCHIFO STO SWITCH

        switch (this.chosenGods.get(playerIndex)) {
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
                throw new WrongGodNameException(this.chosenGods.get(playerIndex));
        }
    }

    public boolean isAmICurrentPlayer() {
        return amICurrentPlayer;
    }

    public void setAmICurrentPlayer(boolean amICurrentPlayer) {
        this.amICurrentPlayer = amICurrentPlayer;
    }
}
