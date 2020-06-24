package com.example.a7_kabale.logic;

import java.util.ArrayList;

public class LogicState {
	private ArrayList<ArrayList<Card>> tableauRows;
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	private int totalCardsInTopDeck = 24;
	private int[] hiddenCards = {0,1,2,3,4,5,6};
	private int backToBackTopDeck = 0;
	private ArrayList<int[]> historicHiddenCards = new ArrayList<>();
	private ArrayList<Integer> historicCardsInTopDeck = new ArrayList<>();
	
	public LogicState(){}
	
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
	
	public int getTotalCardsInTopDeck() {
		return totalCardsInTopDeck;
	}
	
	public void setTotalCardsInTopDeck(int totalCardsInTopDeck) {
		this.totalCardsInTopDeck = totalCardsInTopDeck;
	}
	
	public int[] getHiddenCards() {
		return hiddenCards;
	}
	
	public void setHiddenCards(int[] hiddenCards) {
		this.hiddenCards = hiddenCards;
	}
	
	public int getBackToBackTopDeck() {
		return backToBackTopDeck;
	}
	
	public void setBackToBackTopDeck(int backToBackTopDeck) {
		this.backToBackTopDeck = backToBackTopDeck;
	}
	
	public ArrayList<int[]> getHistoricHiddenCards() {
		return historicHiddenCards;
	}
	
	public void setHistoricCardsInTopDeck(ArrayList<Integer> historicCardsInTopDeck) {
		this.historicCardsInTopDeck = historicCardsInTopDeck;
	}
	
	public ArrayList<Integer> getHistoricCardsInTopDeck() {
		return historicCardsInTopDeck;
	}
	
	public void setHistoricHiddenCards(ArrayList<int[]> historicHiddenCards) {
		this.historicHiddenCards = historicHiddenCards;
	}
}
