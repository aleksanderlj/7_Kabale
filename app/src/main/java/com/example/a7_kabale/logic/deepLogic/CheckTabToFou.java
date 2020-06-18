package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

public class CheckTabToFou {

	private LogicState logicState;
	private Card cardToSearchFor;
	
	public CheckTabToFou(){
	}
	
	public String checkTableauToFoundation(){
		if (checkTableauToFoundationDiamonds())
			return "Move " + cardToSearchFor.toString() + " to " + logicState.getFoundationsDeckDiamonds();
		else if (checkTableauToFoundationHearts())
			return "Move " + cardToSearchFor.toString() + " to " + logicState.getFoundationsDeckHearts();
		else if (checkTableauToFoundationClubs())
			return "Move " + cardToSearchFor.toString() + " to " + logicState.getFoundationsDeckClubs();
		else if (checkTableauToFoundationSpades())
			return "Move " + cardToSearchFor.toString() + " to " + logicState.getFoundationsDeckSpades();
		return null;
	}

	public String topDeckToFoundation() {
		if (logicState.getTopDeckCard() != null) {
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					if (logicState.getFoundationsDeckDiamonds() != null &&
							logicState.getFoundationsDeckDiamonds().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return "Move " + logicState.getTopDeckCard().toString() + " to " + logicState.getFoundationsDeckDiamonds().toString();
					}
					return null;
				case "Hearts":
					if (logicState.getFoundationsDeckHearts() != null &&
							logicState.getFoundationsDeckHearts().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return "Move " + logicState.getTopDeckCard().toString() + " to " + logicState.getFoundationsDeckHearts().toString();
					}
					return null;
				case "Clubs":
					if (logicState.getFoundationsDeckClubs() != null &&
							logicState.getFoundationsDeckClubs().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return "Move " + logicState.getTopDeckCard().toString() + " to " + logicState.getFoundationsDeckClubs().toString();
					}
					return null;
				case "Spades":
					if (logicState.getFoundationsDeckSpades() != null &&
							logicState.getFoundationsDeckSpades().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return "Move " + logicState.getTopDeckCard().toString() + " to " + logicState.getFoundationsDeckSpades().toString();
					}
					return null;
			}
		}
		return null;
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
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
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
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
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
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
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
						if (logicState.getHiddenCards()[i] != 0){
							int [] newHiddenCards = logicState.getHiddenCards();
							newHiddenCards[i] = newHiddenCards[i]-1;
							logicState.setHiddenCards(newHiddenCards);
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
