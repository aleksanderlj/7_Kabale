package com.example.a7_kabale.Logic.DeepLogic;

import com.example.a7_kabale.Logic.Card;
import com.example.a7_kabale.Logic.LogicState;

public class CheckTabToFou {

	private LogicState logicState;
	private Card cardToSearchFor;
	
	public CheckTabToFou(){
	}
	
	public Card[] checkTableauToFoundation(){
		if (checkTableauToFoundationDiamonds())
			return new Card[] {cardToSearchFor, logicState.getFoundationsDeckDiamonds()};
		else if (checkTableauToFoundationHearts())
			return new Card[] {cardToSearchFor, logicState.getFoundationsDeckHearts()};
		else if (checkTableauToFoundationClubs())
			return new Card[] {cardToSearchFor, logicState.getFoundationsDeckClubs()};
		else if (checkTableauToFoundationSpades())
			return new Card[] {cardToSearchFor, logicState.getFoundationsDeckSpades()};
		return null;
	}

	public Card[] topDeckToFoundation() {
		if (logicState.getTopDeckCard().getValue() != 0) {
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					if (logicState.getFoundationsDeckDiamonds().getValue() != 0 &&
							logicState.getFoundationsDeckDiamonds().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckDiamonds()};
					}
					return null;
				case "Hearts":
					if (logicState.getFoundationsDeckHearts().getValue() != 0 &&
							logicState.getFoundationsDeckHearts().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckHearts()};
					}
					return null;
				case "Clubs":
					if (logicState.getFoundationsDeckClubs().getValue() != 0 &&
							logicState.getFoundationsDeckClubs().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckClubs()};
					}
					return null;
				case "Spades":
					if (logicState.getFoundationsDeckSpades().getValue() != 0 &&
							logicState.getFoundationsDeckSpades().getValue() + 1 == logicState.getTopDeckCard().getValue()) {
						return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckSpades()};
					}
					return null;
			}
		}
		return null;
	}

	// Private metoder
	private boolean checkTableauToFoundationDiamonds() {
		// Først tjek for at der er minimum et es og tjek for at alle kort ikke er der
		if (logicState.getFoundationsDeckDiamonds().getValue() != 0 && logicState.getFoundationsDeckDiamonds().getValue() < 13) {
			// Da vi skal finde kortet, der er 1 højere
			cardToSearchFor = new Card(logicState.getFoundationsDeckDiamonds().getValue() + 1, logicState.getFoundationsDeckDiamonds().getSuit());

			// Herefter forreste kort i hver række:
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() ==cardToSearchFor.getValue() &&
							logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit().equals(cardToSearchFor.getSuit())) {
						cardToSearchFor = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
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
		if (logicState.getFoundationsDeckHearts().getValue() != 0 && logicState.getFoundationsDeckHearts().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckHearts().getValue() + 1, logicState.getFoundationsDeckHearts().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() ==cardToSearchFor.getValue() &&
							logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit().equals(cardToSearchFor.getSuit())) {
						cardToSearchFor = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
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
		if (logicState.getFoundationsDeckClubs().getValue() != 0 && logicState.getFoundationsDeckClubs().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckClubs().getValue() + 1, logicState.getFoundationsDeckClubs().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() ==cardToSearchFor.getValue() &&
							logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit().equals(cardToSearchFor.getSuit())) {
						cardToSearchFor = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
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
		if (logicState.getFoundationsDeckSpades().getValue() != 0 && logicState.getFoundationsDeckSpades().getValue() < 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckSpades().getValue() + 1, logicState.getFoundationsDeckSpades().getSuit());

			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() ==cardToSearchFor.getValue() &&
							logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit().equals(cardToSearchFor.getSuit())) {
						cardToSearchFor = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
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
