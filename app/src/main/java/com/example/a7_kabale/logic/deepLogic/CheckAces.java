package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.LogicState;

public class CheckAces {
	private LogicState logicState;

	public CheckAces(){}
	
	public String checkTopDeckForAce(){
		if (logicState.getTopDeckCard() == null)
			return null;
		if (logicState.getTopDeckCard().getValue() == 1) {
			logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					return "Move "+ logicState.getTopDeckCard().toString() + " to F" + 1;
				case "Hearts":
					return "Move "+ logicState.getTopDeckCard().toString() + " to F" + 2;
				case "Clubs":
					return "Move "+ logicState.getTopDeckCard().toString() + " to F" + 3;
				case "Spades":
					return "Move "+ logicState.getTopDeckCard().toString() + " to F" + 4;
			}
		}
		return null;
	}
	
	public String checkTableauRowsForAce(){
		int tableauNumber;
		for (int i = 0; i < logicState.getTableauRows().size(); i++) {
			tableauNumber = i + 1;
			if (logicState.getTableauRows().get(i).size() != 0) {
				if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() == 1) {
					int[] newHiddenCards = logicState.getHiddenCards();
					switch (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit()) {
						case "Diamonds":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return "Move D1 to F" + 1;
						case "Hearts":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return "Move H1 to F" + 2;
						case "Clubs":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return "Move C1 to F" + 3;
						case "Spades":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return "Move S1 to F" + 4;
					}
				}
			}
		}
		return null;
	}
	
	public void setLogicState(LogicState logicState) {
		this.logicState = logicState;
	}
}
