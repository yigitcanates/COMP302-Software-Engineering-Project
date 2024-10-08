package domain;

public class PotionMaker {
	private static PotionMaker pm;
	Ingredient ingr1;
	Ingredient ingr2;
    private Potion potion;

    
    public PotionMaker() {
	}
    
    public static PotionMaker getInstance() {
		if (pm == null) pm = new PotionMaker();
        return pm;
	}

	public Potion mixIngredients() {
		boolean ingr1redsize = ingr1.getRedAspect().isCircleSize();
		boolean ingr1redsign = ingr1.getRedAspect().isSign();
		boolean ingr1greensize = ingr1.getGreenAspect().isCircleSize();
		boolean ingr1greensign = ingr1.getGreenAspect().isSign();
		boolean ingr1bluesize = ingr1.getBlueAspect().isCircleSize();
		boolean ingr1bluesign = ingr1.getBlueAspect().isSign();
		
		boolean ingr2redsize = ingr2.getRedAspect().isCircleSize();
		boolean ingr2redsign = ingr2.getRedAspect().isSign();
		boolean ingr2greensize = ingr2.getGreenAspect().isCircleSize();
		boolean ingr2greensign = ingr2.getGreenAspect().isSign();
		boolean ingr2bluesize = ingr2.getBlueAspect().isCircleSize();
		boolean ingr2bluesign = ingr2.getBlueAspect().isSign();
		
		int quality = 0;
		
		if (ingr1redsize == !ingr2redsize) {
			if(ingr1redsign == ingr2redsign) {
				if(ingr1redsign) {
					quality=1;
				}
				else {
					quality=-1;
				}
			}
		}
		
		else if (ingr1greensize == !ingr2greensize) {
			if(ingr1greensign == ingr2greensign) {
				if(ingr1greensign) {
					quality=1;
				}
				else {
					quality=-1;
				}
			}
		}
		
		else if (ingr1bluesize == !ingr2bluesize) {
			if(ingr1bluesign == ingr2bluesign) {
				if(ingr1bluesign) {
					quality=1;
				}
				else {
					quality=-1;
				}
			}
		}
		
		Potion pt = new Potion(quality);
		
		this.potion = pt;
		
		return pt;
    }
	
	public int tryPotion(String experimental) {
		
		if(this.potion.getQuality()==-1) {
			if (experimental.equals("student")){
				return 0;
			}
			else {
				return 1; //try on yourself
			}
		}
		
		return -1; //positive or neutral potion
		
	}
	
	public Ingredient getIngr1() {
		return ingr1;
	}

	public void setIngr1(Ingredient ingr1) {
		this.ingr1 = ingr1;
	}

	public Ingredient getIngr2() {
		return ingr2;
	}

	public void setIngr2(Ingredient ingr2) {
		this.ingr2 = ingr2;
	}
}
