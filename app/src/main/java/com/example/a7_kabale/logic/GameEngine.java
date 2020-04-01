package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
	private Card[] aceDecks;
	private List<Card>[] stackDecks;
	private Card shownCard;
	
	public void initiateGame(){
		initiateCards();
		//setGameState();
		
	}
	
	private void initiateCards(){
		aceDecks = new Card[4];
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
