package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
	private Card[] foundationsDeck; //The 4 decks of cards in the top right corner beginning with an Ace card

	private ArrayList<Card>
			tableauDecks1, tableauDecks2, tableauDecks3, tableauDecks4, tableauDecks5,
			tableauDecks6, tableauDecks7; //The 7 rows of cards to be moved around and combined

	private Card topDeckCard; //The drawn card from the top of the deck

	public void initiateGame() {
		initiateCards();
		setGameState();

		if (!checkTopDeckForAce()) {
			if (!checkTableauDecksForAce()) {
				System.out.println("Næste træk etc.");
			}
		}


		// Run of the game
		while (true) {
			setGameState();
			checkTopDeckForAce();
			checkTableauDecksForAce();
		}
	}

	private void initiateCards() {
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
	private void setGameState() {
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

	private boolean checkTopDeckForAce() {
		if (topDeckCard.getValue() == 1) {
			switch (topDeckCard.getSuit()) {
				case "Diamonds":
					foundationsDeck[0] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					System.out.println("Move the Ace from the talon to the first foundation pile.");
					return true;
				case "Hearts":
					foundationsDeck[1] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					System.out.println("Move the Ace from the talon to the second foundation pile.");
					return true;
				case "Clubs":
					foundationsDeck[2] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					System.out.println("Move the Ace from the talon to the third foundation pile.");
					return true;
				case "Spades":
					foundationsDeck[3] = topDeckCard;
					topDeckCard = new Card(4, "Hearts");
					System.out.println("Move the Ace from the talon to the fourth foundation pile.");
					return true;
			}
		}
		return false;
	}

	private boolean checkTableauDecksForAce() {
		switch (tableauDecks1.get(tableauDecks1.size() - 1).getValue()) {
			case 1:
				System.out.println("Move ace from tableau deck 1, to the correct foundation pile.");
				return true;
			case 2:
				System.out.println("Move ace from tableau deck 2, to the correct foundation pile.");
				return true;
			case 3:
				System.out.println("Move ace from tableau deck 3, to the correct foundation pile.");
				return true;
			case 4:
				System.out.println("Move ace from tableau deck 4, to the correct foundation pile.");
				return true;
			case 5:
				System.out.println("Move ace from tableau deck 5, to the correct foundation pile.");
				return true;
			case 6:
				System.out.println("Move ace from tableau deck 6, to the correct foundation pile.");
				return true;
			case 7:
				System.out.println("Move ace from tableau deck 7, to the correct foundation pile.");
				return true;
		}
		return false;
	}
}

