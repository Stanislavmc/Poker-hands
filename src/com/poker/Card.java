package com.poker;

import com.poker.enums.CardRank;
import com.poker.enums.Suit;

public class Card {

	private Suit suit;
	private CardRank cardRank;

	public Card(CardRank cardRank, Suit suit) {
		this.cardRank = cardRank;
		this.suit = suit;
	}

	// card code is a card rank and suit ex.: 8H - 8 Hearts, TC - Ten Clubs
	public Card(String cardCode) { 
		if (cardCode != null && cardCode.length() == 2) {
			
			switch (cardCode.charAt(0)) {
				case '2': cardRank = CardRank.TWO; break;
				case '3': cardRank = CardRank.THREE; break;
				case '4': cardRank = CardRank.FOUR; break;
				case '5': cardRank = CardRank.FIVE; break;
				case '6': cardRank = CardRank.SIX; break;
				case '7': cardRank = CardRank.SEVEN; break;
				case '8': cardRank = CardRank.EIGHT; break;
				case '9': cardRank = CardRank.NINE; break;
				case 'T': cardRank = CardRank.TEN; break;
				case 'J': cardRank = CardRank.JACK; break;
				case 'Q': cardRank = CardRank.QUEEN; break;
				case 'K': cardRank = CardRank.KING; break;
				case 'A': cardRank = CardRank.ACE; break;
				default: System.out.println("Bad card RANK code!"); break;
			}
			
			switch (cardCode.charAt(1)) {
				case 'C': suit = Suit.CLUBS; break;
				case 'D': suit = Suit.DIAMONDS; break;
				case 'H': suit = Suit.HEARTS; break;
				case 'S': suit = Suit.SPADES; break;
				default: System.out.println("Bad card SUITE code!"); break;
			}
		} else {
			System.out.println("Bad card code");
		}
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public CardRank getCardRank() {
		return cardRank;
	}

	public void setCardRank(CardRank cardRank) {
		this.cardRank = cardRank;
	}
	
	public int getCardRankNumber() {
		return getCardRank().getCardRankNumber();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(cardRank.getShortName()).append(suit.getShortName()).toString();
	}
}