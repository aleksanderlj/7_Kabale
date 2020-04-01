package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
	private List<Card> allCards;
	private List<Card>[] aceDecks;
	private List<Card>[] cardDecks;
	private List<Card>[] stackDecks;
	private int hiddenCards;
	private Card shownCard;
	
	public void initiateGame(){
		initiateCards();
		//setGameState();
		
	}
	
	
	private void initiateCards(){
		allCards = new ArrayList<>();
		for(int i = 0; i < 52; i++){
			if (i < 13)
				allCards.add(new Card(i % 13, "Hearts"));
			else if (i < 26)
				allCards.add(new Card(i % 13, "Diamonds"));
			else if (i < 39)
				allCards.add(new Card(i % 13, "Clubs"));
			else
				allCards.add(new Card(i % 13, "Spades"));
		}
		aceDecks[0] = new ArrayList<>();
		aceDecks[1] = new ArrayList<>();
		aceDecks[2] = new ArrayList<>();
		aceDecks[3] = new ArrayList<>();
		stackDecks[0] = new ArrayList<>();
		stackDecks[1] = new ArrayList<>();
		stackDecks[2] = new ArrayList<>();
		stackDecks[3] = new ArrayList<>();
		stackDecks[4] = new ArrayList<>();
		stackDecks[5] = new ArrayList<>();
		stackDecks[6] = new ArrayList<>();
	}
	
	// TODO - This must be implemented, when we get data from a picture
	private void setGameState(){
	
	}
}
