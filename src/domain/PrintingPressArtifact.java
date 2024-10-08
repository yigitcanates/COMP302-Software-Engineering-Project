package domain;

public class PrintingPressArtifact implements ArtifactBehavior<Integer> {

	@Override
	public void useCard(Integer item) {
		KUAlchemistsGame.getInstance().getCurrentPlayer().increaseGold(item);
	}
}