package com.example.a7_kabale.logic;

import androidx.annotation.NonNull;

public class Card {
	private int value;
	private String suit;
	//	// This value is only meant for cards in the stackdecks
	private boolean shown;
	
	public Card (int value, String suit){
		this.value = value;
		this.suit = suit;
	}
	
	@NonNull
	@Override
	public String toString() {
		return this.value + this.suit;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void setShown(boolean shown) {
		this.shown = shown;
	}
}
