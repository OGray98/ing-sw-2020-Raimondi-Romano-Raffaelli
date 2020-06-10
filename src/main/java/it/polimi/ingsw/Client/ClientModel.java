package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPlayerIndexException;
import it.polimi.ingsw.exception.InvalidPutWorkerException;
import it.polimi.ingsw.exception.NotSelectedGodException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.*;

import java.util.*;

//import it.polimi.ingsw.stub.StubView;

/**
 * This class is the model which contains data used by clients
 */
public class ClientModel extends Observable<MessageToView> implements ViewModelInterface{

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
                    "Prometheus", "If your Worker does not move up, it may build both before and after moving.",
                    "Zeus", "Your worker may build a block under itself."
            )
    );
    private final Map<PlayerIndex, String> chosenGods = new HashMap<>(2);
    private final Map<PlayerIndex, String> nicknames = new HashMap<>(2);
    private final List<String> godsChosenByGodLike = new ArrayList<>(2);
    private PlayerIndex playerIndex;
    private GameState currentState = GameState.START_GAME;
    private GameState powerGodState = GameState.NULL;

    private boolean isSelectedWorker = false;
    Position selectedWorkerPos;

    List<Position> normalActionPositionsWorker1 = new ArrayList<>();
    List<Position> normalActionPositionsWorker2 = new ArrayList<>();

    List<Position> powerActionPositionsWorker1 = new ArrayList<>();
    List<Position> powerActionPositionsWorker2 = new ArrayList<>();

    private boolean amICurrentPlayer = false;

    public ClientModel() {

        gods.put("Hestia","Your worker may build one additional time, but this cannot be on a perimeter space.");
        gods.put("Triton","Each time your worker moves into a perimeter space, it may immediately move again.");
        gods.put("Charon","Before your worker moves, you may force a neighboring opponent worker to the space directly on the other side of your worker, if that space is unoccupied.");
        gods.put("Hera", "An opponent cannot win by moving into a perimeter space.");

        for (int i = 0; i < Board.NUM_ROW; i++)
            for (int j = 0; j < Board.NUM_COLUMNS; j++)
                this.levelsPositions.put(new Position(i, j), 0);
    }

    public void setSelectedWorkerPos(Position selectedWorkerPos) {
        if (!this.isSelectedWorker || !selectedWorkerPos.equals(this.selectedWorkerPos)) {
            this.isSelectedWorker = true;
            this.selectedWorkerPos = selectedWorkerPos;
            //notify to view
            notify(new PositionMessage(playerIndex, selectedWorkerPos, false));
        }
    }

    public Position getSelectedWorkerPos(){
        if(this.selectedWorkerPos == null) throw new NullPointerException("not selected any worker!");
        return this.selectedWorkerPos;
    }

    /**
     * Add nickname to list of players nicknames
     *
     * @param nickname nickname of new player
     * @throws NullPointerException if nickname is null
     */
    public void addNickname(PlayerIndex index, String nickname) throws NullPointerException {
        if (nickname == null) throw new NullPointerException("nickname");
        if(!nicknames.containsKey(index))
            nicknames.put(index, nickname);
    }

    public String getNickname(PlayerIndex index) {
        if (!this.nicknames.containsKey(index))
            throw new InvalidPlayerIndexException(index);
        return this.nicknames.get(index);
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
                .filter(entry -> entry.getValue().contains(message.getWorkerPosition()) && entry.getKey() == message.getClient())
                .forEach(entry -> {
                    entry.getValue().remove(message.getWorkerPosition());
                    entry.getValue().add(message.getMovePosition());
                });

        if(message.getClient()==playerIndex){
            this.selectedWorkerPos = message.getMovePosition();
        }

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
        notify(new RemovePlayerMessage(playerIndex, playersPositions.get(indexLoser)));
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
        notify(new ConnectionPlayerIndex(playerIndex));
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
     * @param workerPos is the Position of the worker selected
     * @return position useful for the next action of passed type
     */
    public List<Position> getActionPositions(Position workerPos, ActionType type) {

        if (type == ActionType.POWER){
            if(this.playersPositions.get(playerIndex).get(0).equals(workerPos))
                return powerActionPositionsWorker1;
            if(this.playersPositions.get(playerIndex).get(1).equals(workerPos))
                return powerActionPositionsWorker2;
        }
        else{
            if(this.playersPositions.get(playerIndex).get(0).equals(workerPos))
                return normalActionPositionsWorker1;
            if(this.playersPositions.get(playerIndex).get(1).equals(workerPos))
                return normalActionPositionsWorker2;
        }
        throw new IllegalStateException("Void list returned!");
    }

    /**
     * Set Position usable when you want do an action of passed ActionType
     * @param message positions passed by server
     */
    public void setActionPositions(ActionMessage message) {
        if (message.getActionType() == ActionType.POWER){
            if(message.getWorkerPos().equals(playersPositions.get(playerIndex).get(0)))
                this.powerActionPositionsWorker1 = message.getPossiblePosition();
            else
                this.powerActionPositionsWorker2 = message.getPossiblePosition();
        }
        else{
            if(message.getWorkerPos().equals(playersPositions.get(playerIndex).get(0))){
                this.normalActionPositionsWorker1 = message.getPossiblePosition();
                //notify view the cells where is possible to build
                if(message.getActionType() == ActionType.BUILD)
                    notify(new PositionMessage(playerIndex, message.getWorkerPos(), false));
                if(message.getActionType() == ActionType.MOVE && this.getCurrentState() == GameState.INITPOWER && message.getWorkerPos().equals(this.selectedWorkerPos)){
                    notify(new PositionMessage(playerIndex, message.getWorkerPos(), false));
                }
            }
            else{
                this.normalActionPositionsWorker2 = message.getPossiblePosition();
                //notify view the cells where is possible to build
                if(message.getActionType() == ActionType.BUILD)
                    notify(new PositionMessage(playerIndex, message.getWorkerPos(), false));
                if(message.getActionType() == ActionType.MOVE && this.getCurrentState() == GameState.INITPOWER && message.getWorkerPos().equals(this.selectedWorkerPos)){
                    notify(new PositionMessage(playerIndex, message.getWorkerPos(), false));
                }
            }
        }
    }

    /**
     * When the turn is ended the lists of possible actions will be cleared
     * */
    public void clearActionLists(){
        this.normalActionPositionsWorker1.clear();
        this.normalActionPositionsWorker2.clear();
        this.powerActionPositionsWorker1.clear();
        this.powerActionPositionsWorker2.clear();
        this.isSelectedWorker = false;
        //TODO selectedWorker? serve una Position da distinguere per capire che non ci sono pedine selezionate
    }

    public boolean isThereASelectedWorker(){
        return isSelectedWorker;
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
        this.godsChosenByGodLike.remove(message.getGodName());

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
            case "Charon":
                this.powerGodState = GameState.MOVE;
                break;
            case "Artemis":
            case "Atlas":
            case "Zeus":
            case "Triton":
                this.powerGodState = GameState.BUILD;
                break;
            case "Demeter":
            case "Hephaestus":
            case "Hestia":
                this.powerGodState = GameState.ENDPHASE;
                break;
            case "Hera":
                this.powerGodState = GameState.MATCH_ENDED;
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

    public List<String> getGods() {
        return new ArrayList<>(gods.keySet());
    }

    public List<String> getGodsDescription(){
        return new ArrayList<>(gods.values());
    }

    /**
     * Method needs to be called after initial operations when all players are in lobby
     * */
    public boolean isThreePlayersGame(){
        if (this.nicknames.size() == 3)
            return true;
        else if (this.nicknames.size() == 2)
            return false;
        throw new IllegalStateException("not valid number of players!");
    }

    public boolean isOccupiedPosition(Position pos){
        return this.playersPositions.containsValue(pos);
    }

    public boolean isGodLikeChoosingCards() {
        return this.currentState == GameState.GOD_PLAYER_CHOOSE_CARDS;
    }

    public void notifyInformationMessage(InformationMessage message){
        notify(message);
    }
}
