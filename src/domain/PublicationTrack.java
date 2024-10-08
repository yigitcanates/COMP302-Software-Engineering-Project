package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PublicationTrack {
	
	private static PublicationTrack instance;
	
    private List<AlchemyMarker> availableAlchemies;
    private List<Ingredient> availableIngredients;
    private List<Theory> publishedTheories;
    
    private List<PubListener> pubListeners;
    
    
	public static PublicationTrack getInstance() {
		if (instance == null) {
			instance = new PublicationTrack();
		}
		return instance;
	}
    
	public PublicationTrack() {
		this.availableAlchemies = new ArrayList<AlchemyMarker>();
		this.availableIngredients = new ArrayList<Ingredient>();
		this.publishedTheories = new ArrayList<Theory>();
		this.pubListeners = new ArrayList<PubListener>();
	}
	
	/**
	 * REQUIRES: Ingredient instance, AlchemyMarker instance, playerNo as int.
	 * MODIFIES: publishedTheories, availableIngredients, publishedTheories
	 * EFFECTS: A new Theory instance is created.
	 * 			This theory instance is added to the publishedTheories.
	 * 			Ingredient used in the Theory is removed from availableIngredients.
	 * 			AlchemyMarker used in the Theory is removed from availableAlchemies.
	 * 			Publication Event is published to PubListeners.
	 */
	public void publishTheory(Ingredient ingr, AlchemyMarker marker, int playerNo) {
		Theory publishedTheory = new Theory(ingr, marker, playerNo);
		publishedTheories.add(publishedTheory);
		removeAvailableIngr(ingr);
		removeAvailableMarker(marker);
		publishPublicationEvent();
	}

	public void publishPublicationEvent() {
		for (PubListener l: pubListeners) {
			l.onPubChange();
		}
		
	}

	private void removeAvailableMarker(AlchemyMarker marker) {
		availableAlchemies.remove(marker);
	}

	private void removeAvailableIngr(Ingredient ingr) {
		availableIngredients.remove(ingr);
		
	}

	public void populateTrack() {
		populateAvailableAlchemies();
		populateAvailableIngredients();
	}

	public List<AlchemyMarker> getAvailableAlchemies() {
		return availableAlchemies;
	}

	public void setAvailableAlchemies(List<AlchemyMarker> availableAlchemies) {
		this.availableAlchemies = availableAlchemies;
	}

	public List<Ingredient> getAvailableIngredients() {
		return availableIngredients;
	}

	public void setAvailableIngredients(List<Ingredient> availableIngredients) {
		this.availableIngredients = availableIngredients;
	}

	public List<Theory> getPublishedTheories() {
		return publishedTheories;
	}

	public void setPublishedTheories(List<Theory> publishedTheories) {
		this.publishedTheories = publishedTheories;
	}

	private void populateAvailableIngredients() {
		
		List<Ingredient> ingrs = new ArrayList<Ingredient>();
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		//Ingredient(name, ID, red, green, blue)
		Ingredient rat = new Ingredient("rat", 1, as3, as1, as4);
		Ingredient bird = new Ingredient("bird", 2, as1, as1, as1);
		Ingredient garlic = new Ingredient("garlic", 3, as3, as4, as2);
		Ingredient clover = new Ingredient("clover", 4, as2, as2, as2);
		Ingredient aloevera = new Ingredient("aloevera", 5, as1, as4, as3);
		Ingredient mushroom = new Ingredient("mushroom", 6, as4, as2, as3);
		Ingredient flower = new Ingredient("flower", 7, as4, as3, as1);
		Ingredient bluelotus = new Ingredient("bluelotus", 8, as2, as3, as4);
		
		ingrs.add(rat);
		ingrs.add(bird);
		ingrs.add(garlic);
		ingrs.add(clover);
		ingrs.add(aloevera);
		ingrs.add(mushroom);
		ingrs.add(flower);
		ingrs.add(bluelotus);
		
		this.availableIngredients = ingrs;
	}

	private void populateAvailableAlchemies() {
		
		List<AlchemyMarker> markers = new ArrayList<AlchemyMarker>();
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		//AlchemyMarker(red, green, blue, id)
		AlchemyMarker marker1 = new AlchemyMarker(as3, as1, as4, 1);
		AlchemyMarker marker2 = new AlchemyMarker(as1, as1, as1, 2);
		AlchemyMarker marker3 = new AlchemyMarker(as3, as4, as2, 3);
		AlchemyMarker marker4 = new AlchemyMarker(as2, as2, as2, 4);
		AlchemyMarker marker5 = new AlchemyMarker(as1, as4, as3, 5);
		AlchemyMarker marker6 = new AlchemyMarker(as4, as2, as3, 6);
		AlchemyMarker marker7 = new AlchemyMarker(as4, as3, as1, 7);
		AlchemyMarker marker8 = new AlchemyMarker(as2, as3, as4, 8);
		
		markers.add(marker1);
		markers.add(marker2);
		markers.add(marker3);
		markers.add(marker4);
		markers.add(marker5);
		markers.add(marker6);
		markers.add(marker7);
		markers.add(marker8);
		
		this.availableAlchemies = markers;	
	}

	public Ingredient getIngredientByName(String ingr) {
		
		for (int i=0; i<availableIngredients.size(); i++) {
			if (availableIngredients.get(i).getName().equals(ingr)) {
				return availableIngredients.get(i);
			}
		}
		return null;
	}

	public AlchemyMarker getMarkerByName(String marker) {
		
		for (int i=0; i<availableAlchemies.size(); i++) {
			if (availableAlchemies.get(i).getID() == (Integer.parseInt(marker))) {
				return availableAlchemies.get(i);
			}
		}
		return null;
	}

	public void addPubListener(PubListener e) {
		pubListeners.add(e);
	}
	
	public void removeTheory(Theory theory) {
		AlchemyMarker tempAlchMark = theory.getMarker();
		Ingredient tempIngr = theory.getIngredient();
		
		publishedTheories.remove(theory);
		availableAlchemies.add(tempAlchMark);
		availableIngredients.add(tempIngr);
		publishPublicationEvent();
		
	}
}
