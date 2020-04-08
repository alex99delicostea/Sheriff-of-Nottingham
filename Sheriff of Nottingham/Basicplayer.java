package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;

import java.util.ArrayList;

public class Basicplayer extends Player {
    /** @sheriffPlay */
    public void sheriffPlay(final ArrayList<Goods> cardPack, final ArrayList<Player> players) {
         for (int i = 0; i < players.size(); i++) {
             if (players.get(i).getListIdentifier() != super.getListIdentifier()
                     && super.getSumOfMoney() >= Constants.MINIMUM_ALLOWED) {
                 super.checkGoods(cardPack, players.get(i));
                 players.get(i).organizeSackWithGoods();
             } else {
                 players.get(i).organizeSackWithGoods();
             }
         }

    }
    /** @comerciantPlay */
    public void comerciantPlay() {
       int[] freq = new int[Constants.MAX_INDEX_OF_GOOD + 1];
       int max = 0;
       int profMax = 0;
       //int max_id = 0;
       for (int i = 0; i < super.getGoodsInHand().size(); i++) {
           if (super.getGoodsInHand().get(i).getType() == GoodsType.Legal) {
                   freq[super.getGoodsInHand().get(i).getId()]++;
           }
       }

       for (int i = 0; i < super.getGoodsInHand().size(); i++) {
           int profGood = super.getGoodsInHand().get(i).getProfit();
          if (super.getGoodsInHand().get(i).getType() == GoodsType.Legal) {
              if ((freq[super.getGoodsInHand().get(i).getId()] > max)
                      || (freq[super.getGoodsInHand().get(i).getId()] == max
                      && profGood > super.getDeclaredGood().getProfit())) {
                  max = freq[super.getGoodsInHand().get(i).getId()];
                  profMax = super.getGoodsInHand().get(i).getProfit();
                  super.setDeclaredGood(super.getGoodsInHand().get(i));
              }
              if (freq[super.getGoodsInHand().get(i).getId()] == max && profGood == profMax) {
                  if (super.getGoodsInHand().get(i).getId() > super.getDeclaredGood().getId()) {
                      super.setDeclaredGood(super.getGoodsInHand().get(i));
                  }
              }
          }
       }

        for (int i = 0; i < super.getGoodsInHand().size(); i++) {
            try {
              if (super.getSackWithGoods().size() < Constants.MAX_IN_BAG) {
                  if (super.getGoodsInHand().get(i).getId() == super.getDeclaredGood().getId()) {
                      super.getSackWithGoods().add(super.getGoodsInHand().get(i));
                  }
              }
            } catch (NullPointerException e) {

            }

        }
        //int e = 0;
        if (super.getSackWithGoods().size() == 0) {
            //e++;
            int maxProfitOnIllegalCard = 0;
            Goods goodToAdd = null;
            for (int i = 0; i < super.getGoodsInHand().size(); i++) {
              if (super.getGoodsInHand().get(i).getType() == GoodsType.Illegal) {
                  if (super.getGoodsInHand().get(i).getProfit() > maxProfitOnIllegalCard
                          && super.getSumOfMoney() >= Constants.PENALTY_FOR_ILLEGAL) {
                      maxProfitOnIllegalCard = super.getGoodsInHand().get(i).getProfit();
                      goodToAdd  = super.getGoodsInHand().get(i);
                  }
              }
            }
            //System.out.println(good_to_add.getId());
         if (goodToAdd  != null) {
             super.getGoodsInHand().remove(goodToAdd);
             super.setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.ZERO_VAR));
             super.getSackWithGoods().add(goodToAdd);
         }
            //assert good_to_add != null;
            //System.out.println(good_to_add.getId());
        }
        //System.out.println(super.getDeclared_Good().getId());
        //System.out.println(e);
    }
}
