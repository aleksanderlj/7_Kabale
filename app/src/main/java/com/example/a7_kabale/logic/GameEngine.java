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
	private Card[] retrunCards = new Card[2];
	private CheckAces checkAces = new CheckAces();
	private CheckTabToFou checkTabToFou = new CheckTabToFou();
	private CheckKings checkKings = new CheckKings();
	private TableauMovement tableauMovement = new TableauMovement();
	private int backToBackTopDeck = 0;

	public void initiateGame() {
		initiateCardsArray();
		// Finished game might not need this call:
		//updateGameState();
	}

	private void initiateCardsArray() {
		tableauRows = new ArrayList<>();
	}

	// TODO - This must be implemented, when we get data from a picture - take a snapshot of the current cards
	public Card[] updateGameState(ArrayList<ArrayList<Card>> input) {
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.
		initiateCardsArray();
		topDeckCard = input.get(1).get(0); //Ace of Diamonds

		//Made from the picture in our Discord chat:
		tableauRows.add(input.get(6));
		tableauRows.add(input.get(7));
		tableauRows.add(input.get(8));
		tableauRows.add(input.get(9));
		tableauRows.add(input.get(10));
		tableauRows.add(input.get(11));
		tableauRows.add(input.get(12));


		//Da alle disse bunker er tomme fra start.
		foundationsDeckDiamonds = input.get(2).get(0);
		foundationsDeckHearts = input.get(3).get(0);
		foundationsDeckClubs = input.get(4).get(0);
		foundationsDeckSpades = input.get(5).get(0);
		
		logicState.updateState(tableauRows, topDeckCard, foundationsDeckDiamonds,
				foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
		retrunCards = null;
		return calculateNextMove();
	}
	
	private Card[] calculateNextMove() {
		checkAces.setLogicState(logicState);
		checkTabToFou.setLogicState(logicState);
		checkKings.setLogicState(logicState);
		tableauMovement.setLogicState(logicState);
		
		
		// checkTopDeckForAce
		retrunCards = checkAces.checkTopDeckForAce();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("checkTopDeckForAce FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// checkTopDeckForAce
		retrunCards = checkAces.checkTableauRowsForAce();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("checkTableauRowsForAce FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}

		// topdeckToTableau
		retrunCards = tableauMovement.topdeckToTableau();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("topdeckToTableau FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}

		// topDeckToFoundation
		retrunCards = checkTabToFou.topDeckToFoundation();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("topDeckToFoundation FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// checkForKing
		retrunCards = checkKings.checkForKing();
		if (checkKings.checkForKing() != null) {
			System.out.println(retrunCards.toString());
			System.out.println("checkForKing FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}

		// tableauToTableauHiddenCard
		retrunCards = tableauMovement.tableauToTableauHiddenCard();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("tableauToTableauHidden FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// tableauToTableau
		retrunCards = tableauMovement.tableauToTableau();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("tableauToTableau FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// tabRowToTabRow
		retrunCards = tableauMovement.tabRowToTabRow();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("tabRowToTabRow FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// checkTableauToFoundation
		retrunCards = checkTabToFou.checkTableauToFoundation();
		if (retrunCards != null) {
			System.out.println(retrunCards.toString());
			System.out.println("checkTableauToFoundation FÆRDIG");
			backToBackTopDeck = 0;
			return retrunCards;
		}
		
		// getTotalCardsInTopDeck
		if (logicState.getTotalCardsInTopDeck() > 0 && backToBackTopDeck < logicState.getTotalCardsInTopDeck()) {
			System.out.println("Turn the top deck.");
			System.out.println("topDeck FÆRDIG");
			backToBackTopDeck++;
			return new Card[] {new Card(0, "Turn top deck")};
		} else {
			if (logicState.getFoundationsDeckDiamonds().getValue() == 13
					&& logicState.getFoundationsDeckHearts().getValue() == 13
					&& logicState.getFoundationsDeckClubs().getValue() == 13
					&& logicState.getFoundationsDeckSpades().getValue() == 13) {
				System.out.println("CONGRATULATIONS - you won the game!");
				return new Card[] {new Card(0, "CONGRATULATIONS - you won the game!")};
			}
			else {
				System.out.println("Game over!");
				return new Card[] {new Card(0, "Game over!")};
			}
		}
	}
}