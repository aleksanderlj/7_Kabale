package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
	private List<Card> cardDeck;
	
	public void initiateGame(){
		cardDeck = initiateCardDeck();
		for (Card card : cardDeck){
			System.out.println(card);
		}
	}
	
	private List<Card> initiateCardDeck(){
		List<Card> cardDeck = new ArrayList<>();
		
		for(int i = 0; i < 52; i++){
			if (i < 13){
				cardDeck.add(new Card((i % 13), "Hearts"));
			}
			else if (i < 26){
				cardDeck.add(new Card(i % 13, "Diamonds"));
			}
			else if (i < 39){
				cardDeck.add(new Card(i % 13, "Clubs"));
			}
			else{
				cardDeck.add(new Card(i % 13, "Spades"));
			}
		}
		return cardDeck;
	}
	
}
