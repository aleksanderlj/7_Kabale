package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

public class CheckTabToFou {

	private LogicState logicState;
	private Card cardToSearchFor;
	
	public CheckTabToFou(){
	}
	
	public boolean checkTableauToFoundation(){
		return checkTableauToFoundationDiamonds() || checkTableauToFoundationHearts()
				|| checkTableauToFoundationClubs() || checkTableauToFoundationSpades();
	}

	public boolean topDeckToFoundation() {
		if (logicState.getTopDeckCard() != null) {
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					if (logicState.getFoundationsDeckDiamonds() != null &&
							logicState.getFoundationsDeckDiamonds().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						System.out.println("Move " + logicState.getTopDeckCard() + " from the top deck to the first foundation pile.");
						return true;
					}
					return false;
				case "Hearts":
					if (logicState.getFoundationsDeckHearts() != null &&
							logicState.getFoundationsDeckHearts().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						System.out.println("Move " + logicState.getTopDeckCard() + " from the top deck to the second foundation pile.");
						return true;
					}
					return false;
				case "Clubs":
					if (logicState.getFoundationsDeckClubs() != null &&
							logicState.getFoundationsDeckClubs().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						System.out.println("Move " + logicState.getTopDeckCard() + " from the top deck to the third foundation pile.");
						return true;
					}
					return false;
				case "Spades":
					if (logicState.getFoundationsDeckSpades() != null &&
							logicState.getFoundationsDeckSpades().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						System.out.println("Move " + logicState.getTopDeckCard() + " from the top deck to the fourth foundation pile.");
						return true;
					}
					return false;
			}
		}
		return false;
	}

	// Private metoder
	private boolean checkTableauToFoundationDiamonds() {
		// Først tjek for at der er minimum et es og tjek for at alle kort ikke er der
		if (logicState.getFoundationsDeckDiamonds() != null && logicState.getFoundationsDeckDiamonds().getValue() < 13) {
			// Da vi skal finde kortet, der er 1 højere
			cardToSearchFor = new Card(logicState.getFoundationsDeckDiamonds().getValue() + 1, logicState.getFoundationsDeckDiamonds().getSuit());

			// Herefter forreste kort i hver række:
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + (i+1) +
								" to the first foundation pile.");
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
							System.out.println("Flip the hidden card!");
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationHearts(){
		if (logicState.getFoundationsDeckHearts() != null && logicState.getFoundationsDeckHearts().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckHearts().getValue() + 1, logicState.getFoundationsDeckHearts().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + (i+1) +
								" to the second foundation pile.");
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
							System.out.println("Flip the hidden card!");
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationClubs(){
		if (logicState.getFoundationsDeckClubs() != null && logicState.getFoundationsDeckClubs().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckClubs().getValue() + 1, logicState.getFoundationsDeckClubs().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + (i+1) +
								" to the third foundation pile.");
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
							System.out.println("Flip the hidden card!");
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationSpades(){
		if (logicState.getFoundationsDeckSpades() != null && logicState.getFoundationsDeckSpades().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckSpades().getValue() + 1, logicState.getFoundationsDeckSpades().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + (i+1) +
								" to the fourth foundation pile.");
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
							System.out.println("Flip the hidden card!");
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void setLogicState(LogicState logicState) {
		this.logicState = logicState;
	}
}
