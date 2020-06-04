package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

	//Det er svært at huske på, at vi ikke behøver gemme alle tidligere kort. Vi skal bare have
	//info fra billedet og det øverste kort...
	private ArrayList<Card>
			//The 7 rows of cards to be moved around and combined - disse skal måske være arrays.
			tableauDecks1, tableauDecks2, tableauDecks3, tableauDecks4, tableauDecks5,
			tableauDecks6, tableauDecks7;

	private Card topDeckCard, //The drawn card from the top of the deck
	//The 4 decks of cards in the top right corner beginning with an Ace card
	foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;

	public void initiateGame() {
		initiateCardsArray();
		setGameState();

		/*Så vi husker hvordan det hele skal opsættes:

		-Check først for Ace i bunken, der vendes
		-Check derefter for Ace forrest i de 7 rækker kort
		-Check så efter om der er nogen Ace i de 4 "tårne".
		-Hvis der er det, så check efter et kort, der er +1 af kortet på toppen af disse 4 - dette
		skal loopes igennem for hver bunke af kulør.
		-Her kommer det virkeligt svære - vi skal tjekke, om kortene i de 7 rækker kan flyttes rundt.
		-Hvis ingen af disse returnerer "true", så skal der vendes et kort fra bunken, og der tjekkes forfra igen.
		*/




		// Run of the game
		int i = 0;
		while (i < 1) {
			i++;
			setGameState();
				if (!checkTopDeckForAce())
					continue;
				else if (!checkTableauDecksForAce())
					continue;
				else if (checkTableauToFoundationHearts())
					continue;
		}
	}

	private void initiateCardsArray() {
		//Fra venstre mod højre
		tableauDecks1 = new ArrayList<>();
		tableauDecks2 = new ArrayList<>();
		tableauDecks3 = new ArrayList<>();
		tableauDecks4 = new ArrayList<>();
		tableauDecks5 = new ArrayList<>();
		tableauDecks6 = new ArrayList<>();
		tableauDecks7 = new ArrayList<>();
	}

	// TODO - This must be implemented, when we get data from a picture - take a snapshot of the current cards
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

		//Da alle disse bunker er tomme fra start.
		foundationsDeckDiamonds = new Card();
		foundationsDeckHearts = new Card();
		foundationsDeckClubs = new Card();
		foundationsDeckSpades = new Card();
	}

	private boolean checkTopDeckForAce() {
		if (topDeckCard.getValue() == 1) {
			switch (topDeckCard.getSuit()) {
				case "Diamonds":
					foundationsDeckDiamonds = topDeckCard;
					System.out.println("Move the Ace from the talon to the first foundation pile.");
					return true;
				case "Hearts":
					foundationsDeckHearts = topDeckCard;
					System.out.println("Move the Ace from the talon to the second foundation pile.");
					return true;
				case "Clubs":
					foundationsDeckClubs = topDeckCard;
					System.out.println("Move the Ace from the talon to the third foundation pile.");
					return true;
				case "Spades":
					foundationsDeckSpades = topDeckCard;
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
	
	private boolean checkTableauToFoundationHearts(){
		//if (tableauDecks1.get(tableau)
		
		
		
		return false;
	}
}

