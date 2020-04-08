package com.tema1.Game;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.LegalGoods;
import com.tema1.helpers.Constants;
import com.tema1.main.GameInput;
import com.tema1.players.Compare;
import com.tema1.players.Greedyplayer;
import com.tema1.players.Player;
import com.tema1.players.PlayerFactory;

import java.util.*;

public class Game {
    private List<String> playerNames = new ArrayList<>(Constants.MAX_NUMBER_OF_PLAYERS);
    private ArrayList<Player> properPlayers = new ArrayList<>(Constants.MAX_NUMBER_OF_PLAYERS);
      private ArrayList<Goods> cardPack = new ArrayList<>();
    /**@playGame*/
      public void playGame(final GameInput input) {
          int numberOfRound = input.getRounds();
          playerNames = input.getPlayerNames();
          List<Integer> iDs = input.getAssetIds();
          Map<Integer, Goods> all = GoodsFactory.getInstance().getAllGoods();
          for (int id : iDs) {
              for (Map.Entry<Integer, Goods> entry : all.entrySet()) {
                  if (entry.getKey() == id) {
                      cardPack.add(entry.getValue());
                  }
              }
          }
          PlayerFactory pl = new PlayerFactory();
          int indentifier = 0;
          for (int i = 0; i < playerNames.size(); i++) {
              properPlayers.add(pl.getPlayer(playerNames.get(i)));
          }
          for (int i = 0; i < properPlayers.size(); i++) {
              properPlayers.get(i).setListIdentifier(indentifier);
              indentifier++;
          }
          int totalSubRound = numberOfRound * properPlayers.size();
          for (int i = 0; i < totalSubRound; i++) {
              if (i % properPlayers.size() == 0) {
                  Greedyplayer.round++;
              }
              Player sheriffPlay = properPlayers.get(i % properPlayers.size());
              for (int j = 0; j < properPlayers.size(); j++) {
                  if (properPlayers.get(j).getListIdentifier() != sheriffPlay.getListIdentifier()) {
                      properPlayers.get(j).takeCards(cardPack);
                      properPlayers.get(j).comerciantPlay();
                  }
              }
              sheriffPlay.sheriffPlay(cardPack, properPlayers);
          }
          //System.out.println(rounds);
      }
      /**@Compute_and_show*/
      public void computeAndShow() {
          for (int i = 0; i < playerNames.size(); i++) {
              for (HashMap.Entry<Goods, Integer> entry
                      : properPlayers.get(i).getTable().entrySet()) {
                  if (entry.getValue() > 0) {
                      int sum = entry.getValue() * entry.getKey().getProfit();
                      properPlayers.get(i).changeMoneySum(sum);
                  }

              }
          }
          int[] kingBonuses = new int[Constants.MAX_INDEX_OF_GOOD + 1];
          int[] queenBonuses = new int[Constants.MAX_INDEX_OF_GOOD + 1];
          int[] kingIds = new int[Constants.MAX_INDEX_OF_GOOD + 1];
          int[] queenIds = new int[Constants.MAX_INDEX_OF_GOOD + 1];
          for (Player p1 : properPlayers) {
              HashMap<Goods, Integer> playerTable = (HashMap<Goods, Integer>) p1.getTable();
              for (Map.Entry<Goods, Integer> entry : playerTable.entrySet()) {
                  try {
                      if ((entry.getValue() > kingBonuses[entry.getKey().getId()])) {
                          queenBonuses[entry.getKey().getId()] =
                                  kingBonuses[entry.getKey().getId()];
                          queenIds[entry.getKey().getId()] = kingIds[entry.getKey().getId()];
                          kingBonuses[entry.getKey().getId()] = entry.getValue();
                          kingIds[entry.getKey().getId()] = p1.getListIdentifier();


                      }
                  } catch (NullPointerException e) {

                  }

                  try {
                             //System.out.println(King_Ids[entry.getKey().getId()] );
                      if ((kingIds[entry.getKey().getId()] != p1.getListIdentifier())
                             && (entry.getValue() <= kingBonuses[entry.getKey().getId()])
                              && (entry.getValue() > queenBonuses[entry.getKey().getId()])) {
                          queenBonuses[entry.getKey().getId()] = entry.getValue();
                          queenIds[entry.getKey().getId()] = p1.getListIdentifier();
                                 //System.out.println(p1.getList_identifier());
                      }
                  } catch (NullPointerException e) {

                  }
              }

          }
              for (int i = 0; i < Constants.CARDS_TAKEN; i++) {
                  if (kingBonuses[i] > 0) {
                      Goods instanta1 = GoodsFactory.getInstance().getGoodsById(i);
                      properPlayers.get(kingIds[i])
                              .changeMoneySum(((LegalGoods) instanta1).getKingBonus());
                      //System.out.println(((LegalGoods) instanta1).getKingBonus());
                      //System.out.println(King_Ids[i]);
                  }
              }
              for (int i = 0; i < Constants.CARDS_TAKEN; i++) {
                  if (queenBonuses[i] > 0) {
                      Goods instanta2 = GoodsFactory.getInstance().getGoodsById(i);
                      properPlayers.get(queenIds[i])
                              .changeMoneySum(((LegalGoods) instanta2).getQueenBonus());
                      //System.out.println(Queen_Ids[i]);

                  }
              }
              /*for(int i = 0; i < Queen_bonuses.length; i++){
                  System.out.println(Queen_bonuses[i]);
              }*/


          Compare c = new Compare();
          Collections.sort(properPlayers, c);
          for (int i = 0; i < properPlayers.size(); i++) {
              System.out.println(properPlayers.get(i).getListIdentifier() + " "
                     + playerNames.get(properPlayers.get(i).getListIdentifier()).toUpperCase()
                      + " " + properPlayers.get(i).getSumOfMoney());
          }
      }

}
