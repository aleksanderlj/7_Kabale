package com.example.a7_kabale.logic;

import android.content.SyncStatusObserver;

import com.example.a7_kabale.logic.deepLogic.CheckAces;
import com.example.a7_kabale.logic.deepLogic.CheckTabToFou;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

//DET BLIVER NOGLE VILDE LOOPS DET HER!!!!!

public class GameEngine {

	private ArrayList<ArrayList<Card>> tableauDecks;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;

	public void initiateGame() {
		initiateCardsArray();
		setGameState();
		
		// Run of the game
		int i = 0;
		while (i < 1) {
			i++;
			setGameState();
			CheckAces checkAces = new CheckAces(tableauDecks, topDeckCard, foundationsDeckDiamonds,
					foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
			CheckTabToFou checkTabToFou = new CheckTabToFou(tableauDecks, topDeckCard, foundationsDeckDiamonds,
					foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
				if (checkAces.checkTopDeckForAce())
					continue;
				else if (checkAces.checkTableauDecksForAce())
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
	
	private boolean checkTableauToFoundationHearts(){

		/*
		Gentages for hver kulør.

		Check først om foundationpile ikke er null. Tjek herefter værdien af kortet.
		Undersøg om der findes et kort i hvert tableaudeck, som er præcis 1 større end kortet i foundationpile.
		*/

		//if (tableauDecks1.get(tableau)
		
		
		
		return false;
	}
}

