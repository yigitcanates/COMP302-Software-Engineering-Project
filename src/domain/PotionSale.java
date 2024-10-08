package domain;

import java.io.Serializable;

public class PotionSale implements Serializable {
    private int prediction;
    private Potion potion;

    public PotionSale(Ingredient ingr1, Ingredient ingr2, int prediction) {
        this.prediction = prediction;
        int quality = findQuality(ingr1, ingr2);
        this.potion = new Potion(quality);
    }

	public Potion getPotion() {
		return potion;
	}

	public void setPotion(Potion potion) {
		this.potion = potion;
	}

	public int getPrediction() {
		return prediction;
	}

	public void setPrediction(int prediction) {
		this.prediction = prediction;
	}
	
public int findQuality(Ingredient ingr1, Ingredient ingr2) {
	//REQUIRES: ingr1, ingr2, their attributes are not null		
	//EFFECTS: for the same attribute of ingr1 and ingr2 if their size attributes are different
	//		   and if their sign attributes are the same returns 1 if their sign attributes are 'true',
	//		   -1 if their sign attributes are 'false',
	//		   0 when there is no attribute of ingr1 and ingr2 with opposite size and identical sign attributes. 												
	
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
		
		if (ingr1greensize == !ingr2greensize) {
			if(ingr1greensign == ingr2greensign) {
				if(ingr1greensign) {
					quality=1;
				}
				else {
					quality=-1;
				}
			}
		}
		
		if (ingr1bluesize == !ingr2bluesize) {
			if(ingr1bluesign == ingr2bluesign) {
				if(ingr1bluesign) {
					quality=1;
				}
				else {
					quality=-1;
				}
			}
		}
		return quality;
	}
	

    
}