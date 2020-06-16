package com.example.a7_kabale.logic;

import androidx.annotation.NonNull;

public class Card {
	private int value;
	private String suit;
	private boolean red;
	//	// This value is only meant for cards in the stackdecks
	
	public Card (int value, String suit){
		this.value = value;
		this.suit = suit;
		if (suit.equals("Hearts") || suit.equals("Diamonds"))
			red = true;
		else
			red = false;
	}
	
	@NonNull
	@Override
	public String toString() {
		return this.suit + " " + this.value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public boolean isRed() {
		return red;
	}
	
	public void setRed(boolean red) {
		this.red = red;
	}
}
