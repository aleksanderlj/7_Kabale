package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import java.util.ArrayList;

public class CheckAces {
	private ArrayList<ArrayList<Card>> tableauRow;
	private Card topDeckCard;
	
	public CheckAces(ArrayList<ArrayList<Card>> tableauRow, Card topDeckCard) {
		this.tableauRow = tableauRow;
		this.topDeckCard = topDeckCard;
	}
	
	public boolean checkTopDeckForAce(){
		if (topDeckCard.getValue() == 1) {
			switch (topDeckCard.getSuit()) {
				case "Diamonds":
					System.out.println("Move the "+ topDeckCard.toString() + " from the top deck to the first foundation pile.");
					return true;
				case "Hearts":
					System.out.println("Move the " + topDeckCard.toString() + " from the top deck to the second foundation pile.");
					return true;
				case "Clubs":
					System.out.println("Move the " + topDeckCard.toString() + " from the top deck to the third foundation pile.");
					return true;
				case "Spades":
					System.out.println("Move the " + topDeckCard.toString() + " from the top deck to the fourth foundation pile.");
					return true;
			}
		}
		return false;
	}
	
	public boolean checkTableauRowsForAce(){
		int tableauNumber;
		for (int i = 0; i < tableauRow.size(); i++) {
			tableauNumber = i + 1;
			if (tableauRow.get(i).get(tableauRow.get(i).size() - 1).getValue() == 1) {
				switch (tableauRow.get(i).get(tableauRow.get(i).size() - 1).getSuit()) {
					case "Diamonds":
						System.out.println("Move the Diamonds Ace from tableau row " + tableauNumber + " to the first foundation pile.");
						return true;
					case "Hearts":
						System.out.println("Move the Hearts Ace from the tableau row " + tableauNumber + " to the second foundation pile.");
						return true;
					case "Clubs":
						System.out.println("Move the Clubs Ace from the tableau row " + tableauNumber + " to the third foundation pile.");
						return true;
					case "Spades":
						System.out.println("Move the Spades Ace from the tableau row " + tableauNumber + " to the fourth foundation pile.");
						return true;
				}
			}
		}
		return false;
	}
}
