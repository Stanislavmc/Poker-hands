package com.poker.enums;


public enum Combination {
	HIGH_CARD(1),		//	Highest value card.
	ONE_PAIR(2),		//	Two cards of the same value.
	TWO_PAIR(3),		//	Two different pairs.
	THREE_OF_A_KIND(4),//	Three cards of the same value.
	STRAIGHT(5), 		//	Straight: All cards are consecutive values.
	FLUSH(6),			//	Flush: All cards of the same suit.
	FULL_HOUSE(7),		//	Full House: Three of a kind and a pair.
	FOUR_OF_A_KIND(8),	//	Four of a Kind: Four cards of the same value.
	STRAIGHT_FLUSH(9),	//	Straight Flush: All cards are consecutive values of same suit.
	ROYAL_FLUSH(10);	//	Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
	

	private int combinationRank;

	private Combination(int combinationRank) {
		this.combinationRank = combinationRank;
	}

	public int getCombinationNumber() {
		return combinationRank;
	}
	
}

