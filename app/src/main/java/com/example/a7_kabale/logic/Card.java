package com.example.a7_kabale.logic;

import androidx.annotation.NonNull;

public class Card {
	private int value;
	private String suit;
	
	public Card (int value, String suit){
		this.value = value;
		this.suit = suit;
	}
	
	@NonNull
	@Override
	public String toString() {
		return this.value + this.suit;
	}
}
