package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Bribedplayer extends Basicplayer {
    private Player left;
    private Player right;
    /**@comerciantPlay*/
     public void comerciantPlay() {
         int illegalCount = 0;
         int legalCount;
         int a = 0;
         for (int i = 0; i < super.getGoodsInHand().size(); i++) {
             if (super.getGoodsInHand().get(i).getType() == GoodsType.Illegal) {
                 illegalCount++;
             }
         }
         legalCount = super.getGoodsInHand().size() - illegalCount;
         if (illegalCount == Constants.ZERO_VAR
                 || super.getSumOfMoney() <= Constants.LITTLE_BRIBE) {
             super.setBribe(Constants.ZERO_VAR);
             super.comerciantPlay();
         } else {
             class Compare implements Comparator<Goods> {
                 @Override
                 public int compare(final Goods c1, final Goods c2) {
                     if (c2.getProfit() != c1.getProfit()) {
                         return c2.getProfit() - c1.getProfit();
                     } else {
                         return c2.getId() - c1.getId();
                     }
                 }

             }
             Compare c = new Compare();
             Collections.sort(super.getGoodsInHand(), c);
       /*for(int i = 0; i < super.getGoods_in_hand().size(); i++)
         System.out.println(super.getGoods_in_hand().get(i).getId());*/
             for (int i = 0; i < 2; i++) {
                 super.getGoodsInHand().remove(super.getGoodsInHand().size() - 1);
             }
       /*for(int i = 0; i < super.getGoods_in_hand().size(); i++)
         System.out.println(super.getGoods_in_hand().get(i).getId());*/
             int newIllegalCount = 0;
             int newLegalCount;
             for (int i = 0; i < super.getGoodsInHand().size(); i++) {
                 if (super.getGoodsInHand().get(i).getType() == GoodsType.Illegal) {
                     newIllegalCount++;
                 }
             }
             newLegalCount = super.getGoodsInHand().size() - newIllegalCount;
            // System.out.println(super.getGoods_in_hand().size());

             //System.out.println(Illegal_count);
             //System.out.println(Legal_count);
             int sumForIllegalPenalty = newIllegalCount * Constants.PENALTY_FOR_ILLEGAL;
             int sumForLegalPenalty = newLegalCount * Constants.PENALTY_FOR_LEGAL;
             int initIllegal = newIllegalCount;
             int initLegal = newLegalCount;

             if (super.getSumOfMoney()
                     - sumForIllegalPenalty - sumForLegalPenalty <= Constants.ZERO_VAR) {
                 while (super.getSumOfMoney()
                         - sumForIllegalPenalty - sumForLegalPenalty <= Constants.ZERO_VAR) {
                     //System.out.println(new_Illegal_count);
                     //System.out.println(new_Legal_count);
                     if (newLegalCount > Constants.ZERO_VAR) {
                         newLegalCount--;
                     } else {
                         if (newLegalCount == Constants.ZERO_VAR) {
                             newIllegalCount--;
                         }

                     }
                     sumForIllegalPenalty = newIllegalCount * Constants.PENALTY_FOR_ILLEGAL;
                     sumForLegalPenalty = newLegalCount * Constants.PENALTY_FOR_LEGAL;
                    // System.out.println(new_Illegal_count);
                    // System.out.println(new_Legal_count);
                 }
             }
             if (super.getSumOfMoney() - sumForIllegalPenalty
                     - sumForLegalPenalty > Constants.PENALTY_FOR_LEGAL
                     && super.getSumOfMoney() - sumForIllegalPenalty
                     - sumForLegalPenalty <= Constants.PENALTY_FOR_ILLEGAL) {
                 if (newIllegalCount + newLegalCount < Constants.MAX_IN_BAG
                         && initIllegal + newLegalCount < Constants.MAX_IN_BAG) {
                        newLegalCount++;
                 }
             }

             //System.out.println(Illegal_count);
             //System.out.println(super.getSack_with_goods().size());
             for (int i = 0; i < newIllegalCount; i++) {
                 super.getSackWithGoods().add(super.getGoodsInHand().get(i));
             }
             //System.out.println(super.getSack_with_goods().size());
             //System.out.println(Illegal_count);
             for (int i = initIllegal; i < initIllegal + newLegalCount; i++) {
                 super.getSackWithGoods().add(super.getGoodsInHand().get(i));
             }
             a = newIllegalCount;

         }
         /*for(int i = 0; i < super.getSack_with_goods().size();i++){
             System.out.println(super.getSack_with_goods().get(i).getId());
         }*/

         if ((a == Constants.THE_SPECIAL_ONE || a == Constants.PENALTY_FOR_LEGAL)
                 && super.getSumOfMoney() > Constants.LITTLE_BRIBE) {
             super.setBribe(Constants.LITTLE_BRIBE);
             super.setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.ZERO_VAR));
         }
         if (a > Constants.PENALTY_FOR_LEGAL && super.getSumOfMoney() > Constants.BIG_BRIBE) {
             super.setBribe(Constants.BIG_BRIBE);
             super.setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.ZERO_VAR));
         }

       }
       /**@findToCheck */
       public void findToCheck(final ArrayList<Player> players) {
           if (players.size() == Constants.PENALTY_FOR_LEGAL) {
               if (super.getListIdentifier() == Constants.ZERO_VAR) {
                   left = players.get(Constants.THE_SPECIAL_ONE);
                   right = players.get(Constants.THE_SPECIAL_ONE);
               } else {
                   left = players.get(Constants.ZERO_VAR);
                   right = players.get(Constants.ZERO_VAR);
               }
               //System.out.println(left.getList_identifier());
           }
           if (players.size() > Constants.PENALTY_FOR_LEGAL) {
               if (super.getListIdentifier() == Constants.ZERO_VAR) {
                   left = players.get(players.size() - 1);
                   right = players.get(Constants.THE_SPECIAL_ONE);
               }
               if (super.getListIdentifier()
                       == players.get(players.size() - 1).getListIdentifier()
                       && super.getListIdentifier() != Constants.ZERO_VAR) {
                   left = players.get(players.size() - 2);
                   right = players.get(Constants.ZERO_VAR);
               } else {
                  if (super.getListIdentifier() != 0
                          && super.getListIdentifier()
                          != players.get(players.size() - 1).getListIdentifier()) {
                   left = players.get(super.getListIdentifier() - 1);
                   right = players.get(super.getListIdentifier() + 1);
                  }
               }
           }
           //System.out.println(left.getList_identifier());
           //System.out.println(super.getList_identifier());
           //System.out.println(right.getList_identifier());

       }
       /**@sheriffPlay */
    public void sheriffPlay(final ArrayList<Goods> cardPack, final ArrayList<Player> players) {
           findToCheck(players);
           if (right == left) {
               if (super.getSumOfMoney() >= Constants.MINIMUM_ALLOWED) {
                   super.checkGoods(cardPack, right);
               }
               right.organizeSackWithGoods();
           }
           if (right != left) {
               if (super.getSumOfMoney() >= Constants.MINIMUM_ALLOWED) {
                   super.checkGoods(cardPack, right);
                   super.checkGoods(cardPack, left);
               }
               right.organizeSackWithGoods();
               left.organizeSackWithGoods();
           }

           for (int i = 0; i < players.size(); i++) {
               if (players.get(i).getListIdentifier() != super.getListIdentifier()) {
                   if (players.get(i).getListIdentifier() != left.getListIdentifier()
                           && players.get(i).getListIdentifier()
                           != right.getListIdentifier()) {
                       if (super.getSumOfMoney() >= Constants.MINIMUM_ALLOWED) {
                           if (players.get(i).getBribe() > 0) {
                               super.changeMoneySum(players.get(i).getBribe());
                               players.get(i).changeMoneySum(-players.get(i).getBribe());
                           }
                       }
                       players.get(i).organizeSackWithGoods();
                   }
               }
           }
    }

}
