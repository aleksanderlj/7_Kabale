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

	public void initiateGame() {
		initiateCardsArray();
		setGameState();
		
		CheckAces checkAces;
		CheckTabToFou checkTabToFou;
		CheckKings checkKings;
		TableauMovement tableauMovement;
		
		// Run of the game
		int i = 0;
		while (i < 1) {
			i++;
			setGameState();
			checkAces = new CheckAces(logicState);
			checkTabToFou = new CheckTabToFou(logicState);
			checkKings = new CheckKings(logicState);
			tableauMovement = new TableauMovement(logicState);

			System.out.println(logicState.getArrayofHiddenCards()[3]);

			if (checkAces.checkTopDeckForAce())
				System.out.println("checkTopDeckForAce FÆRDIG");
			else if (checkAces.checkTableauRowsForAce())
				System.out.println("checkTableauRowsForAce FÆRDIG");
			else if (checkTabToFou.checkTableauToFoundation())
				System.out.println("checkTableauToFoundation FÆRDIG");
			else if (checkKings.checkForKing())
				System.out.println("checkForKing FÆRDIG");
			else if (tableauMovement.topdeckToTableau())
				System.out.println("topdeckToTableau FÆRDIG");
			else if (tableauMovement.tableauToTableauHiddenCard())
				System.out.println("tableauToTableauHidden FÆRDIG");
			else if (tableauMovement.tableauToTableau())
				System.out.println("tableauToTableau FÆRDIG");
		}
	}

	private void initiateCardsArray() {
		//Fra venstre mod højre
		tableauRows = new ArrayList<>();
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
		tableauRows.add(new ArrayList<Card>());
	}

	// TODO - This must be implemented, when we get data from a picture - take a snapshot of the current cards
	private void setGameState() {
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.
		
		topDeckCard = new Card(8, "Clubs"); //Ace of Diamonds

		//Made from the picture in our Discord chat:
		tableauRows.get(0).add((new Card(13, "Diamonds")));
		tableauRows.get(1).add(new Card(9, "Diamonds"));
		tableauRows.get(2).add(new Card(4, "Spades"));
		tableauRows.get(3).add(new Card(10, "Spades"));
		tableauRows.get(4).add(new Card(7, "Spades"));
		tableauRows.get(5).add(new Card(3, "Spades"));
		tableauRows.get(6).add(new Card(12, "Hearts"));

		//Da alle disse bunker er tomme fra start.
		foundationsDeckDiamonds = new Card(3, "Diamonds");
		foundationsDeckHearts = null;
		foundationsDeckClubs = null;
		foundationsDeckSpades = null;
		
		logicState.updateState(tableauRows, topDeckCard, foundationsDeckDiamonds,
				foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
	}
}

