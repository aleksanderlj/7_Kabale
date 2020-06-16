package com.example.a7_kabale.logic;

import com.example.a7_kabale.logic.deepLogic.CheckAces;
import com.example.a7_kabale.logic.deepLogic.CheckKings;
import com.example.a7_kabale.logic.deepLogic.CheckTabToFou;
import com.example.a7_kabale.logic.deepLogic.TableauMovement;

import java.util.ArrayList;

//DET BLIVER NOGLE VILDE LOOPS DET HER!!!!!

public class GameEngine {
	private LogicState logicState = new LogicState();
	private ArrayList<ArrayList<Card>> tableauRows = new ArrayList<>();
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	private CheckAces checkAces = new CheckAces();
	private CheckTabToFou checkTabToFou = new CheckTabToFou();
	private CheckKings checkKings = new CheckKings();
	private TableauMovement tableauMovement = new TableauMovement();
	private int backToBackTopDeck = 0;

	public void initiateGame() {
		initiateCardsArray();
		// Finished game might not need this call:
		updateGameState();
	}

	private void initiateCardsArray() {
		//Fra venstre mod højre
		tableauRows = new ArrayList<>();
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
		tableauRows.add(new ArrayList<>());
	}

	// TODO - This must be implemented, when we get data from a picture - take a snapshot of the current cards
	public void updateGameState() {
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.
		topDeckCard = null; //Ace of Diamonds

		//Made from the picture in our Discord chat:
		tableauRows.get(0).add(new Card(13, "Clubs"));
		tableauRows.get(1).add(new Card(12, "Spades"));
		tableauRows.get(2).add(new Card(10, "Spades"));
		tableauRows.get(3).add(new Card(9, "Spades"));
		tableauRows.get(4).add(new Card(5, "Hearts"));
		tableauRows.get(5).add(new Card(9, "Hearts"));
		tableauRows.get(6).add(new Card(13, "Diamonds"));

		//Da alle disse bunker er tomme fra start.
		foundationsDeckDiamonds = null;
		foundationsDeckHearts = null;
		foundationsDeckClubs = null;
		foundationsDeckSpades = null;
		
		logicState.updateState(tableauRows, topDeckCard, foundationsDeckDiamonds,
				foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
		calculateNextMove();
	}
	
	private void calculateNextMove() {
		checkAces.setLogicState(logicState);
		checkTabToFou.setLogicState(logicState);
		checkKings.setLogicState(logicState);
		tableauMovement.setLogicState(logicState);


		if (checkAces.checkTopDeckForAce()) {
			System.out.println("checkTopDeckForAce FÆRDIG");
			backToBackTopDeck = 0;
		} else if (checkAces.checkTableauRowsForAce()) {
			System.out.println("checkTableauRowsForAce FÆRDIG");
			backToBackTopDeck = 0;
		} else if (checkTabToFou.topDeckToFoundation()) {
			System.out.println("topDeckToFoundation FÆRDIG");
			backToBackTopDeck = 0;
		} else if (checkTabToFou.checkTableauToFoundation()) {
			System.out.println("checkTableauToFoundation FÆRDIG");
			backToBackTopDeck = 0;
		} else if (checkKings.checkForKing()) {
			System.out.println("checkForKing FÆRDIG");
			backToBackTopDeck = 0;
		} else if (tableauMovement.topdeckToTableau()) {
			System.out.println("topdeckToTableau FÆRDIG");
			backToBackTopDeck = 0;
		} else if (tableauMovement.tableauToTableauHiddenCard()) {
			System.out.println("tableauToTableauHidden FÆRDIG");
			backToBackTopDeck = 0;
		} else if (tableauMovement.tableauToTableau()) {
			System.out.println("tableauToTableau FÆRDIG");
			backToBackTopDeck = 0;
		} else if (tableauMovement.tabRowToTabRow()) {
			System.out.println("tabRowToTabRow FÆRDIG");
			backToBackTopDeck = 0;
		} else if (logicState.getTotalCardsInTopDeck() > 0 && backToBackTopDeck < logicState.getTotalCardsInTopDeck()) {
			System.out.println("Turn the top deck.");
			System.out.println("topDeck FÆRDIG");
			backToBackTopDeck++;
		} else {
			if (logicState.getFoundationsDeckDiamonds().getValue() == 13
					&& logicState.getFoundationsDeckHearts().getValue() == 13
					&& logicState.getFoundationsDeckClubs().getValue() == 13
					&& logicState.getFoundationsDeckSpades().getValue() == 13)
				System.out.println("CONGRATULATIONS - you won the game!");
			else
				System.out.println("Game over\nThere are no further moves!");
		}
	}
}