package domain;

import java.util.List;

public class ElixirOfInsightArtifact implements ArtifactBehavior<List<Ingredient>> {
	@Override
	public void useCard(List<Ingredient> list) {
		/**
		 * REQUIRES
		 * - The list should contain exactly 3 Ingredient objects.
		 * 
		 * MODIFIES
		 * - The ingredient deck by removing the top three ingredients and adding the provided list of ingredients.
		 * 
		 * EFFECTS
		 * - Removes top three ingredients from the ingredient deck.
		 * - Adds the provided list of ingredients to the ingredient deck in the order they appear in the input list.
		 */
				
		IngredientDeck ingrDeck = IngredientDeck.getInstance();
		int cardNum = ingrDeck.getCardNum();
		
		if (cardNum < 3) {
			throw new IllegalStateException("Invalid state: Ingredient Deck Cannot have less that 3 cards");
		}
		if (list == null) {
			throw new IllegalArgumentException("List must have 3 elements");
		}
		int listSize = list.size();
		if (listSize != 3) {
			throw new IllegalArgumentException("List must have 3 elements");
		}
		
		// remove the top three ingredients from ingredient deck
		for (int i = 1; i < 4; i++) {
			ingrDeck.getIngredients().remove(cardNum - i);
		}
		
		// insert the ingredients in the new order
		for (Ingredient ingr : (List <Ingredient>) list) {
			ingrDeck.getIngredients().add(ingr);
		}
		
	}

}
