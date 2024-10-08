package domain.controllers;

import domain.Ingredient;
import domain.Player;
import domain.PotionSale;

public class SellPotionHandler {
	
	public int sellPotion(Ingredient ingr1, Ingredient ingr2, int prediction, Player p) {
		PotionSale ps = new PotionSale(ingr1, ingr2, prediction);
		int quality = ps.getPotion().getQuality();
		updatePlayerGoldReputation(ps, p);
		p.discardIngredients(ingr1, ingr2);
		return quality;
	}
	
	public void updatePlayerGoldReputation(PotionSale ps, Player p) {
		int quality = ps.getPotion().getQuality();
		int prediction = ps.getPrediction();
		
    	if(prediction==1) { //positive prediction
    		if(quality==1) { //positive potion
    			p.increaseGold(3);
    		}
    		if(quality==-1) { //negative potion
    			p.increaseReputation(-1);
    		}
    	}
    	
    	if(prediction==0) { //positive/neutral prediction
    		if(quality==1 || quality==0) { //positive or neutral potion
    			p.increaseGold(2);
    		}
    		if(quality==-1) { //negative potion
    			p.increaseReputation(-1);
    		}
    	}
    	
    	if(prediction==-1) { //'may be negative' prediction
    		p.increaseGold(1);
    	}
    	
	}
}
