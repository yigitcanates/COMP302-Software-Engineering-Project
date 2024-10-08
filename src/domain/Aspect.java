package domain;

import java.io.Serializable;

public class Aspect implements Serializable {
	private boolean sign;
	private boolean circleSize;

	public Aspect(boolean circleSize, boolean sign) {
		this.sign = sign;
		this.circleSize = circleSize;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public boolean isCircleSize() {
		return circleSize;
	}

	public void setCircleSize(boolean circleSize) {
		this.circleSize = circleSize;
	}
	

}
