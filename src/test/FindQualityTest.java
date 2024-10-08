package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import domain.Aspect;
import domain.Ingredient;
import domain.PotionSale;

/**
 * @author Eray Cakar
 */

public class FindQualityTest {
	
	Aspect a = new Aspect(true, true);
	Aspect b = new Aspect(false, true);
	Aspect c = new Aspect(true, false);
	Aspect d = new Aspect(false, false);
	
	PotionSale ps = new PotionSale(new Ingredient("", 0 , a, a, a), new Ingredient("", 0 , a, a, a), 0);
	
	@Test //test for null entries
	public void expectedNullPointerException() {
		assertThrows(NullPointerException.class, ()-> {
		    ps.findQuality(null, null); 
		});
		Ingredient ingr1 = new Ingredient(null, 0 ,null, null, null);
		Ingredient ingr2 = new Ingredient("2", 0 , a, b, c);
		Ingredient ingr3 = new Ingredient("3", 0 , null, b, c);
		assertThrows(NullPointerException.class, ()-> {
		    ps.findQuality(ingr1, ingr2); 
		});
		assertThrows(NullPointerException.class, ()-> {
		    ps.findQuality(ingr1, ingr1); 
		});
		assertThrows(NullPointerException.class, ()-> {
		    ps.findQuality(ingr2, ingr3); 
		});
	}
	
	@Test //test with ingredients with all aspects equal
	public void testWithSameIngredient() {
		Ingredient ingr1 = new Ingredient("1", 0 , a, a, a);
		Ingredient ingr2 = new Ingredient("2", 0 , a, b, c);
		Ingredient ingr3 = new Ingredient("3", 0 , a, b, c);
		assertEquals(0, ps.findQuality(ingr1, ingr1));
		assertEquals(0, ps.findQuality(ingr2, ingr3));
	}
	
	@Test //test with aspects of same size --> return 0
	public void testWithSameSize() {
		Ingredient ingr1 = new Ingredient("1", 0 , a, b, a);
		Ingredient ingr2 = new Ingredient("2", 0 , c, d, c);
		assertEquals(0, ps.findQuality(ingr1, ingr2));
		
		Ingredient ingr3 = new Ingredient("3", 0 , a, c, c);
		Ingredient ingr4 = new Ingredient("4", 0 , c, a, c);
		assertEquals(0, ps.findQuality(ingr3, ingr4));
	}
	
	@Test //test with aspects of different size different sign --> return 0
	public void testWithDifferentSign() {
		Ingredient ingr1 = new Ingredient("1", 0 , a, b, a);
		Ingredient ingr2 = new Ingredient("2", 0 , d, b, a);
		assertEquals(0, ps.findQuality(ingr1, ingr2));
		
		Ingredient ingr3 = new Ingredient("3", 0 , a, b, c);
		Ingredient ingr4 = new Ingredient("4", 0 , a, c, c);
		assertEquals(0, ps.findQuality(ingr3, ingr4));
		
		Ingredient ingr5 = new Ingredient("5", 0 , a, b, c);
		Ingredient ingr6 = new Ingredient("6", 0 , a, b, b);
		assertEquals(0, ps.findQuality(ingr5, ingr6));
		
		Ingredient ingr7 = new Ingredient("7", 0 , a, b, c);
		Ingredient ingr8 = new Ingredient("8", 0 , d, c, b);
		assertEquals(0, ps.findQuality(ingr7, ingr8));
	}
	
	@Test //test with aspects of different size same sign --> 1 if sign == true, -1 if sign == false
	public void testWithSameSign() {
		Ingredient ingr1 = new Ingredient("1", 0 , a, b, a);
		Ingredient ingr2 = new Ingredient("2", 0 , b, b, a);
		assertEquals(1, ps.findQuality(ingr1, ingr2));
		
		Ingredient ingr3 = new Ingredient("3", 0 , a, b, c);
		Ingredient ingr4 = new Ingredient("4", 0 , a, a, c);
		assertEquals(1, ps.findQuality(ingr3, ingr4));
		
		Ingredient ingr5 = new Ingredient("5", 0 , a, b, c);
		Ingredient ingr6 = new Ingredient("6", 0 , a, b, d);
		assertEquals(-1, ps.findQuality(ingr5, ingr6));
		
		Ingredient ingr7 = new Ingredient("7", 0 , a, b, c);
		Ingredient ingr8 = new Ingredient("8", 0 , d, c, d);
		assertEquals(-1, ps.findQuality(ingr7, ingr8));
		
		Ingredient ingr9 = new Ingredient("9", 0 , a, b, d);
		Ingredient ingr10 = new Ingredient("10", 0 , b, c, d);
		assertEquals(1, ps.findQuality(ingr9, ingr10));
	}
}
