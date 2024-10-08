/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Aspect;
import domain.Ingredient;
import domain.IngredientDeck;

class IngredientDeckTest {
	private static IngredientDeck deck;

	@BeforeAll
	static void setUpBeforeClass() {
		deck = new IngredientDeck();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		deck = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		deck.initializeIngredientDeck();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Test method for {@link domain.IngredientDeck#populateIngredientDeck()}.
	 */
	@Test
	final void testInitializeIngredientDeck() {
		assertTrue(deck.repOk());
		assertEquals(8, deck.getCardNum());
	}

	/**
	 * Test method for {@link domain.IngredientDeck#pop()}.
	 */
	@Test
	final void testPop() {
		Ingredient popped = deck.pop();
		assertTrue(deck.repOk());
		assertEquals(7, deck.getCardNum());
	}

	/**
	 * Test method for {@link domain.IngredientDeck#putCard(domain.Ingredient)}.
	 */
	@Test
	final void testPutCard() {
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Ingredient ingr = new Ingredient("test", 1, as1, as2, as3);
		deck.putCard(ingr);
		assertTrue(deck.repOk());
		assertEquals(9, deck.getCardNum());
		assertTrue(deck.isIn(ingr));
	}

	
	/**
	 * Test method for {@link domain.IngredientDeck#getTopThreeCards()}
	 */
	@Test
	final void testGetTopThreeCards() {
		List<Ingredient> temp_list =  deck.getTopThreeCards();
		Ingredient temp_ingr_1 = deck.pop();
		Ingredient temp_ingr_2 = deck.pop();
		Ingredient temp_ingr_3 = deck.pop();
		assertTrue(deck.repOk());
		assertEquals(5, deck.getCardNum());
		
		assertTrue( temp_list.contains(temp_ingr_1) );
		assertTrue( temp_list.contains(temp_ingr_2) );
		assertTrue( temp_list.contains(temp_ingr_3) );
	}
	
	/**
	 * Test method for a combination of operations
	 */
	@Test
	final void testWithCombination() {
		
		deck.pop(); deck.pop(); deck.pop(); deck.pop(); deck.pop(); deck.pop(); deck.pop(); deck.pop();
		assertEquals(0, deck.getCardNum());
		assertTrue(deck.repOk());
		
		assertEquals(null, deck.pop());
		
		deck.pop(); deck.pop();
		assertEquals(0, deck.getCardNum());
		assertTrue(deck.repOk());
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Ingredient ingr = new Ingredient("test", 1, as1, as2, as3);
		deck.putCard(ingr);
		deck.putCard(ingr);
		deck.putCard(ingr);
		deck.putCard(ingr);
		
		assertEquals(4, deck.getCardNum());
		assertTrue(deck.repOk());
		
		
	}
	

}
