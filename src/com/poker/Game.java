package com.poker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {

	public static void main(String[] args) {
		args = new String[1]; // TODO remove those tmp two lines
		args[0] = "pokerdata.txt";
		CombinationCounter combinationCounter = new CombinationCounter();
		if (args.length > 0) {
			String fileName = args[0];

			int player1WinCounter = 0;
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String line;
				// read data from the file line by line
				int cnt = 1;
				System.out.println("Hand	 	Player 1	 	Player 2	Player1 is winner");
				while ((line = br.readLine()) != null) {
					System.out.print(cnt+++" ");
					ArrayList<Card> cards1 = new ArrayList<Card>();
					ArrayList<Card> cards2 = new ArrayList<Card>();

					// one line example:
					// 8C TS KC 9H 4S 7D 2S 5D 3S AC
					// 5 cards has one player, and second 5 cards - second player
					int cardCount = 0;

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
					if (cnt > 5) break;// TODO 
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
