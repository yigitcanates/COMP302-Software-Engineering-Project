package domain;

import java.io.Serializable;

public class Potion implements Serializable {
    private int quality;
    String image;
    
	public Potion(int quality) {
		super();
		this.quality = quality;
		
    	switch (quality) {
    	case 1: 
    		this.image="images/positivepotion.png";
    		break;
    	case -1:
    		this.image="images/negativepotion.png";
    		break;
    	case 0:
    		this.image="images/neutralpotion.png";
    		break;
    	}
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	
}
