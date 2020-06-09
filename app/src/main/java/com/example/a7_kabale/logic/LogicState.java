package com.example.a7_kabale.logic;

import java.util.ArrayList;
import java.util.List;

public class LogicState {
	private ArrayList<ArrayList<Card>> tableauRows;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	private int shownCards;
	private List<Integer> hiddenCards = new ArrayList<>();
	
	public LogicState(ArrayList<ArrayList<Card>> tableauRows, Card topDeckCard, Card foundationsDeckDiamonds, Card foundationsDeckHearts,
					  Card foundationsDeckClubs, Card foundationsDeckSpades){
		this.tableauRows = tableauRows;
		this.topDeckCard = topDeckCard;
		this.foundationsDeckDiamonds = foundationsDeckDiamonds;
		this.foundationsDeckHearts = foundationsDeckHearts;
		this.foundationsDeckClubs = foundationsDeckClubs;
		this.foundationsDeckSpades = foundationsDeckSpades;
		shownCards = 7;
		hiddenCards.add(0);
		hiddenCards.add(1);
		hiddenCards.add(2);
		hiddenCards.add(3);
		hiddenCards.add(4);
		hiddenCards.add(5);
		hiddenCards.add(6);
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
	
	public void setHiddenCards(List<Integer> hiddenCards) {
		this.hiddenCards = hiddenCards;
	}
	
	public List<Integer> getHiddenCards() {
		return hiddenCards;
	}
	
	public void setShownCards(int shownCards) {
		this.shownCards = shownCards;
	}
}
