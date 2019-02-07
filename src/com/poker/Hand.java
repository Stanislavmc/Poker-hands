package com.poker;

import java.util.ArrayList;
import java.util.Comparator;

import com.poker.enums.CardRank;
import com.poker.enums.Combination;
import com.poker.enums.Suit;

public class Hand {
	private ArrayList<Card> cards;
	private ArrayList<Card> cardsSortedByValue = new ArrayList<>();
	private ArrayList<Card> firstPair = new ArrayList<>();
	private ArrayList<Card> secondPair = new ArrayList<>();
	private Combination combination;

	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		cards.sort(Comparator.comparing(Card::getCardRankNumber));
		combination = countCombination(cards);
		initCardsLists();
		

	}
	
	
	private void initCardsLists() {
		// if we have cards 5C 5S 6C 7S JS, then cards with combination are 5C 5S, 
		// other are without combination : 6C 7S JS
		ArrayList<Card> cardsWithCombination = new ArrayList<>();
		ArrayList<Card> cardsWithoutCombination = new ArrayList<>();
		
		switch (combination) {
		case ROYAL_FLUSH:
		case STRAIGHT_FLUSH:
		case FLUSH:
		case STRAIGHT:
			cardsWithCombination.addAll(cards);
			break;
		case FOUR_OF_A_KIND:
		case THREE_OF_A_KIND:
		case ONE_PAIR:
			cardsWithCombination.addAll(firstPair);
			break;
		case FULL_HOUSE:
			if(firstPair.size()==2) {
				cardsWithCombination.addAll(firstPair);
				cardsWithCombination.addAll(secondPair);
			} else {
				cardsWithCombination.addAll(secondPair);
				cardsWithCombination.addAll(firstPair);
			}
			break;
		case TWO_PAIR:
			cardsWithCombination.addAll(firstPair);
			cardsWithCombination.addAll(secondPair);
			cardsWithCombination.sort(Comparator.comparing(Card::getCardRankNumber));
			break;
		case HIGH_CARD:
			cardsWithCombination.add(cards.get(cards.size()-1));
			break;
		default:
			break;
		}

		cardsWithoutCombination.addAll(cards);
		cardsWithoutCombination.removeAll(cardsWithCombination);
		
		// get list of cards, where on the right will be cards with combinations, on the left - without combinations
		cardsSortedByValue.addAll(cardsWithoutCombination);
		cardsSortedByValue.addAll(cardsWithCombination);
	}
	

	public Card getHighestCard() {
		// The highest card is the last card, because we have sorted our list of cards above
		return cards.get(cards.size() - 1);
	}
	
	private Combination countCombination(ArrayList<Card> cards) {

		Combination result = Combination.HIGH_CARD;

		if (isStraight(cards)) {
			result = Combination.STRAIGHT;
		}

		if (isFlash(cards)) {
			if (Combination.STRAIGHT.equals(result)) {
				result = Combination.STRAIGHT_FLUSH;
			} else {
				result = Combination.FLUSH;
			}
		}

		// if straight flush starts from ACE, then it's Royal flush
		if (Combination.STRAIGHT_FLUSH.equals(result) && cards.get(cards.size() - 1).getCardRank().equals(CardRank.ACE)) {
			result = Combination.ROYAL_FLUSH;
		}

		if (!Combination.ROYAL_FLUSH.equals(result) && !Combination.STRAIGHT_FLUSH.equals(result)) {
			Combination pairCombinationResult = getPairCombination(cards);
			if (pairCombinationResult.getCombinationNumber() > result.getCombinationNumber()) {
				result = pairCombinationResult;
			}
		}

		return result;
	}

	// method returns one of those combinations
	// FOUR_OF_A_KIND FULL_HOUSE THREE_OF_A_KIND TWO_PAIR ONE_PAIR
	// if hand does not have pairs at all, then it returns HIGH_CARD
	private Combination getPairCombination(ArrayList<Card> hand) {
		Combination combination = Combination.HIGH_CARD;
		// sameRankSet values :
		// 1 - no the same cards, 2 - two the same cards, 3 - three the same cards etc.
		boolean isSet1 = true;
		// we have 5 cards, so we should compare them only 4 times
		for (int i = 0; i < 4; i++) {
			Card card = hand.get(i);
			Card nextCard = hand.get(i + 1);

			if (card.getCardRank().equals(nextCard.getCardRank())) {
				if (isSet1) {
					addIfNotExists(firstPair, card);
					addIfNotExists(firstPair, nextCard);
				} else {
					addIfNotExists(secondPair, card);
					addIfNotExists(secondPair, nextCard);
				}
			} else if (isSet1 && firstPair.size() > 1) {
				isSet1 = false;
			}
		}

		if (firstPair.size() == 4) {
			combination = Combination.FOUR_OF_A_KIND;
		} else if (firstPair.size() + secondPair.size() == 5) {
			combination = Combination.FULL_HOUSE;
		} else if (firstPair.size() == 3) {
			combination = Combination.THREE_OF_A_KIND;
		} else if (firstPair.size() == 2 && secondPair.size() == 2) {
			combination = Combination.TWO_PAIR;
		} else if (firstPair.size() == 2) {
			combination = Combination.ONE_PAIR;
		}

		return combination;
	}

	private <E> void addIfNotExists(ArrayList<E> list, E element) {
		if(!list.contains(element)) {
			list.add(element);
		}
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

	public Combination getCombination() {
		return combination;
	}
	
	public ArrayList<Card> getCardsSortedByValue() {
		return cardsSortedByValue;
	}
	
	@Override
	public String toString() {
		return cardsSortedByValue.toString();
	}
}
