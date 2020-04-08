package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.IllegalGoods;
import com.tema1.helpers.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player {
      private List<Goods> goodsInHand;
      private List<Goods> sackWithGoods;
      private Map<Goods, Integer> table;
      private int bribe;
      private int sumOfMoney;
      private Goods declaredGood;
      private int listIdentifier;

      public Player() {
          sumOfMoney = Constants.INITIAL_COINS;
          bribe = 0;
          listIdentifier = -1;
          declaredGood = null;
          goodsInHand = new ArrayList<Goods>();
          sackWithGoods = new ArrayList<Goods>();
          table = new HashMap<Goods, Integer>();
          for (int i = 0; i <= Constants.MAX_INDEX_OF_GOOD; i++) {
              table.put(GoodsFactory.getInstance().getGoodsById(i), 0);
          }
      }
      /**@getDeclaredGood */
      public Goods getDeclaredGood() {

          return declaredGood;
      }
      /**@setBribe */
      public void setBribe(final int bribe) {
          this.bribe = bribe;
      }
      /**@getBribe */
      public int getBribe() {
          return bribe;
      }
      /**@changeMoneySum */
      public void changeMoneySum(final int sum) {
          sumOfMoney += sum;
      }
      /**@getSackWithGoods */
      public List<Goods> getSackWithGoods() {
          return  sackWithGoods;
      }
      /**@setSackWithGoods */
      public void setSackWithGoods(final ArrayList<Goods> goods) {
          this.sackWithGoods = goods;
      }
      /**@getSumOfMoney() */
      public int getSumOfMoney() {
          return sumOfMoney;
      }
      /** @getGoodsInHand */
      public List<Goods> getGoodsInHand() {
          return goodsInHand;
      }
      /** @setDeclaredGood */
      public void setDeclaredGood(final Goods declaredGood) {
          this.declaredGood = declaredGood;
    }
    /**@getListIdentifier */
      public int getListIdentifier() {
          return listIdentifier;
      }
      /**@setListIdentifier */
      public void setListIdentifier(final int listIdentifier) {
          this.listIdentifier = listIdentifier;
      }
      /**@getTable */
      public Map<Goods, Integer> getTable() {
          return table;
      }
    /**@takeCards */
    public void takeCards(final ArrayList<Goods> cardPack) {
          if (!goodsInHand.isEmpty()) {
              goodsInHand.clear();
          }
          for (int i = 0; i < Constants.CARDS_TAKEN; i++) {
              goodsInHand.add(cardPack.get(Constants.ZERO_VAR));
              //System.out.println(Goods_in_hand);
              cardPack.remove(Constants.ZERO_VAR);

          }
      }
      /**@checkGoods */
      public void checkGoods(final ArrayList<Goods> cardPack, final Player p) {
          int ok = 1;
          int sumFromPenalties = 0;
          ArrayList<Goods> a = new ArrayList<Goods>(Constants.MAX_IN_BAG);
          int illegalSignal = 0;
            for (Goods good : p.getSackWithGoods()) {
                //System.out.println(good.getId());
                if (good.getType() == GoodsType.Illegal || good != p.getDeclaredGood()) {
                    //illegal_signal = 1;
                    ok = 0;
                    sumFromPenalties += good.getPenalty();
                    cardPack.add(good);
                    a.add(good);
                    //p.getSack_with_goods().remove(good);
                }
            }

      /*for(int j = 0; j < a.size(); j++){
           System.out.println(a.get(j).getId());
       }*/
          //System.out.println();
          int e = 0;
          if (ok == 1) {
              for (Goods good : p.getSackWithGoods()) {
                  e++;
                  //System.out.println(good.getId());
                  sumFromPenalties -= good.getPenalty();
              }
          }
          //System.out.println(e);
          p.getSackWithGoods().removeAll(a);
          /*for(int j = 0; j < p.getSack_with_goods().size(); j++){
              System.out.println(p.getSack_with_goods().get(j).getId());
          }*/
          this.changeMoneySum(sumFromPenalties);
          p.changeMoneySum(-sumFromPenalties);
          //System.out.println(p.Sum_of_Money);

      }
      /**@organizeSackWithGoods */
      public void organizeSackWithGoods() {
          for (Goods good : sackWithGoods) {
              if (good.getType() == GoodsType.Legal) {
                   table.put(good, table.get(good) + 1);
              }
              if (good.getType() == GoodsType.Illegal) {
                  table.put(good, table.get(good) + 1);
                  IllegalGoods illegal = (IllegalGoods) good;
                  Map<Goods, Integer> bonusItemsFromIllegal = illegal.getIllegalBonus();
                  for (Map.Entry<Goods, Integer> entry : bonusItemsFromIllegal.entrySet()) {
                      int all = entry.getValue() - 1;
                      while (all >= 0) {
                          table.put(entry.getKey(), table.get(entry.getKey()) + 1);
                          all--;
                      }

                  }

              }


          }
          /*for(int i = 0; i < Sack_with_goods.size(); i++){
              System.out.println(Sack_with_goods.get(i).getId());
          }*/
          sackWithGoods.clear();
      }
      public abstract void sheriffPlay(ArrayList<Goods> cardPack, ArrayList<Player> players);
      public abstract void comerciantPlay();
}
