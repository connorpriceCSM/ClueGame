package tests;


import clueGame.Board;

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
		 * 2) 1 of the 6 players is the human player (easy to just make it the first player read from the file --> James Bond)
		 * 3) The location of a couple of selected players is correct
		 * 4) The color of those same selected players is correct
		 */
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		
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
		
	}
	
	@Test
	public void testDraw()
	{
		/*
		 * This method will need to test to make sure that
		 * 21 total cards are given out, 3 players will get 4 cards and the remaining 3 players will get 3 cards.
		 * 
		 */
		
	}
}
