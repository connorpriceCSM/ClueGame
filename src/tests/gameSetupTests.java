package tests;


import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.ComputerPlayer;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class gameSetupTests {

	private static Board board;
	
	
	// set up the board as we've done every other time.
	// initialize will call methods to load people and weapons
	@BeforeClass
	public static void setUp()
	{
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "ClueLegend.txt");		
		board.initialize();
	}
	
	@Test
	public void testPeople()
	{   
		/*
		 * This method will need to test to make sure that
		 * 1) 6 players have been loaded in according to CluePlayers.txt
		 * 2) 1 of the 6 players is the human player
		 * 3) The location of a couple of selected players is correct
		 * 4) The color of those same selected players is correct
		 */
		ArrayList<Player> players = board.getPlayers();
		
		//Check to see if there are all 6 players
		assertEquals(6, players.size());
		
		//Test to see if one player is a human
		assertTrue(players.contains(board.getHumanPlayer()));
		
		//Test locations
		Player play1er = players.get(0);
		Player play2er = players.get(1);
		Player play3er = players.get(2);
		
		//Player one location
		assertEquals(8, play1er.getRow());
		assertEquals(21, play1er.getColumn());
		
		//Player two location
		assertEquals(19, play2er.getRow());
		assertEquals(21, play2er.getColumn());
		
		//Player three location
		assertEquals(0, play3er.getRow());
		assertEquals(14, play3er.getColumn());
		
		//Test colors of players
		assertEquals(Color.BLACK, play1er.getColor());
		assertEquals(Color.BLUE, play2er.getColor());
		assertEquals(Color.WHITE, play3er.getColor());
		
	}
	
	@Test
	public void testCards()
	{
		/*
		 * This method will need to test to make sure that
		 * 1) 6 weapon cards have been loaded
		 * 2) 6 player cards have been loaded
		 * 3) 9 rooms have been loaded
		 * 4) 3 select cards have already been seen by the player.
		 */
		ArrayList<Card> cards = board.getCards();
		ArrayList<String> weapons = board.getWeaponNames();
		ArrayList<String> rooms = board.getRoomNames();
		ArrayList<String> people = board.getPlayerNames();
		Player human = board.getHumanPlayer();
		
		//Check weapon cards
		assertEquals(6, weapons.size());
		assertTrue(weapons.contains("Lightsaber"));
		assertTrue(weapons.contains("Handguns"));
		assertTrue(weapons.contains("Staff"));
		assertTrue(weapons.contains("Machine Gun"));
		assertTrue(weapons.contains("Phaser"));
		assertTrue(weapons.contains("Stun Watch"));
		
		//Check player cards
		assertEquals(6, people.size());
		assertTrue(people.contains("James Bond"));
		assertTrue(people.contains("Lara Croft"));
		assertTrue(people.contains("Gandalf"));
		assertTrue(people.contains("Obi-Wan Kenobi"));
		assertTrue(people.contains("Spock"));
		assertTrue(people.contains("Rambo"));
		
		//Test for the 9 room cards
		assertEquals(9, rooms.size());
		assertTrue(rooms.contains("Bedroom"));
		assertTrue(rooms.contains("Garden"));
		assertTrue(rooms.contains("Kitchen"));
		assertTrue(rooms.contains("Sauna"));
		assertTrue(rooms.contains("Foyer"));
		assertTrue(rooms.contains("Entertainment room"));
		assertTrue(rooms.contains("Observatory"));
		assertTrue(rooms.contains("Bathroom"));
		assertTrue(rooms.contains("Library"));
		
		//Check player has seen cards
		assertEquals(3,(human.getSeenCards()).size());
	}
	
	@Test
	public void testDraw()
	{
		/*
		 * This method will need to test to make sure that
		 * 21 total cards are given out, 3 going to each player for a total of 18
		 */
		ArrayList<Player> players = board.getPlayers();
		ArrayList<Card> cards = board.getCards();
		//Test total number of cards
		assertEquals(cards.size(), 21);
		for (int i = 0; i < players.size(); i++) {
			
			// check to make sure the players have the same if not, roughly equal number of cards.
				assertTrue((players.get(i).getCards().size() == 3));
			 
			
		}

	}
}
