package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import java.util.ArrayList;

public class CheckAces {
	private ArrayList<ArrayList<Card>> tableauDecks;
	private Card topDeckCard;
	
	public CheckAces(ArrayList<ArrayList<Card>> tableauDecks, Card topDeckCard) {
		this.tableauDecks = tableauDecks;
		this.topDeckCard = topDeckCard;
	}
	
	public boolean checkTopDeckForAce(){
		if (topDeckCard.getValue() == 1) {
			switch (topDeckCard.getSuit()) {
				case "Diamonds":
					System.out.println("Move the "+ topDeckCard.toString() + " from the topdeck to the first foundation pile.");
					return true;
				case "Hearts":
					System.out.println("Move the " + topDeckCard.toString() + " from the topdeck to the second foundation pile.");
					return true;
				case "Clubs":
					System.out.println("Move the " + topDeckCard.toString() + " from the topdeck to the third foundation pile.");
					return true;
				case "Spades":
					System.out.println("Move the " + topDeckCard.toString() + " from the topdeck to the fourth foundation pile.");
					return true;
			}
		}
		return false;
	}
	
	public boolean checkTableauDecksForAce(){
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
}
