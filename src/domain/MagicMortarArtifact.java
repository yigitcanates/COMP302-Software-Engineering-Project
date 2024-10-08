package domain;

import java.util.List;

public class MagicMortarArtifact implements ArtifactBehavior<Ingredient> {

	@Override
	public void useCard(Ingredient ingr) {
		
		// card is already discarded by the MakeExperimentHandler
		// we need to add it back to the artifacts list of the player
		KUAlchemistsGame.getInstance().getCurrentPlayer().addIngredient(ingr);
	}
}