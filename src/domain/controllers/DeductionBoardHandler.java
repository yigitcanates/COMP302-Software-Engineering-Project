package domain.controllers;

import domain.DBListener;
import domain.DeductionBoard;
import domain.KUAlchemistsGame;
import domain.Potion;

public class DeductionBoardHandler{
	
    public DeductionBoardHandler(){
    }

    public void markResultsTriangle(int index, int quality) {
    	System.out.println("handler received from ui");
    	DeductionBoard DBoard;
    	DBoard = KUAlchemistsGame.getInstance().getCurrentPlayer().getdBoard();
    	DBoard.markResultsTriangle(index, new Potion(quality));
    	System.out.println("handler sent to dboard");
    }

    public void markDeductionGrid(int ingrIndex, int markerIndex) {
    	DeductionBoard DBoard;
    	DBoard = KUAlchemistsGame.getInstance().getCurrentPlayer().getdBoard();
    	DBoard.markDeductionGrid(ingrIndex, markerIndex);	
    }
    
    public void addListener(DBListener lis) {
    	DeductionBoard DBoard;
    	DBoard = KUAlchemistsGame.getInstance().getCurrentPlayer().getdBoard();
		DBoard.addDBListener(lis);
	}
}