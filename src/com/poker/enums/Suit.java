package com.poker.enums;

public enum Suit {
	CLUBS, 
	HEARTS, 
	SPADES, 
	DIAMONDS;
	
	public char getShortName() {
		return toString().charAt(0);
	}
}