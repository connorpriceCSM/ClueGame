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

public class gameActionTests {

	private static Board board;
	ComputerPlayer player =  new ComputerPlayer();
	private static Card mustardCard;
	  private static Card whiteCard;
	  private static Card diningRoomCard;
	  private static Card studyCard;
	  private static Card candlestickCard;
	  private static Card revolverCard;
	  private static Card peacockCard;
	  private static Card libraryCard;
	  private static Card wrenchCard;
	  private static Card knifeCard;
	  private static Card greenCard;
	  private static Card plumCard;
	  private static Card ballroomCard;
	  private static Card kitchenCard;
	  private static Card hallCard;
	  private static Card pipeCard;
	  private static Card scarletCard;
	  private static Card ropeCard;
	  private static Card conservatoryCard;
	
	
	// set up the board as we've done every other time.
	// initialize will call methods to load people and weapons
	@BeforeClass
	public static void setUp()
	{
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "ClueLegend.txt");		
		board.initialize();
		board.dealCards();
		
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
	    whiteCard = new Card("Mrs. White", CardType.PERSON);
	    peacockCard = new Card("Mrs. Peacock", CardType.PERSON);
	    greenCard = new Card("Mr. Green", CardType.PERSON);
	    plumCard = new Card("Professor Plum", CardType.PERSON);
	    scarletCard = new Card("Miss Scarlet", CardType.PERSON);
	    
	    diningRoomCard = new Card("Dining Room", CardType.ROOM);
	    studyCard = new Card("Study", CardType.ROOM);
	    ballroomCard = new Card("Ballroom", CardType.ROOM);
	    kitchenCard = new Card("Kitchen", CardType.ROOM);
	    hallCard = new Card("Hall", CardType.ROOM);
	    libraryCard = new Card("Library", CardType.ROOM);
	    conservatoryCard = new Card("Conservatory", CardType.ROOM);
	    
	    candlestickCard = new Card("Candlestick", CardType.WEAPON);
	    revolverCard = new Card("Revolver", CardType.WEAPON);
	    wrenchCard = new Card("Wrench", CardType.WEAPON);
	    knifeCard = new Card("Knife", CardType.WEAPON);
	    pipeCard = new Card("Lead Pipe",CardType.WEAPON);
	    ropeCard = new Card("Rope", CardType.WEAPON);
	}
	
	
	
	public void testTargetsNoRooms()
	{
     
     
		
		
	}
	@Test
	public void testTargetsRoomNotVisted()
	{
		 board.calcTargets(2, 13, 2);
	     player =  new ComputerPlayer();
	     //doorway cell for the Lounge
	     BoardCell idealCell = board.getCellAt(1, 12);
	     BoardCell selectedCell = player.pickLocation(board.getTargets());
	     assertEquals(idealCell, selectedCell);
	     
	     board.calcTargets(19, 19, 2);
	     player =  new ComputerPlayer();
	     //doorway cell for the Garden
	     BoardCell idealCell2 = board.getCellAt(20, 18);
	     BoardCell selectedCell2 = player.pickLocation(board.getTargets());
	     assertEquals(idealCell2, selectedCell2);
	     
	     board.calcTargets(22, 10, 2);
	     player =  new ComputerPlayer();
	     //doorway cell for the D Room I don't remember
	     BoardCell idealCell3 = board.getCellAt(21, 11);
	     BoardCell selectedCell3 = player.pickLocation(board.getTargets());
	     assertEquals(idealCell3, selectedCell3);
	     
	     board.calcTargets(10, 14, 1);
	     player =  new ComputerPlayer();
	     //doorway cell for the Sauna
	     BoardCell idealCell4 = board.getCellAt(10, 15);
	     BoardCell selectedCell4 = player.pickLocation(board.getTargets());
	     assertEquals(idealCell4, selectedCell4);
	     
	     board.calcTargets(6, 1, 1);
	     player =  new ComputerPlayer();
	     //doorway cell for the kitchen
	     BoardCell idealCell5 = board.getCellAt(5, 1);
	     BoardCell selectedCell5 = player.pickLocation(board.getTargets());
	     assertEquals(idealCell5, selectedCell5);
	 
	     
		
	}
	
	public void testTargetsRoomVisited()
	{
		
	}
}
