package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.LogicState;

public class CheckAces {
	private LogicState logicState;
	
	public CheckAces(LogicState logicState) {
		this.logicState = logicState;
	}
	
	public boolean checkTopDeckForAce(){
		if (logicState.getTopDeckCard().getValue() == 1) {
			logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					System.out.println("Move the "+ logicState.getTopDeckCard().toString() + " from the top deck to the first foundation pile.");
					return true;
				case "Hearts":
					System.out.println("Move the " + logicState.getTopDeckCard().toString() + " from the top deck to the second foundation pile.");
					return true;
				case "Clubs":
					System.out.println("Move the " + logicState.getTopDeckCard().toString() + " from the top deck to the third foundation pile.");
					return true;
				case "Spades":
					System.out.println("Move the " + logicState.getTopDeckCard().toString() + " from the top deck to the fourth foundation pile.");
					return true;
			}
		}
		return false;
	}
	
	public boolean checkTableauRowsForAce(){
		int tableauNumber;
		for (int i = 0; i < logicState.getTableauRows().size(); i++) {
			tableauNumber = i + 1;
			if (logicState.getTableauRows().get(i).size() != 0) {
				if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() == 1) {
					int[] newHiddenCards = logicState.getHiddenCards();
					switch (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit()) {
						case "Diamonds":
							System.out.println("Move the Diamonds Ace from tableau row " + tableauNumber + " to the first foundation pile.");
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return true;
						case "Hearts":
							System.out.println("Move the Hearts Ace from the tableau row " + tableauNumber + " to the second foundation pile.");
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return true;
						case "Clubs":
							System.out.println("Move the Clubs Ace from the tableau row " + tableauNumber + " to the third foundation pile.");
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return true;
						case "Spades":
							System.out.println("Move the Spades Ace from the tableau row " + tableauNumber + " to the fourth foundation pile.");
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return true;
					}
				}
			}
		}
		return false;
	}
}
