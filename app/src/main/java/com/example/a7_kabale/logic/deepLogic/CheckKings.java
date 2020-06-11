package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

import java.util.ArrayList;

public class CheckKings {
	
    private LogicState logicState;

    public CheckKings(LogicState logicState) {
        this.logicState = logicState;
    }
    
    public boolean checkForKing(){
        Integer freeDeck = freeTableauRow();
        if (freeDeck == null)
        	return false;
        else if (logicState.getTopDeckCard().getValue() == 13){
        	logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			System.out.println("Move " + logicState.getTopDeckCard().toString() + " from top deck to tableau row " + freeDeck);
			return true;
        }
        else {
        	int highestKing = 0, tableauKing = 0;
        	for (int i = 0; i < logicState.getTableauRows().size(); i++){
        		if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(0).getValue() == 13 && highestKing < logicState.getHiddenCards()[i]) {
						highestKing = logicState.getHiddenCards()[i];
						tableauKing = i;
					}
				}
			}
        	if (highestKing != 0){
				System.out.println("Move " + "king" + " from tableau row " +
						(tableauKing + 1) + " to tableau row " + (freeDeck + 1));
				int[] newHiddenCards = logicState.getHiddenCards();
				newHiddenCards[highestKing] = newHiddenCards[highestKing]-1;
				logicState.setHiddenCards(logicState.getHiddenCards());
				return true;
			}
		}
       	return false;
    }
    
    private Integer freeTableauRow(){
        for (int i = 0; i < logicState.getTableauRows().size(); i++){
            if (logicState.getTableauRows().get(i).size() == 0 && logicState.getHiddenCards()[i] == 0)
                return i;
        }
        return null;
    }
}
