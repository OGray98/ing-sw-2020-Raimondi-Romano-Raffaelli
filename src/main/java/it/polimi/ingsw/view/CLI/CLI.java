package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI extends ClientView {

    private Scanner reader = new Scanner(System.in);
    private int[][] cellLevelRep = new int[5][5];
    private String[][] playersRep = new String[5][5];
    private boolean canUsePower = false;
    private String workerToMove = new String(" ");

    public CLI(ViewModelInterface clientModel) {
        super(clientModel);
    }

    @Override
    public void receiveErrorMessage(String error) {
        System.out.println(error);
    }

    @Override
    public void init() {
        //creating initial board
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                playersRep[i][j] = new String(" ");
            }
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                cellLevelRep[i][j] = 0;
            }
        }
        System.out.println("\n\n*********   Welcome to Santorini!   *********\n\n");
    }

    @Override
    public void showGodLikeChoice(List<String> gods) {

        System.out.println("Select the gods for all players that will be played in the game:\n");

        for(String god : gods){
            System.out.println(" - " + (gods.indexOf(god) + 1) + " - " + god +
                    ": " + clientModel.getGodsDescription().get(gods.indexOf(god)));
        }

        System.out.println("\n");

        int godsToChoose;
        if(!clientModel.isThreePlayersGame())
            godsToChoose = 2;
        else
            godsToChoose = 3;

        List<String> godsChosen = new ArrayList<>();

        while(godsChosen.size() < godsToChoose){
            System.out.println("Select the number for god " + (godsChosen.size() + 1) + ":");

            try{
                int godNum = Integer.parseInt(reader.nextLine()) - 1;
                if(godNum < 0 || godNum > 13){
                    System.out.println("You can only insert a number between 1 and 14");
                }
                else{
                    if(!godsChosen.contains(gods.get(godNum))){
                        godsChosen.add(gods.get(godNum));
                        if(godsChosen.size() < godNum)
                            System.out.println("You add " + gods.get(godNum) + " to remove it reinsert the same number");
                    }
                    else{
                        godsChosen.remove(gods.get(godNum));
                        System.out.println("You removed " + gods.get(godNum) + " from chosen gods");
                    }
                }
            }
            catch (NumberFormatException e){
                System.out.println("You can only insert a number between 1 and 14");
            }
        }

        System.out.println("\n");
        handleMessage(new GodLikeChoseMessage(clientModel.getPlayerIndex(), godsChosen));
    }

    @Override
    public void showGodToSelect(List<String> godLikeGods) {
        System.out.println("Select the god you want to play:\n");

        for(String god : godLikeGods){
            System.out.println(" - " + godLikeGods.indexOf(god) + " - " + god +
                    ": " + clientModel.getGodsDescription().get(godLikeGods.indexOf(god)));
        }

        System.out.println("\n");

        System.out.println("Insert the number of the god you want: ");

        String chosenGod = new String();

        while(chosenGod.equals("")){
            if(godLikeGods.size() == 2){
                try{
                    int selectedGod = Integer.parseInt(reader.nextLine());
                    if(selectedGod < 0 || selectedGod > 1){
                        System.out.println("You can only insert a number between 0 and 1");
                    }
                    else
                        chosenGod = godLikeGods.get(selectedGod);
                }
                catch (NumberFormatException e){
                    System.out.println("You can only insert a number between 0 and 1");
                }
            }
            else {
                try{
                    int selectedGod = Integer.parseInt(reader.nextLine());
                    if(selectedGod < 0 || selectedGod > 2){
                        System.out.println("You can only insert a number between 0 and 2");
                    }
                    else
                        chosenGod = godLikeGods.get(selectedGod);
                }
                catch (NumberFormatException e){
                    System.out.println("You can only insert a number between 0 and 2");
                }
            }
        }

        handleMessage(new PlayerSelectGodMessage(clientModel.getPlayerIndex(), chosenGod));
    }


    @Override
    public String showSelectIP(String message) {
        System.out.println(message + '\n');
        return reader.nextLine();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showGetNickname() {

        if(clientModel.getPlayerIndex() == PlayerIndex.PLAYER0){
            System.out.println("Do you want a game with 2 or 3 players?");
            System.out.println("Insert '2' or '3':");

            String isThreePlayersGame  = reader.nextLine();

            while (!isThreePlayersGame.equals("2") && !isThreePlayersGame.equals("3")){
                System.out.println("Insert '2' or '3':");
                isThreePlayersGame = reader.nextLine();
            }

            if(isThreePlayersGame.equals("2")){
                handleMessage(new TypeMatchMessage(clientModel.getPlayerIndex(), false));
            }
            else if(isThreePlayersGame.equals("3")){
                handleMessage(new TypeMatchMessage(clientModel.getPlayerIndex(), true));
            }
        }

        System.out.println("Insert your nickname: ");
        String nickname = reader.nextLine();
        handleMessage(new NicknameMessage(clientModel.getPlayerIndex(), nickname));

        System.out.println("Waiting for players...\n");
    }

    @Override
    public void removeActionsFromView() {

    }

    @Override
    public void showActionPositions(List<Position> possiblePosition, boolean isPowerCells) {

    }



    @Override
    public void updatePutWorker(PutWorkerMessage message) {
        int rowWorker1 = message.getPositionOne().row;
        int colWorker1 = message.getPositionOne().col;
        int rowWorker2 = message.getPositionTwo().row;
        int colWorker2 = message.getPositionTwo().col;

        playersRep[rowWorker1][colWorker1] = getWorker(message.getClient());
        playersRep[rowWorker2][colWorker2] = getWorker(message.getClient());

        System.out.println("\n\n");
        printBoardRep();
    }

    @Override
    public void updateMoveWorker(MoveMessage message) {
        int newRow = message.getMovePosition().row;
        int newCol = message.getMovePosition().col;
        int oldRow = message.getWorkerPosition().row;
        int oldCol = message.getWorkerPosition().col;

        if(clientModel.isOccupiedPosition(new Position(newRow, newCol))){
            workerToMove = playersRep[oldRow][oldCol];
        }
        if(!workerToMove.equals(" ")){
            playersRep[newRow][newCol] = workerToMove;
            workerToMove = " ";
            return;
        }
        playersRep[newRow][newCol] = getWorker(message.getClient());
        playersRep[oldRow][oldCol] = " ";

        System.out.println("\n\n");
        printBoardRep();
    }

    @Override
    public void updateBuild(BuildViewMessage message) {
        int buildRow = message.getBuildPosition().row;
        int buildCol = message.getBuildPosition().col;

        cellLevelRep[buildRow][buildCol]++;

        System.out.println("\n\n");
        printBoardRep();
    }

    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {
        if(!(message.getClient() == clientModel.getPlayerIndex()))
            return;
        System.out.println("\n");
        System.out.println("Your god is " + message.getGodName() + ": " /*TODO: mettere descrizione*/);
        System.out.println("\n");
    }

    @Override
    public void updateActions(PositionMessage message) {

    }

    @Override
    public void updateRemovePlayer(RemovePlayerMessage message) {
        for(Position removePosition : message.getRemovePositions()){
            playersRep[removePosition.row][removePosition.col] = " ";
        }
    }

    @Override
    public void showWinner(InformationMessage message) {
        OkMessage winnerMsg = (OkMessage) message;
        System.out.println("******************************");
        System.out.println(winnerMsg.getErrorMessage() + "game is finished!");
        System.out.println("******************************");
    }

    @Override
    public void showLoser(InformationMessage message) {
        OkMessage winnerMsg = (OkMessage) message;
        System.out.println(winnerMsg.getErrorMessage());
    }

    @Override
    public void showPowerButton(boolean isOn) {
        if(isOn){
            this.canUsePower = true;
        }
    }

    @Override
    public void showEndTurnButton(boolean isOn) {
        if(clientModel.getPowerGodState() != GameState.BUILDPOWER){
            handleMessage(new EndTurnMessage(clientModel.getPlayerIndex()));
        }
    }

    @Override
    public void reinsertNickname() {
        System.out.println("Nickname already taken, try an other:");
        String newNick = reader.nextLine();
        handleMessage(new NicknameMessage(clientModel.getPlayerIndex(), newNick));
    }

    @Override
    public void showGodLikeChooseFirstPlayer() {
        System.out.println("Who will start the game?\n");

        for(String nick : clientModel.getNicknames()){
            System.out.println(" - " + clientModel.getNicknames().indexOf(nick) + " - " + nick);
        }

        System.out.println("\n");

        List<String> players = clientModel.getNicknames();
        int chosenPlayer = -1;

        while(chosenPlayer == -1){
            if(players.size() == 2){
                try{
                    int selectedPlayer = Integer.parseInt(reader.nextLine());
                    if(selectedPlayer < 0 || selectedPlayer > 1){
                        System.out.println("You can only insert a number between 0 and 1");
                    }
                    else
                        chosenPlayer = selectedPlayer;
                }
                catch (NumberFormatException e){
                    System.out.println("You can only insert a number between 0 and 1");
                }
            }
            else {
                try{
                    int selectedPlayer = Integer.parseInt(reader.nextLine());
                    if(selectedPlayer < 0 || selectedPlayer > 2){
                        System.out.println("You can only insert a number between 0 and 2");
                    }
                    else
                        chosenPlayer = selectedPlayer;
                }
                catch (NumberFormatException e){
                    System.out.println("You can only insert a number between 0 and 2");
                }
            }
        }

        if(chosenPlayer == 0){
            handleMessage(new GodLikeChooseFirstPlayerMessage(clientModel.getPlayerIndex(), PlayerIndex.PLAYER0));
        }
        else if(chosenPlayer == 1){
            handleMessage(new GodLikeChooseFirstPlayerMessage(clientModel.getPlayerIndex(), PlayerIndex.PLAYER1));
        }
        else{
            handleMessage(new GodLikeChooseFirstPlayerMessage(clientModel.getPlayerIndex(), PlayerIndex.PLAYER2));
        }
    }

    @Override
    public void showCurrentPlayer(PlayerIndex currentPlayer) {
        System.out.println("It's "+ clientModel.getNickname(currentPlayer) + " turn now");
    }

    @Override
    public void changeState(String state){
        //start put worker
        if(state.equals("")){
            try {
                clientModel.getPlayerIndexPosition(clientModel.getPlayerIndex());
            }
            catch (NullPointerException e){
                return;
            }
            printBoardRep();
            putWorkerInput();
            return;
        }
        System.out.println(state);
    }

    @Override
    public void receiveInputCli() {
        if(!clientModel.isAmICurrentPlayer()){
            return;
        }
        System.out.println("\n");
        printBoardRep();
        positionInput();
    }

    @Override
    public void deactivatePower(){
        this.canUsePower = false;
    }

    /**
     * Method used to print the current state of the board
     */
    private void printBoardRep(){
        for(int i=0;i<5;i++){
            if(i==0){
                System.out.println("        0     1     2     3     4");
                System.out.println("_________________________________");
            }
            else {
                System.out.print("\n\n");
            }
            for(int j=0;j<5;j++){
                if(j==0){
                    System.out.print( (i) + " |     ");
                }
                if(cellLevelRep[i][j] == 4){
                    System.out.print("X" + playersRep[i][j] + "    ");
                }
                else {
                    System.out.print(cellLevelRep[i][j] + playersRep[i][j] + "    ");
                }
            }
        }
        System.out.print("\n\n");
    }

    /**
     * Method that handle the input to put the two worker on the board
     * */
    private void putWorkerInput(){
        System.out.println("To select a cell insert the row and then the column");
        List<Position> putPos = new ArrayList<>();
        int contIndexes = 0;
        int cont = 0;
        int row = 0;
        int col = 0;

        while(cont < 2){
            while (contIndexes==0){
                if(putPos.size() == 1){
                    System.out.println("First cell selected: " + "[" + putPos.get(0).row + "][" + putPos.get(0).col + "]");
                    System.out.println("Now Select the second one!");
                }
                System.out.println("Row:");
                try {
                    row = Integer.parseInt(reader.nextLine());
                    if(!isValidPositionIndex(row)){
                        System.out.println("You must insert a number between 0 and 4!");
                    }
                    else contIndexes++;
                }
                catch (NumberFormatException e){
                    System.out.println("You must insert a number between 0 and 4!");
                }
            }

            while(contIndexes==1){
                System.out.println("Column:");
                try {
                    col = Integer.parseInt(reader.nextLine());
                    if(!isValidPositionIndex(col)){
                        System.out.println("You must insert a number between 0 and 4!");
                        contIndexes = 0;
                        break;
                    }
                    else contIndexes++;
                }
                catch (NumberFormatException e){
                    System.out.println("You must insert a number between 0 and 4!");
                }
                if(clientModel.isOccupiedPosition(new Position(row, col)) || putPos.contains(new Position(row,col))){
                    System.out.println("This cell is already occupied, select an other!");
                    contIndexes = 0;
                }
                else {
                    putPos.add(new Position(row, col));
                    contIndexes = 0;
                    cont++;
                }
            }
        }

        handleMessage(new PositionMessage(clientModel.getPlayerIndex(), putPos.get(0), false));
        handleMessage(new PositionMessage(clientModel.getPlayerIndex(), putPos.get(1), false));
    }

    /**
     * Method that handle the input from user in every phase of the game after PUTWORKER state
     * */
    private void positionInput(){
        System.out.println("To select a cell insert the row and then the column");
        int contIndexes = 0;
        int cont = 0;
        int row = 0;
        int col = 0;
        int selectedWorkerRow = 0;
        int selectedWorkerCol = 0;
        boolean usingPower = false;

        while(cont == 0){
            if(!clientModel.isThereASelectedWorker()){
                System.out.println("Select one of your worker (you have tiles with letter " + getWorker(clientModel.getPlayerIndex()) + ")");
            }
            else if (canUsePower && !usingPower){
                if(clientModel.getActionPositions(new Position(selectedWorkerRow, selectedWorkerCol), ActionType.POWER).size() != 0){
                    System.out.println("Your power is: "  /*TODO: mettere descrizione*/);
                    System.out.println("You can use power during this turn phase! Power can be used in the following cells:");
                    printActionPositions(ActionType.POWER);
                    System.out.println("Press 'p' if you want to use the power! Press anything else to go on without power");
                    if(reader.nextLine().equals("p")){
                        usingPower = true;
                    }
                }
            }
            else {
                if(clientModel.getCurrentState() == GameState.ENDPHASE){
                    handleMessage(new EndTurnMessage(clientModel.getPlayerIndex()));
                    return;
                }
                printActionPositions(ActionType.MOVE);
                System.out.println("Select a cell for your action");
            }
            while (contIndexes==0){
                System.out.println("Row:");
                try {
                    row = Integer.parseInt(reader.nextLine());
                    if(!isValidPositionIndex(row)){
                        System.out.println("You must insert a number between 0 and 4!");
                    }
                    else contIndexes++;
                }
                catch (NumberFormatException e){
                    System.out.println("You must insert a number between 0 and 4!");
                }
            }

            while(contIndexes==1){
                System.out.println("Column:");
                try {
                    col = Integer.parseInt(reader.nextLine());
                    if(!isValidPositionIndex(col)){
                        System.out.println("You must insert a number between 0 and 4!");
                        contIndexes = 0;
                        break;
                    }
                    else contIndexes++;
                }
                catch (NumberFormatException e){
                    System.out.println("You must insert a number between 0 and 4!");
                }
                if(clientModel.getPlayerIndexPosition(clientModel.getPlayerIndex()).contains(new Position(row,col))){
                    System.out.println("You selected worker in position: ["+row+"]["+col+"]");
                    clientModel.setSelectedWorkerPos(new Position(row, col));
                    selectedWorkerRow = row;
                    selectedWorkerCol = col;
                    contIndexes = 0;
                    cont = 0;
                    break;
                }
                else {
                    if(!clientModel.isThereASelectedWorker()){
                        contIndexes = 0;
                        break;
                    }
                }
                if(!usingPower && !clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE).contains(new Position(row, col))){
                    System.out.println("This cell is not valid, please insert an other!");
                    contIndexes = 0;
                    cont = 0;
                }
                if(usingPower && !clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(new Position(row, col))){
                    usingPower = false;
                    System.out.println("This cell is not valid, please insert an other!");
                    contIndexes = 0;
                    cont = 0;
                }
                else {
                    cont++;
                }
            }
        }

        if(!usingPower){
            handleMessage(new PositionMessage(clientModel.getPlayerIndex(), new Position(row, col), false));
            return;
        }
        else handleMessage(new PositionMessage(clientModel.getPlayerIndex(), new Position(row,col), true));
    }

    private boolean isValidPositionIndex(int index){
        if(index < 0 || index > 4)
            return false;
        return true;
    }

    /**
     * Method that prints the possible positions required
     * @param type indicates the type of action to see, power or normal
     * */
    private void printActionPositions(ActionType type){
        if(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), type).size() == 0){
            System.out.println("No possible moves...");
            return;
        }
        System.out.print("Valid action positions: ");
        for(Position p : clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), type)){
            System.out.print(" [" + p.row + ", " + p.col + "] ");
        }
        System.out.println("\n");
    }

    /**
     * Given a PlayerIndex returns the representation of the tile for the related player
     * */
    private String getWorker(PlayerIndex index){
        if(index == PlayerIndex.PLAYER0){
            return "A";
        }
        else if(index == PlayerIndex.PLAYER1){
            return "B";
        }
        else {
            return "C";
        }
    }
}
