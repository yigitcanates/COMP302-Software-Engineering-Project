package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class DeductionBoard implements Serializable {
    private Potion[] resultsTriangle;
    private Boolean[][] deductionGrid;
    private List<DBListener> DBListeners;
    
    public Potion[] getResultsTriangle() {
		return resultsTriangle;
	}

	public void setResultsTriangle(Potion[] resultsTriangle) {
		this.resultsTriangle = resultsTriangle;
	}

	public Boolean[][] getDeductionGrid() {
		return deductionGrid;
	}

	public void setDeductionGrid(Boolean[][] deductionGrid) {
		this.deductionGrid = deductionGrid;
	}

	public DeductionBoard() {
    	
		this.DBListeners = new ArrayList<>();
    	// elements initially null
    	// structure:
    	//        [1]
    	//      [2] [3]
    	//    [4] [5] [6] ...
    	this.resultsTriangle = new Potion[28];
    	
    	// all elements initialized to false, no red mark
    	this.deductionGrid = new Boolean[8][];
    	for (int i = 0; i < 8; i++) {
            this.deductionGrid[i] = new Boolean[8];
            for (int k = 0; k < 8; k++) {
            	this.deductionGrid[i][k] = false;
            }
        }
	}

	public void markResultsTriangle(int index, Potion pt) {
		resultsTriangle[index] = pt;
		System.out.println("db received from handler and set the array");
		publishDBEvent();
    }

    public void markDeductionGrid(int ingrIndex, int markerIndex) {
    	deductionGrid[ingrIndex][markerIndex] = !deductionGrid[ingrIndex][markerIndex];
    	System.out.println("deduction grid marked");
    	publishDBEvent();
    }
    
    public void addDBListener(DBListener lis) {
    	DBListeners.add(lis);
	}
    
    public void publishDBEvent() {
    	
		for(DBListener l: DBListeners) {
			l.onDBChange();
			System.out.println("published by db");
		}
	}
}
