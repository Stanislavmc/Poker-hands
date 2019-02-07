package com.poker;

import java.util.ArrayList;
import java.util.Comparator;

import com.poker.enums.CardRank;
import com.poker.enums.Combination;
import com.poker.enums.Suit;

public class CombinationCounter {

	public boolean isPlayerOneWinner(ArrayList<Card> hand1, ArrayList<Card> hand2) {

		// sort cards
		hand1.sort(Comparator.comparing(Card::getCardRankNumber));
		hand2.sort(Comparator.comparing(Card::getCardRankNumber));

		Combination combination1 = getCombination(hand1);
		Combination combination2 = getCombination(hand2);
		
		boolean result = false;
		// If combination are the same, then compare two biggest cards in combination.
		if (combination1.getCombinationNumber() == combination2.getCombinationNumber()) {
			// else one of pair combinations
			int cardRankNumber1 = getHighestCardRankInCombination(hand1, combination1);
			int cardRankNumber2 = getHighestCardRankInCombination(hand2, combination2);
			if (cardRankNumber1 == cardRankNumber2) {
				// in this place there could be situation, where two players have ROYAL_FLUSH,
				// then draw, then player1 is not winner
				result = getHighestCard(hand1).getCardRankNumber() > 
						getHighestCard(hand2).getCardRankNumber();
			} else
				result = cardRankNumber1 > cardRankNumber2;
		} else
			result = combination1.getCombinationNumber() > combination2.getCombinationNumber();
		System.out.println("\t" + hand1 + "\t" + hand2 + "\t" + result);
		System.out.println("\t"+combination1+"\t\t"+combination2);	
		System.out.println();
		return result;
	}

	private Card getHighestCard(ArrayList<Card> hand) {
		// The highest card is the last card, because we have sorted our list of cards above
		return hand.get(hand.size() - 1);
	}
	
	// ex.: if we have hand : 3C 3S 5C 5S 8S, it returns 5C
	private int getHighestCardRankInCombination(ArrayList<Card> hand, Combination combination) {
		Card result = null;
		switch (combination) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
		case THREE_OF_A_KIND:
			//hand is sorted, so middle(third) card is always combination part
			result = hand.get(2);
			break;
		case TWO_PAIR:

			Card firstPairCard = null;
			Card secondPairCard = null;
			for (int i = 0; i < 4; i++) {
				CardRank cardRank = hand.get(i).getCardRank();
				CardRank nextCardRank = hand.get(i + 1).getCardRank();
				if (cardRank.equals(nextCardRank)) {
					if (firstPairCard == null)
						firstPairCard = hand.get(i);
					else
						secondPairCard = hand.get(i);
				}
			}
			if (firstPairCard.getCardRankNumber() > secondPairCard.getCardRankNumber()) {
				result = firstPairCard;
			} else
				result = secondPairCard;

			break;
		case ONE_PAIR:
			for (int i = 0; i < 4; i++) {
				CardRank cardRank = hand.get(i).getCardRank();
				CardRank nextCardRank = hand.get(i + 1).getCardRank();
				if (cardRank.equals(nextCardRank)) {
					result = hand.get(i);
					break;
				}
			}
			break;
		default:
			// all other combinations requires highest card in hand
			result = getHighestCard(hand);
			break;
		}
		
		return result.getCardRankNumber();
	}

	private Combination getCombination(ArrayList<Card> hand) {
//		System.out.println("-------");
//		System.out.println(hand);

		Combination result = Combination.HIGH_CARD;

		if (isStraight(hand)) {
			result = Combination.STRAIGHT;
		}

		if (isFlash(hand)) {
			if (Combination.STRAIGHT.equals(result)) {
				result = Combination.STRAIGHT_FLUSH;
			} else {
				result = Combination.FLUSH;
			}
		}

		// if straight flush starts from ACE, then it's Royal flush
		if (Combination.STRAIGHT_FLUSH.equals(result) && hand.get(hand.size() - 1).getCardRank().equals(CardRank.ACE)) {
			result = Combination.ROYAL_FLUSH;
		}

		if (!Combination.ROYAL_FLUSH.equals(result) && !Combination.STRAIGHT_FLUSH.equals(result)) {
			Combination pairCombinationResult = getPairCombination(hand);
			if (pairCombinationResult.getCombinationNumber() > result.getCombinationNumber()) {
				result = pairCombinationResult;
			}
		}

		return result;
	}

	private boolean isStraight(ArrayList<Card> hand) {
		int firstCardRankNumber = hand.get(0).getCardRankNumber();
		int sum = 0; // straight if sum is 10 : 0+1+2+3+4
		for (Card card : hand) {
			sum = sum + (card.getCardRankNumber() - firstCardRankNumber);
		}
		if (sum == 10) {
			return true;
		} else
			return false;
	}

	private boolean isFlash(ArrayList<Card> hand) {
		Suit firstCardSuit = hand.get(0).getSuit();
		for (Card card : hand) {
			// if all suits are the same, then it is flash
			if (firstCardSuit.compareTo(card.getSuit()) != 0) {
				return false;
			}
		}
		return true;
	}

	// method returns one of those combinations
	// FOUR_OF_A_KIND FULL_HOUSE THREE_OF_A_KIND TWO_PAIR ONE_PAIR
	// if hand does not have pairs at all, then it returns HIGH_CARD
	private Combination getPairCombination(ArrayList<Card> hand) {
		Combination combination = Combination.HIGH_CARD;
		// sameRankSet values :
		// 1 - no the same cards, 2 - two the same cards, 3 - three the same cards etc.
		int sameRankSet1 = 1;
		int sameRankSet2 = 1;
		boolean isSet1 = true;
		// we have 5 cards, so we should compare them only 4 times
		for (int i = 0; i < 4; i++) {
			CardRank cardRank = hand.get(i).getCardRank();
			CardRank nextCardRank = hand.get(i + 1).getCardRank();

			if (cardRank.equals(nextCardRank)) {
				if (isSet1)
					sameRankSet1++;
				else
					sameRankSet2++;
			} else if (isSet1 && sameRankSet1 > 1) {
				isSet1 = false;
			}
		}

		if (sameRankSet1 == 4) {
			combination = Combination.FOUR_OF_A_KIND;
		} else if (sameRankSet1 + sameRankSet2 == 5) {
			combination = Combination.FULL_HOUSE;
		} else if (sameRankSet1 == 3) {
			combination = Combination.THREE_OF_A_KIND;
		} else if (sameRankSet1 == 2 && sameRankSet2 == 2) {
			combination = Combination.TWO_PAIR;
		} else if (sameRankSet1 == 2) {
			combination = Combination.ONE_PAIR;
		}

		return combination;
	}

}
