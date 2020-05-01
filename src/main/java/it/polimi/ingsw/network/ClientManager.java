package it.polimi.ingsw.network;


import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private final Client client;
    private final Board board;
    private final Deck deck;

    public ClientManager(Client client, Deck deck){
        this.client = client;
        this.board = new Board();
        this.deck = deck;
    }

    public void start(){
        if(client.getMessageQueue() != null){
            for(Message mes : client.getMessageQueue()){
                List<Message> newQueue = new ArrayList<>(client.getMessageQueue());
                updateClient(mes);
                newQueue.remove(mes);
                client.setMessageQueue(newQueue);
            }
        }
    }

    public void updateClient(Message message){
        if(message == null)
            throw new NullPointerException("message");
        switch (message.getType()){
            case SELECT_CARD:
                updateSelectCardMessage((PlayerSelectGodMessage) message);
            case PUT_WORKER:
                updateBoardPutWorkerMessage((PutWorkerMessage) message);
            case MOVE:
                updateBoardMoveMessage((MoveMessage) message);
                break;
            case BUILD:
                updateBoardBuildMessage((BuildMessage) message);
                break;
            case USE_POWER:
                updateBoardAfterPowerMessage((UsePowerMessage) message);
                break;
                default:
                //error message
                break;
        }


    }

    public CardInterface updateSelectCardMessage(PlayerSelectGodMessage message){
            return this.deck.getGodCard(message.getGodName());
    }

    public void updateBoardMoveMessage(MoveMessage message){
        this.board.changeWorkerPosition(message.getWorkerPosition(),message.getMovePosition());
    }

    public void updateBoardBuildMessage(BuildMessage message){
        this.board.constructBlock(message.getBuildPosition());
    }

    public void updateBoardAfterPowerMessage(UsePowerMessage message){
        this.board.updateAfterPower(new BoardChange(message.getWorkerPosition(),message.getPowerPosition(),message.getClient()));
    }

    public void updateBoardPutWorkerMessage(PutWorkerMessage message){
        this.board.putWorker(message.getPositionOne(),message.getClient());
        this.board.putWorker(message.getPositionTwo(),message.getClient());
    }



}
