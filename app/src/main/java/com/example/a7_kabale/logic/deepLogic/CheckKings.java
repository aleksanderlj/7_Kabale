package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;

import java.util.ArrayList;

public class CheckKings {

    private ArrayList<ArrayList<Card>> tableauDecks;
    private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades, cardToSearchFor;

    public CheckKings(ArrayList<ArrayList<Card>> tableauDecks, Card topDeckCard, Card foundationsDeckDiamonds, Card foundationsDeckHearts,
                         Card foundationsDeckClubs, Card foundationsDeckSpades) {
        this.tableauDecks = tableauDecks;
        this.topDeckCard = topDeckCard;
        this.foundationsDeckDiamonds = foundationsDeckDiamonds;
        this.foundationsDeckHearts = foundationsDeckHearts;
        this.foundationsDeckClubs = foundationsDeckClubs;
        this.foundationsDeckSpades = foundationsDeckSpades;
    }
    public boolean checkForKing(){
        Integer freeDeck = freeTableauDeck();
        if (freeDeck!= null && topDeckCard.getValue() == 13){
			System.out.println("Move king from top deck, to tableau deck " + freeDeck);
			return true;
        }
        else if (freeDeck != null){
        	for (int i = 0; i < tableauDecks.size(); i++){
        		for (int j = 1; j < tableauDecks.get(i).size(); j++){
        			if (tableauDecks.get(i).get(j).getValue() == 13) {
						System.out.println("Move king from tableau deck: " + (i + 1) + " to tableau deck: " + (freeDeck + 1));
						return true;
        			}
				}
			}
		}
       	return false;
    }
    
    private Integer freeTableauDeck(){
        for (int i = 0; i < tableauDecks.size(); i++){
            if (tableauDecks.get(i).size() == 0)
                return i;
        }
        return null;
    }
}
