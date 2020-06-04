package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;

import java.util.ArrayList;

public class CheckTabToFou {
	
	private ArrayList<ArrayList<Card>> tableauDecks;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	
	public CheckTabToFou(ArrayList<ArrayList<Card>> tableauDecks, Card topDeckCard, Card foundationsDeckDiamonds, Card foundationsDeckHearts,
					 Card foundationsDeckClubs, Card foundationsDeckSpades) {
		this.tableauDecks = tableauDecks;
		this.topDeckCard = topDeckCard;
		this.foundationsDeckDiamonds = foundationsDeckDiamonds;
		this.foundationsDeckHearts = foundationsDeckHearts;
		this.foundationsDeckClubs = foundationsDeckClubs;
		this.foundationsDeckSpades = foundationsDeckSpades;
	}
	
	public boolean CheckTableauToFoundation(){
		return false;
	}
	
	// private metoder
}
