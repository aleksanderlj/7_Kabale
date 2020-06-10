package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class LogicState {
	private ArrayList<ArrayList<Card>> tableauRows;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	private int shownCards = 7;
	private int[] hiddenCards = {0, 1, 2, 3, 4, 5, 6};
	
	public LogicState(){
	}
	
	public void updateState(ArrayList<ArrayList<Card>> tableauRows, Card topDeckCard, Card foundationsDeckDiamonds,
								  Card foundationsDeckHearts, Card foundationsDeckClubs, Card foundationsDeckSpades){
		this.tableauRows = tableauRows;
		this.topDeckCard = topDeckCard;
		this.foundationsDeckDiamonds = foundationsDeckDiamonds;
		this.foundationsDeckHearts = foundationsDeckHearts;
		this.foundationsDeckClubs = foundationsDeckClubs;
		this.foundationsDeckSpades = foundationsDeckSpades;
	}
	
	public ArrayList<ArrayList<Card>> getTableauRows() {
		return tableauRows;
	}
	
	public void setTableauRows(ArrayList<ArrayList<Card>> tableauRows) {
		this.tableauRows = tableauRows;
	}
	
	public Card getTopDeckCard() {
		return topDeckCard;
	}
	
	public void setTopDeckCard(Card topDeckCard) {
		this.topDeckCard = topDeckCard;
	}
	
	public Card getFoundationsDeckClubs() {
		return foundationsDeckClubs;
	}
	
	public void setFoundationsDeckClubs(Card foundationsDeckClubs) {
		this.foundationsDeckClubs = foundationsDeckClubs;
	}
	
	public Card getFoundationsDeckDiamonds() {
		return foundationsDeckDiamonds;
	}
	
	public void setFoundationsDeckDiamonds(Card foundationsDeckDiamonds) {
		this.foundationsDeckDiamonds = foundationsDeckDiamonds;
	}
	
	public Card getFoundationsDeckHearts() {
		return foundationsDeckHearts;
	}
	
	public void setFoundationsDeckHearts(Card foundationsDeckHearts) {
		this.foundationsDeckHearts = foundationsDeckHearts;
	}
	
	public Card getFoundationsDeckSpades() {
		return foundationsDeckSpades;
	}
	
	public void setFoundationsDeckSpades(Card foundationsDeckSpades) {
		this.foundationsDeckSpades = foundationsDeckSpades;
	}
	
	public int getShownCards() {
		return shownCards;
	}
	
	public void setShownCards(int shownCards) {
		this.shownCards = shownCards;
	}
	
	public int[] getHiddenCards() {
		return hiddenCards;
	}
	
	public void setHiddenCards(int[] hiddenCards) {
		this.hiddenCards = hiddenCards;
	}
	
	public int[] getArrayofHiddenCards(){
		int[] hiddenCards = new int[7];
		int currHiddenCards;
		for (int i = 0; i < tableauRows.size(); i++){
			currHiddenCards = 0;
			for (int j = 0; j < tableauRows.get(i).size(); j++){
				if (!tableauRows.get(i).get(j).isShown())
					currHiddenCards++;
			}
			hiddenCards[i] = currHiddenCards;
		}
		return hiddenCards;
	}
}
