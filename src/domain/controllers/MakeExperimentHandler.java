package domain.controllers;

import domain.Ingredient;
import domain.Player;
import domain.Potion;
import domain.PotionMaker;

public class MakeExperimentHandler {

    public MakeExperimentHandler() {
    }

    public void makeExperiment(Ingredient ingr1, Ingredient ingr2, String experimental, Player player) {
    	
    	System.out.println("in exp handler: ingr1: "+ingr1.toString()+"ingr 2: " +ingr2.toString());
    	PotionMaker.getInstance().setIngr1(ingr1);
    	PotionMaker.getInstance().setIngr2(ingr2);
    	
		Potion potion = PotionMaker.getInstance().mixIngredients();
		
		int tryResult = PotionMaker.getInstance().tryPotion(experimental);
		if(tryResult==0) {
			player.decreaseGold(1);
		}
		if(tryResult==1) {
			player.increaseSickness(1);
		}
		player.discardIngredients(ingr1, ingr2); // change to implement magic mortar
		player.addPotion(potion);
    }
}