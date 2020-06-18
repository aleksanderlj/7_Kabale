package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

public class CheckKings {
	
    private LogicState logicState;

    public CheckKings(){}
    
    public Card[] checkForKing(){
        Integer freeDeck = freeTableauRow();
        if (freeDeck == null)
        	return null;
        else if (logicState.getTopDeckCard().getValue() == 13){
        	logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			return new Card[] {logicState.getTopDeckCard(), logicState.getTableauRows().get(freeDeck).get(0)};
        }
        else {
        	int highestKing = 0, tableauKing = 0;
        	for (int i = 0; i < logicState.getTableauRows().size(); i++){
        		if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
					if (logicState.getTableauRows().get(i).get(0).getValue() == 13 && highestKing < logicState.getHiddenCards()[i]) {
						highestKing = logicState.getHiddenCards()[i];
						tableauKing = i;
					}
				}
			}
        	if (highestKing != 0){
				int[] newHiddenCards = logicState.getHiddenCards();
				newHiddenCards[highestKing] = newHiddenCards[highestKing]-1;
				logicState.setHiddenCards(logicState.getHiddenCards());
				return new Card[] {logicState.getTableauRows().get(tableauKing).get(0),logicState.getTableauRows().get(freeDeck).get(0)};
			}
		}
       	return null;
    }
    
    private Integer freeTableauRow(){
        for (int i = 0; i < logicState.getTableauRows().size(); i++){
            if (logicState.getTableauRows().get(i).get(0).getValue() != 0 && logicState.getHiddenCards()[i] == 0)
                return i;
        }
        return null;
    }
	
	public void setLogicState(LogicState logicState) {
		this.logicState = logicState;
	}
}
