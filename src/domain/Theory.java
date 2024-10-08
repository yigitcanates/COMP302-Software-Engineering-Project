package domain;

import java.io.Serializable;

public class Theory implements Serializable {
	
	private Ingredient ingredient;
	private AlchemyMarker marker;
	private int playerNo;

	public Theory(Ingredient ingr, AlchemyMarker marker, int playerNum) {
		this.ingredient = ingr;
		this.marker = marker;
		this.playerNo = playerNum;
	}
	
	//num: represents the aspect that is targeted to be proven wrong
	public int tryDebunk(int num) {
		if(num==1) { //redAspect was chosen to debunk
			if (this.ingredient.getRedAspect().isSign()==this.marker.getRedAspect().isSign()) {
				return -1; // unsuccessful debunk
			}
			else {
				return 1; //successful debunk
			}
		}
		if(num==2) { //greenAspect was chosen to debunk
			if (this.ingredient.getGreenAspect().isSign()==this.marker.getGreenAspect().isSign()) {
				return -1; // unsuccessful debunk
			}
			else {
				return 1; //successful debunk
			}
		}
		if(num==3) { //blueAspect was chosen to debunk
			if (this.ingredient.getBlueAspect().isSign()==this.marker.getBlueAspect().isSign()) {
				return -1; // unsuccessful debunk
			}
			else {
				return 1; //successful debunk
			}
		}
		return 0;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public AlchemyMarker getMarker() {
		return marker;
	}

	public void setMarker(AlchemyMarker marker) {
		this.marker = marker;
	}

	public int getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	
}
