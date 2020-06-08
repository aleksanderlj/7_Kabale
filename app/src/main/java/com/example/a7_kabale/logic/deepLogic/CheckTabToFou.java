package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import java.util.ArrayList;

public class CheckTabToFou {
	
	private ArrayList<ArrayList<Card>> tableauRows;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades, cardToSearchFor;
	
	public CheckTabToFou(ArrayList<ArrayList<Card>> tableauRows, Card topDeckCard, Card foundationsDeckDiamonds, Card foundationsDeckHearts,
						 Card foundationsDeckClubs, Card foundationsDeckSpades) {
		this.tableauRows = tableauRows;
		this.topDeckCard = topDeckCard;
		this.foundationsDeckDiamonds = foundationsDeckDiamonds;
		this.foundationsDeckHearts = foundationsDeckHearts;
		this.foundationsDeckClubs = foundationsDeckClubs;
		this.foundationsDeckSpades = foundationsDeckSpades;
	}
	
	public boolean checkTableauToFoundation(){
		return checkTableauToFoundationDiamonds() || checkTableauToFoundationHearts()
				|| checkTableauToFoundationClubs() || checkTableauToFoundationSpades();
	}



	// Private metoder
	private boolean checkTableauToFoundationDiamonds() {
		// Først tjek for at der er minimum et es og tjek for at alle kort ikke er der
		if (foundationsDeckDiamonds.getValue() > 0 && foundationsDeckDiamonds.getValue() < 13) {

			// Da vi skal finde kortet, der er 1 højere
			cardToSearchFor = new Card(foundationsDeckDiamonds.getValue() + 1, foundationsDeckDiamonds.getSuit());

			// Tjek først topdeck:
			if (topDeckCard.toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the first foundation pile.");
				return true;
			}

			// Herefter forreste kort i hver række:
			int tableauNumber;
			for (int i = 0; i < tableauRows.size(); i++) {
				tableauNumber = i + 1;
				if (tableauRows.get(i).get(tableauRows.get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
					System.out.println("Move " + cardToSearchFor.toString() +
							" from tableau row " + tableauNumber +
							" to the first foundation pile.");
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationHearts(){
		if (foundationsDeckHearts.getValue() != 0 && foundationsDeckHearts.getValue() > 13) {

			cardToSearchFor = new Card(foundationsDeckHearts.getValue() + 1, foundationsDeckHearts.getSuit());

			if (topDeckCard.toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the second foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < tableauRows.size(); i++) {
				tableauNumber = i + 1;
				if (tableauRows.get(i).get(tableauRows.get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
					System.out.println("Move " + cardToSearchFor.toString() +
							" from tableau row " + tableauNumber +
							" to the second foundation pile.");
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationClubs(){
		if (foundationsDeckClubs.getValue() != 0 && foundationsDeckClubs.getValue() > 13) {

			cardToSearchFor = new Card(foundationsDeckClubs.getValue() + 1, foundationsDeckClubs.getSuit());

			if (topDeckCard.toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the third foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < tableauRows.size(); i++) {
				tableauNumber = i + 1;
				if (tableauRows.get(i).get(tableauRows.get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
					System.out.println("Move " + cardToSearchFor.toString() +
							" from tableau row " + tableauNumber +
							" to the third foundation pile.");
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationSpades(){
		if (foundationsDeckSpades.getValue() != 0 && foundationsDeckSpades.getValue() > 13) {

			cardToSearchFor = new Card(foundationsDeckSpades.getValue() + 1, foundationsDeckSpades.getSuit());

			if (topDeckCard.toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the fourth foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < tableauRows.size(); i++) {
				tableauNumber = i + 1;
				if (tableauRows.get(i).get(tableauRows.get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
					System.out.println("Move " + cardToSearchFor.toString() +
							" from tableau row " + tableauNumber +
							" to the fourth foundation pile.");
					return true;
				}
			}
		}
		return false;
	}
}
