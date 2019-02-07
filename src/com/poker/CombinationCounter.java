package com.poker;

import java.util.List;

import com.poker.enums.Combination;

public class CombinationCounter {

	public boolean isPlayerOneWinner(Hand hand1, Hand hand2) {
		Combination combination1 = hand1.getCombination();
		Combination combination2 = hand2.getCombination();
		boolean result = false;
		// If combination are the same, then compare cards from the biggest to the smallest
		if (combination1.getCombinationNumber() == combination2.getCombinationNumber()) {
			int cardComparison = compareCards(hand1.getCardsSortedByCombination(), hand2.getCardsSortedByCombination());
			if (cardComparison > 0) {
				result = true;
			}			
		} else
			result = combination1.getCombinationNumber() > combination2.getCombinationNumber();
		return result;
	}

	//the value 0 if list1 == list2; a value less than 0 if list1 < list2; and a value greater than 0 if list1 > list2
	private int compareCards(List<Card> list1, List<Card> list2) {
		if (list1 == null || list2 == null || list1.size() != list2.size()) {
			System.out.println("Bad lists:");
			System.out.println("list1 " + list1);
			System.out.println("list2 " + list2);
			return 0;
		}
		for (int i = list1.size() - 1; i >= 0; i--) {
			Integer cardValue1 = list1.get(i).getCardRankNumber();
			Integer cardValue2 = list2.get(i).getCardRankNumber();
			if (cardValue1.equals(cardValue2))
				continue;
			else
				return cardValue1.compareTo(cardValue2);
		}
		return 0;
	}
}
