package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

import java.util.ArrayList;

public class CheckKings {

    private ArrayList<ArrayList<Card>> tableauRows;
    private Card topDeckCard;

    public CheckKings(LogicState logicState) {
        this.tableauRows = logicState.getTableauRows();
        this.topDeckCard = logicState.getTopDeckCard();
    }
    
    public boolean checkForKing(){
        Integer freeDeck = freeTableauRow();
        if (freeDeck == null)
        	return false;
        else if (topDeckCard.getValue() == 13){
			System.out.println("Move " + topDeckCard.toString() + " from top deck to tableau row " + freeDeck);
			return true;
        }
        else {
        	int highestKing = 0, tableauKing = 0;
        	for (int i = 0; i < tableauRows.size(); i++){
        		for (int j = 1; j < tableauRows.get(i).size(); j++){
        			if (tableauRows.get(i).get(j).getValue() == 13 && highestKing < j) {
        				highestKing = j;
        				tableauKing = i;
        			}
				}
			}
        	if (highestKing != 0){
				System.out.println("Move " + topDeckCard.toString() + " from tableau row " + (tableauKing + 1) + " to tableau row " + (freeDeck + 1));
				return true;
			}
		}
       	return false;
    }
    
    private Integer freeTableauRow(){
        for (int i = 0; i < tableauRows.size(); i++){
            if (tableauRows.get(i).size() == 0)
                return i;
        }
        return null;
    }
}
