package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

public class CheckAces {
	private LogicState logicState;

	public CheckAces(){}
	
	public Card[] checkTopDeckForAce(){
		if (logicState.getTopDeckCard() == null)
			return null;
		if (logicState.getTopDeckCard().getValue() == 1) {
			logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckDiamonds()};
				case "Hearts":
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckHearts()};
				case "Clubs":
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckClubs()};
				case "Spades":
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckSpades()};
			}
		}
		return null;
	}
	
	public Card[] checkTableauRowsForAce(){
		int tableauNumber;
		for (int i = 0; i < logicState.getTableauRows().size(); i++) {
			tableauNumber = i + 1;
			if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
				if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() == 1) {
					int[] newHiddenCards = logicState.getHiddenCards();
					switch (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit()) {
						case "Diamonds":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckDiamonds()};
						case "Hearts":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckHearts()};
						case "Clubs":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckClubs()};
						case "Spades":
							if (logicState.getHiddenCards()[tableauNumber] != 0) {
								newHiddenCards[tableauNumber] = newHiddenCards[tableauNumber] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckSpades()};
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
