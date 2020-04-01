package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
	private Card[] foundationsDeck; //The 4 decks of cards in the top right corner beginning with an Ace card

	private ArrayList<Card>
			tableauDecks1, tableauDecks2, tableauDecks3, tableauDecks4, tableauDecks5,
			tableauDecks6, tableauDecks7; //The 7 rows of cards to be moved around and combined

	private Card topDeckCard; //The drawn card from the top of the deck
	
	public void initiateGame(){
		initiateCards();
		setGameState();
		checkTopDeckForAce();
		
	}
	
	private void initiateCards(){
		foundationsDeck = new Card[3];

		//Fra venstre mod højre
		tableauDecks1 = new ArrayList<>();
		tableauDecks2 = new ArrayList<>();
		tableauDecks3 = new ArrayList<>();
		tableauDecks4 = new ArrayList<>();
		tableauDecks5 = new ArrayList<>();
		tableauDecks6 = new ArrayList<>();
		tableauDecks7 = new ArrayList<>();
	}
	
	// TODO - This must be implemented, when we get data from a picture
	private void setGameState(){
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.

		topDeckCard = new Card(1, "Diamonds"); //Ace of Diamonds

		//Made from the picture in our Discord chat:
		tableauDecks1.add(new Card(13, "Diamonds"));
		tableauDecks2.add(new Card(4, "Diamonds"));
		tableauDecks3.add(new Card(13, "Spades"));
		tableauDecks4.add(new Card(10, "Spades"));
		tableauDecks5.add(new Card(7, "Spades"));
		tableauDecks6.add(new Card(8, "Spades"));
		tableauDecks7.add(new Card(12, "Hearts"));
	}

	private void checkTopDeckForAce(){
		if (topDeckCard.getValue() == 1){
			switch (topDeckCard.getSuit()){
				case "Diamonds":
					foundationsDeck[0] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					break;
				case "Hearts":
					foundationsDeck[1] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					break;
				case "Clubs":
					foundationsDeck[2] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					break;
				case "Spades":
					foundationsDeck[3] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					break;
			}
		}
	}

	private void checkTableauDecksForAce(){
		if (tableauDecks1.contains(Card))
	}
}
