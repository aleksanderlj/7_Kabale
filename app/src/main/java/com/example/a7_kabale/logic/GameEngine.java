package com.example.a7_kabale.logic;

import android.content.SyncStatusObserver;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

//DET BLIVER NOGLE VILDE LOOPS DET HER!!!!!

public class GameEngine {

	//Det er svært at huske på, at vi ikke behøver gemme alle tidligere kort. Vi skal bare have
	//info fra billedet og det øverste kort...

	private ArrayList<ArrayList<Card>> tableauDecks;

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
				if (checkTopDeckForAce())
					continue;
				else if (checkTableauDecksForAce())
					continue;
				else if (checkTableauToFoundationHearts())
					continue;
		}
	}

	private void initiateCardsArray() {
		//Fra venstre mod højre
		tableauDecks = new ArrayList<>();
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
		tableauDecks.add(new ArrayList<Card>());
	}

	// TODO - This must be implemented, when we get data from a picture - take a snapshot of the current cards
	private void setGameState() {
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.

		topDeckCard = new Card(4, "Diamonds"); //Ace of Diamonds

		//Made from the picture in our Discord chat:
		tableauDecks.get(0).add((new Card(13, "Diamonds")));
		tableauDecks.get(1).add(new Card(4, "Diamonds"));
		tableauDecks.get(2).add(new Card(13, "Spades"));
		tableauDecks.get(3).add(new Card(1, "Spades"));
		tableauDecks.get(4).add(new Card(7, "Spades"));
		tableauDecks.get(5).add(new Card(8, "Spades"));
		tableauDecks.get(6).add(new Card(12, "Hearts"));

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
					System.out.println("Move the Diamonds Ace from the topdeck to the first foundation pile.");
					return true;
				case "Hearts":
					foundationsDeckHearts = topDeckCard;
					System.out.println("Move the Hearts Ace from the topdeck to the second foundation pile.");
					return true;
				case "Clubs":
					foundationsDeckClubs = topDeckCard;
					System.out.println("Move the Clubs Ace from the topdeck to the third foundation pile.");
					return true;
				case "Spades":
					foundationsDeckSpades = topDeckCard;
					System.out.println("Move the Spades Ace from the topdeck to the fourth foundation pile.");
					return true;
			}
		}
		return false;
	}

	private boolean checkTableauDecksForAce() {
		int tableauNumber;
		for (int i = 0; i < tableauDecks.size(); i++) {
			tableauNumber = i + 1;
			if (tableauDecks.get(i).get(tableauDecks.get(i).size() - 1).getValue() == 1) {
				switch (tableauDecks.get(i).get(tableauDecks.get(i).size() - 1).getSuit()) {
					case "Diamonds":
						System.out.println("Move the Diamonds Ace from tableaudeck " + tableauNumber + " to the first foundation pile.");
						return true;
					case "Hearts":
						System.out.println("Move the Hearts Ace from the tableaudeck " + tableauNumber + " to the second foundation pile.");
						return true;
					case "Clubs":
						System.out.println("Move the Clubs Ace from the tableaudeck " + tableauNumber + " to the third foundation pile.");
						return true;
					case "Spades":
						System.out.println("Move the Spades Ace from the tableaudeck " + tableauNumber + " to the fourth foundation pile.");
						return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkTableauToFoundationHearts(){
		//if (tableauDecks1.get(tableau)
		
		
		
		return false;
	}
}

