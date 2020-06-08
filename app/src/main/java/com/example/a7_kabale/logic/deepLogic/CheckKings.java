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
        if (freeDeck!= null){
            
        }
        
        
        
        return true;
    }
    
    private Integer freeTableauDeck(){
        for (int i = 0; i < tableauDecks.size(); i++){
            if (tableauDecks.get(i).size() == 0)
                return i;
        }
        return null;
    }
}
