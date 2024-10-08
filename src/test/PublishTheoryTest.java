package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.AlchemyMarker;
import domain.Aspect;
import domain.Ingredient;
import domain.PublicationTrack;
import domain.Theory;

/**
 * This class is for testing publishTheory method of PublicationTrack class
 * 
 * @author Yiğit Can Ateş
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PublishTheoryTest {
	PublicationTrack publicationTrack;
	
	// JUnit 5 setup before all tests
    @BeforeAll
    public void setUpBeforeAll() {
        System.out.println("This is a test for publishTheory() method from PublicationTrack class.");
    }

    // JUnit 5 setup before each test
	@BeforeEach
	void setUp(){
		publicationTrack = new PublicationTrack();
		publicationTrack.populateTrack();
		assertNotNull(publicationTrack, "getInstance() returns null");
		assertTrue(publicationTrack instanceof PublicationTrack, "getInstance() does not return PublicationTrack");
	}

	@Test // This tests that the correct Theory instance is created and publishedTheories are updated.
	final void testTheoryInstanceCreated() {
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		Ingredient mushroom = new Ingredient("mushroom", 6, as4, as2, as3);
		
		AlchemyMarker marker2 = new AlchemyMarker(as1, as1, as1, 2);
		
		int playerNo = 1;
		
		publicationTrack.publishTheory(mushroom, marker2, playerNo);
		
		assertEquals(publicationTrack.getPublishedTheories().get(0).getIngredient(), mushroom);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getMarker(), marker2);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getPlayerNo(), playerNo);
	}
	
	@Test // This tests that the correct Ingredient is removed from availableIngredients.
	final void testRemoveAvailableIngredient() {
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		Ingredient flower = new Ingredient("flower", 7, as4, as3, as1);
		
		AlchemyMarker marker7 = new AlchemyMarker(as4, as3, as1, 7);
		
		int playerNo = 4;
		
		publicationTrack.publishTheory(flower, marker7, playerNo);
		
		assertFalse(publicationTrack.getAvailableIngredients().contains(flower));
		
	}
	
	@Test // This tests that the correct AlchemyMarker is removed from availableAlchemies.
	final void testRemoveAvailableAlchemies() {
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		Ingredient root = new Ingredient("root", 8, as2, as3, as4);
		
		AlchemyMarker marker6 = new AlchemyMarker(as4, as2, as3, 6);
		
		int playerNo = 3;
		
		publicationTrack.publishTheory(root, marker6, playerNo);
		
		assertFalse(publicationTrack.getAvailableAlchemies().contains(marker6));
	}
	
	@Test // This tests multiple theory publication case.
	final void testMultipleTheories() {
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		Ingredient scorpion = new Ingredient("scorpion", 3, as3, as1, as4);
		Ingredient fern = new Ingredient("fern", 4, as2, as2, as2);
		Ingredient feather = new Ingredient("feather", 5, as1, as4, as3);
		
		AlchemyMarker marker1 = new AlchemyMarker(as3, as1, as4, 1);
		AlchemyMarker marker2 = new AlchemyMarker(as1, as1, as1, 2);
		AlchemyMarker marker3 = new AlchemyMarker(as3, as1, as4, 3);
		
		int playerNo = 4;
		
		publicationTrack.publishTheory(scorpion, marker2, playerNo);
		publicationTrack.publishTheory(fern, marker1, playerNo);
		publicationTrack.publishTheory(feather, marker3, playerNo);
		
		assertEquals(publicationTrack.getPublishedTheories().get(0).getIngredient(), scorpion);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getMarker(), marker2);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getPlayerNo(), playerNo);
		
		assertEquals(publicationTrack.getPublishedTheories().get(1).getIngredient(), fern);
		assertEquals(publicationTrack.getPublishedTheories().get(1).getMarker(), marker1);
		assertEquals(publicationTrack.getPublishedTheories().get(1).getPlayerNo(), playerNo);
		
		assertEquals(publicationTrack.getPublishedTheories().get(2).getIngredient(), feather);
		assertEquals(publicationTrack.getPublishedTheories().get(2).getMarker(), marker3);
		assertEquals(publicationTrack.getPublishedTheories().get(2).getPlayerNo(), playerNo);
	
	}
	
	@Test // This tests multiple theory publication case.
	final void testMultipleTheories2() {
		
		Aspect as1 = new Aspect(true, true);
		Aspect as2 = new Aspect(true, false);
		Aspect as3 = new Aspect(false, false);
		Aspect as4 = new Aspect(false, true);
		
		Ingredient flower = new Ingredient("flower", 7, as4, as3, as1);
		Ingredient root = new Ingredient("root", 8, as2, as3, as4);
		
		AlchemyMarker marker4 = new AlchemyMarker(as2, as2, as2, 4);
		AlchemyMarker marker5 = new AlchemyMarker(as1, as4, as3, 5);
		
		int playerNo1 = 1;
		int playerNo2 = 2;
		
		publicationTrack.publishTheory(flower, marker4, playerNo2);
		publicationTrack.publishTheory(root, marker5, playerNo1);
		
		assertEquals(publicationTrack.getPublishedTheories().get(0).getIngredient(), flower);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getMarker(), marker4);
		assertEquals(publicationTrack.getPublishedTheories().get(0).getPlayerNo(), playerNo2);
		
		assertEquals(publicationTrack.getPublishedTheories().get(1).getIngredient(), root);
		assertEquals(publicationTrack.getPublishedTheories().get(1).getMarker(), marker5);
		assertEquals(publicationTrack.getPublishedTheories().get(1).getPlayerNo(), playerNo1);
		
		assertFalse(publicationTrack.getAvailableIngredients().contains(flower));
		assertFalse(publicationTrack.getAvailableIngredients().contains(root));
		
		assertFalse(publicationTrack.getAvailableAlchemies().contains(marker4));
		assertFalse(publicationTrack.getAvailableAlchemies().contains(marker5));
		
	}
	
}
