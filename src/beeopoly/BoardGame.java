package beeopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class BoardGame {

	public static String[] players;
	public static ArrayList<Player> activePlayers;
	public static ArrayList<Player> playerRank;
	public static ArrayList<Garden> gameBoard;
	public boolean quit = false;
	
	public static void main(String[] args) {

		//initialise game with fields, gardens and boardgame array?
		welcome();
		register();
		//enter game loop While(!quit){} etc
	}

	public static void register() {
		Scanner sc = new Scanner(System.in);
		players = new String[4];

		for(int i = 0; i<4; i++) {
			System.out.printf("Please Enter player %d", i+1);
			System.out.println();
			String player = sc.nextLine();
			if(checkPlayer(player)) {
				players[i]= player;
				System.out.println("Player Successfully added");
				if(i>1) {
					System.out.println("Would you like to add another player?");
					int extraPlayer = sc.nextInt();
					
				}
			}else {
				System.err.printf("Error %s has been taken.", player);
				System.out.println();
				i--;
			}
			
		}
		
	}

	private static boolean checkPlayer(String player) {
		for (String name : players) {
			if(player.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private static void welcome() {

		System.out.println("Welcome to Beeopoly");
		System.out.println("Rules of the game.....");
		
	}
	
	private void setPlayerOrder(ArrayList<Player> playOrder) {
		Collections.shuffle(playOrder);
	}
	
	
	
	private void removePlayer(Player player) {
		activePlayers.remove(player);
		playerRank.add(player);
	}
	
	private void endGame() {
		//TO DO
	}

}
