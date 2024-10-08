package domain;

import java.io.Serializable;

public class AlchemyMarker implements Serializable {
    private Aspect redAspect;
    private Aspect greenAspect;
    private Aspect blueAspect;
    private int ID;
    private String image;
    
	public AlchemyMarker(Aspect redAspect, Aspect greenAspect, Aspect blueAspect, int ID) {
		this.redAspect = redAspect;
		this.blueAspect = blueAspect;
		this.greenAspect = greenAspect;
		this.ID = ID;
		switch(ID) {
		case 1:
			this.image="images/alchemy-rat.png";
			break;
		case 2:
			this.image="images/alchemy-bird.png";
			break;
		case 3:
			this.image="images/alchemy-garlic.png";
			break;
		case 4:
			this.image="images/alchemy-clover.png";
			break;
		case 5:
			this.image="images/alchemy-aloevera.png";
			break;
		case 6:
			this.image="images/alchemy-mushroom.png";
			break;
		case 7:
			this.image="images/alchemy-flower.png";
			break;
		case 8:
			this.image="images/alchemy-bluelotus.png";
			break;
		}
		
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Aspect getRedAspect() {
		return redAspect;
	}

	public void setRedAspect(Aspect redAspect) {
		this.redAspect = redAspect;
	}

	public Aspect getBlueAspect() {
		return blueAspect;
	}

	public void setBlueAspect(Aspect blueAspect) {
		this.blueAspect = blueAspect;
	}

	public Aspect getGreenAspect() {
		return greenAspect;
	}

	public void setGreenAspect(Aspect greenAspect) {
		this.greenAspect = greenAspect;
	}
	
}
