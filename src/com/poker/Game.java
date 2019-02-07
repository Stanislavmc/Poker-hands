package com.poker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

	public static void main(String[] args) {
		if (args.length > 0) {
			String fileName = args[0];

			int player1WinCounter = 0;
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String line;
				CombinationCounter combinationCounter = new CombinationCounter();
				// read data from the file line by line
				int counter = 1;
				System.out.println("Hand\t\tPlayer 1\t\tPlayer 2\tPlayer1 is winner");

				while ((line = br.readLine()) != null) {
					List<Card> cards1 = new ArrayList<Card>();
					List<Card> cards2 = new ArrayList<Card>();
					int cardCount = 0;

					// one line example:
					// 8C TS KC 9H 4S 7D 2S 5D 3S AC
					// 5 cards has one player, and second 5 cards - second player
					// split cards between two players
					for (String cardCode : line.split(" ")) {
						if (cardCount++ < 5) {
							cards1.add(new Card(cardCode));
						} else {
							cards2.add(new Card(cardCode));
						}
					}
					Hand hand1 = new Hand(cards1);
					Hand hand2 = new Hand(cards2);
					boolean player1Winner = combinationCounter.isPlayerOneWinner(hand1, hand2);
					if (player1Winner) {
						player1WinCounter++;
					}
					System.out.println(counter++ + " \t" + hand1 + "\t\t" + hand2 + "\t" + player1Winner);
					System.out.println("\t"+hand1.getCombination()+"\t\t"+hand2.getCombination());	
					System.out.println();
				}
				System.out.println("Player1 won " + player1WinCounter + " times");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Please enter file name with poker data");
		}
	}
}
