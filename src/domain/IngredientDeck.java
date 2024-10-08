package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Overview: This class provides a List of Ingredients to store all ingredient cards found in the Ingredient Deck.
 *
 */
public class IngredientDeck {
	
	private static IngredientDeck instance;
    private int cardNum;
    private List<Ingredient> ingredients ;
    
    // Abstraction Function
    // AF(c) = { c.ingredients[i].ingredient | 0 <= i < c.cardNum }
    
	// The rep invariant is 
	// c.ingredients not null &&
    // cardNum >= 0 &&
	// all elements of c.ingredients are Ingredients
    
    public static IngredientDeck getInstance() {
    	
		if (instance == null) {
			instance = new IngredientDeck();
		}
		return instance;
	}

    public IngredientDeck() {
    	// EFFECTS: Initializes this to be empty
    	this.ingredients = new ArrayList<Ingredient>();
    	this.cardNum = 0;
	}
    
    public void initializeIngredientDeck() {
    	// MODIFIES: this.ingredients, this.cardNum
    	// EFFECTS: adds ingredients to this.ingredient
		
    	this.ingredients = new ArrayList<Ingredient>();
    	this.cardNum = 0;
    	
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		//Ingredient(name, ID, red, green, blue)
		// add 5 of each card into the ingredient deck
		for (int i = 0; i < 5; i++) {
			Ingredient rat = new Ingredient("rat", 1, as3, as1, as4);
			Ingredient bird = new Ingredient("bird", 2, as1, as1, as1);
			Ingredient garlic = new Ingredient("garlic", 3, as3, as4, as2);
			Ingredient clover = new Ingredient("clover", 4, as2, as2, as2);
			Ingredient aloevera = new Ingredient("aloevera", 5, as1, as4, as3);
			Ingredient mushroom = new Ingredient("mushroom", 6, as4, as2, as3);
			Ingredient flower = new Ingredient("flower", 7, as4, as3, as1);
			Ingredient bluelotus = new Ingredient("bluelotus", 8, as2, as3, as4);
			
			putCard(rat);
			putCard(bird);
			putCard(garlic);
			putCard(clover);
			putCard(aloevera);
			putCard(mushroom);
			putCard(flower);
			putCard(bluelotus);
			
		}
		Collections.shuffle(this.ingredients);
    }

    
    // for Elixir of insight
    public List<Ingredient> getTopThreeCards(){
    	// EFFECTS: returns a list of top three elements of this.ingredients
    	if (cardNum < 3) {
    		return null;
    	}
    	else {
    		Ingredient ingr;
    		List<Ingredient> topThreeIngredientList = new ArrayList<>();
    		for (int i = 1; i < 4 ; i++) {
    			ingr = ingredients.get(cardNum - i);
    			topThreeIngredientList.add(ingr);
    		}
    		return topThreeIngredientList;
    	}
    }
    
	public Ingredient pop() {
		// MODIFIES: this.ingredients, this.cardNum
		// EFFECTS: Removes top ingredient from this.ingredients, returns top ingredient
		if (cardNum > 0) {
			Ingredient ingr = ingredients.get(cardNum-1);
			this.ingredients.remove(cardNum - 1);
			this.cardNum--;
	    	return ingr;
		}
		else {
			System.out.println("No cards to draw in the ingredients deck");
			return null;
		}
    }
	
	public void putCard(Ingredient ingr) {
		// MODIFIES: this.ingredients, this.cardNum
		// EFFECTS: Adds ingr to the elements of this.ingredients
		this.ingredients.add(ingr);
		this.cardNum++;
	}
	
//	public void removeCard(Ingredient ingr) {
//		// MODIFIES: this.ingredients, this.cardNum
//		// EFFECTS: Removes ingr from this.ingredients
//		this.ingredients.remove(ingr);
//		this.cardNum--;
//	}
	
	public boolean isIn(Ingredient ingr) {
		// EFFECTS: Returns true if ingr is in this.ingredients else returns false.
		return this.ingredients.contains(ingr);
	}
	
	
	// getter for ingredients
	public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // Setter method for ingredients
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    // Getter method for cardNum
    public int getCardNum() {
        return cardNum;
    }

    // Setter method for cardNum
    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

	@Override
	public String toString() {
		return "IngredientDeck [cardNum=" + cardNum + ", ingredients=" + ingredients + "]";
	}
	
	public boolean repOk() {
		if (this.ingredients == null) return false;
		if (this.cardNum < 0) return false;
		for (int i = 0; i < this.cardNum; i++) {
			Object x = this.ingredients.get(i);
			if (!(x instanceof Ingredient)) return false;
			}
		return true;
	}
}