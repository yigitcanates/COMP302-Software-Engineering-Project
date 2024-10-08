package domain.controllers;

import java.util.List;

import domain.AlchemyMarker;
import domain.Aspect;
import domain.Ingredient;
import domain.KUAlchemistsGame;
import domain.Player;
import domain.PublicationTrack;
import domain.Theory;

public class PublicationHandler {
	
	public void makePublication(String ingr, String marker, int playerNo){
		
		Ingredient ingredient = PublicationTrack.getInstance().getIngredientByName(ingr);
		AlchemyMarker alchemyMarker = PublicationTrack.getInstance().getMarkerByName(marker);
		
		PublicationTrack.getInstance().publishTheory(ingredient, alchemyMarker, playerNo);
		KUAlchemistsGame.getInstance().getPlayer(playerNo).decreaseGold(1);
		KUAlchemistsGame.getInstance().getPlayer(playerNo).increaseReputation(1);
	}
	
	public List<AlchemyMarker> getAvailableAlchemies() {
		return PublicationTrack.getInstance().getAvailableAlchemies();
	}

	public List<Ingredient> getAvailableIngredients() {
		return PublicationTrack.getInstance().getAvailableIngredients();
	}
	
	public List<Theory> getPublishedTheories() {
		return PublicationTrack.getInstance().getPublishedTheories();
	}
	
	//theory: published theory chosen to debunk
	//num: represents the aspect that is targeted to be proven wrong
	//num=1 --> redAspect, num==2 -->greenAspect, num==3 --> blueAspect
	public int debunkTheory(Theory theory, int num, int debunkingPlayerNo) {
		int result = theory.tryDebunk(num); //the chosen aspect is checked here 
		
		int debunkedPlayerNo = theory.getPlayerNo();
		
		//successful debunk 
		if (result==1) {
			KUAlchemistsGame.getInstance().getPlayer(debunkingPlayerNo).increaseReputation(2);
			KUAlchemistsGame.getInstance().getPlayer(debunkedPlayerNo).increaseReputation(-1);
			
			KUAlchemistsGame.getInstance().addRecentlyDebunkedPlayer(
					KUAlchemistsGame.getInstance().getPlayer(debunkedPlayerNo).getUsername());
			
			PublicationTrack.getInstance().removeTheory(theory); //removes the published theory since its debunked 
		}
		
		//unsuccessful debunk 
		if (result==-1) {
			KUAlchemistsGame.getInstance().getPlayer(debunkingPlayerNo).increaseReputation(-1);
		}
		
		return result;
	}
}