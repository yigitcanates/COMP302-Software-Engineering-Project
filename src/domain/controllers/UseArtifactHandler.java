package domain.controllers;

import domain.ArtifactBehavior;
import domain.ArtifactCard;
import domain.ElixirOfInsightArtifact;
import domain.KUAlchemistsGame;
import domain.MagicMortarArtifact;
import domain.PrintingPressArtifact;
import domain.WisdomIdolArtifact;

public class UseArtifactHandler {
	ArtifactBehavior artifactBehavior;
	
	public UseArtifactHandler() {
	}
	
	public void useArtifact(ArtifactCard artifactCard) {
		
		if (artifactCard.getID() == 0) {
			artifactBehavior = new ElixirOfInsightArtifact();
			System.out.println("changed to ei");
		}
		if (artifactCard.getID() == 1) {
			artifactBehavior = new PrintingPressArtifact();
			System.out.println("changed to pp");
		}
		if (artifactCard.getID() == 2) {
			artifactBehavior = new MagicMortarArtifact();
			System.out.println("changed to mm");
		}
		if (artifactCard.getID() == 3) {
			artifactBehavior = new WisdomIdolArtifact();
			System.out.println("changed to wi");
		}
		
		KUAlchemistsGame.getInstance().getCurrentPlayer().removeArtifactCard(artifactCard.getID());
	}
	
	public <T> void performArtifact(T element) {
		artifactBehavior.useCard(element);
	}
}