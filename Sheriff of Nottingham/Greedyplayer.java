package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;

import java.util.ArrayList;

public class Greedyplayer extends Basicplayer {
    public static int round = 0;
    /**@comerciantPlay */
    public void comerciantPlay() {
        super.comerciantPlay();
        //System.out.println(super.getDeclared_Good().getId());
        if (super.getSackWithGoods().size() < Constants.MAX_IN_BAG && (round % 2 == 0)) {
            int maxProfitOnIllegalCard = 0;
            Goods goodToAdd = null;
            for (int i = 0; i < super.getGoodsInHand().size(); i++) {
                if (super.getGoodsInHand().get(i).getType() == GoodsType.Illegal) {
                    if (super.getGoodsInHand().get(i).getProfit() > maxProfitOnIllegalCard
                            && super.getSumOfMoney() >= Constants.PENALTY_FOR_ILLEGAL) {
                        maxProfitOnIllegalCard = super.getGoodsInHand().get(i).getProfit();
                        goodToAdd = super.getGoodsInHand().get(i);
                        //System.out.println(good_to_add.getId());
                    }
                }
            }
         if (goodToAdd != null) {
             super.getGoodsInHand().remove(goodToAdd);
             //super.setDeclared_Good(GoodsFactory.getInstance().getGoodsById(0));
             super.getSackWithGoods().add(goodToAdd);
         }
            //System.out.println(super.getDeclared_Good().getId());
            //System.out.println(good_to_add.getId());

        }
        //round++;
        //System.out.println(round);
    }
    /**@sheriffPlay */
    public void sheriffPlay(final ArrayList<Goods> cardPack, final ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getListIdentifier() != super.getListIdentifier()
                    && players.get(i).getBribe() > 0
                    && super.getSumOfMoney() >= Constants.MINIMUM_ALLOWED) {
                super.changeMoneySum(players.get(i).getBribe());
                players.get(i).changeMoneySum(-players.get(i).getBribe());
                players.get(i).organizeSackWithGoods();
            } else {
                super.checkGoods(cardPack, players.get(i));
                players.get(i).organizeSackWithGoods();
            }
        }

    }
}
