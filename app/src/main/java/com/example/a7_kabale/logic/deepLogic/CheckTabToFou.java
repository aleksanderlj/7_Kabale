package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;

import java.util.ArrayList;

public class CheckTabToFou {

	private final LogicState logicState;
	private  Card cardToSearchFor;
	
	public CheckTabToFou(LogicState logicState) {
		this.logicState = logicState;
	}
	
	public boolean checkTableauToFoundation(){
		return checkTableauToFoundationDiamonds() || checkTableauToFoundationHearts()
				|| checkTableauToFoundationClubs() || checkTableauToFoundationSpades();
	}



	// Private metoder
	private boolean checkTableauToFoundationDiamonds() {
		// Først tjek for at der er minimum et es og tjek for at alle kort ikke er der
		if (logicState.getFoundationsDeckDiamonds() != null && logicState.getFoundationsDeckDiamonds().getValue() < 13) {

			// Da vi skal finde kortet, der er 1 højere
			cardToSearchFor = new Card(logicState.getFoundationsDeckDiamonds().getValue() + 1, logicState.getFoundationsDeckDiamonds().getSuit());

			// Tjek først topdeck:
			if (logicState.getTopDeckCard().toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the first foundation pile.");
				return true;
			}

			// Herefter forreste kort i hver række:
			int tableauNumber;
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				tableauNumber = i + 1;
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + tableauNumber +
								" to the first foundation pile.");
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationHearts(){
		if (logicState.getFoundationsDeckHearts() != null && logicState.getFoundationsDeckHearts().getValue() > 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckHearts().getValue() + 1, logicState.getFoundationsDeckHearts().getSuit());

			if (logicState.getTopDeckCard().toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the second foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				tableauNumber = i + 1;
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + tableauNumber +
								" to the second foundation pile.");
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationClubs(){
		if (logicState.getFoundationsDeckClubs() != null && logicState.getFoundationsDeckClubs().getValue() > 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckClubs().getValue() + 1, logicState.getFoundationsDeckClubs().getSuit());

			if (logicState.getTopDeckCard().toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the third foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				tableauNumber = i + 1;
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + tableauNumber +
								" to the third foundation pile.");
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkTableauToFoundationSpades(){
		if (logicState.getFoundationsDeckSpades() != null && logicState.getFoundationsDeckSpades().getValue() > 13) {

			cardToSearchFor = new Card(logicState.getFoundationsDeckSpades().getValue() + 1, logicState.getFoundationsDeckSpades().getSuit());

			if (logicState.getTopDeckCard().toString().equals(cardToSearchFor.toString())){
				System.out.println("Move " + cardToSearchFor.toString() +
						" from topdeck to the fourth foundation pile.");
				return true;
			}

			int tableauNumber;
			for (int i = 0; i < logicState.getTableauRows().size(); i++) {
				tableauNumber = i + 1;
				if (logicState.getTableauRows().get(i).size() != 0) {
					if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).toString().equals(cardToSearchFor.toString())) {
						System.out.println("Move " + cardToSearchFor.toString() +
								" from tableau row " + tableauNumber +
								" to the fourth foundation pile.");
						return true;
					}
				}
			}
		}
		return false;
	}
}
