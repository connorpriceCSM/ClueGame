package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.


 * Authors:  Connor Price & Amelia Atiles
 */

import java.util.Set;


import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurBoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	// Set up the board
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "ClueLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test the back left corner of the map
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has a walkway to the right
		testList = board.getAdjList(3, 3);
		assertEquals(0, testList.size());
		// Test one that has walkway above it
		testList = board.getAdjList(19, 9);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(13, 18);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(7, 16);
		assertEquals(0, testList.size());
		// Test one that has a walkway to the left
		testList = board.getAdjList(20, 15);
		assertEquals(0, testList.size());
		//Test one that has a walkway below it
		testList = board.getAdjList(5, 8);
		assertEquals(0, testList.size());
		//Test the lower right corner of the map
		testList = board.getAdjList(22, 21);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway.

	// These tests are DARK RED on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY UP 
		Set<BoardCell> testList = board.getAdjList(9, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 2)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(22, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(22, 3)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(16, 20);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 20)));
		//TEST DOORWAY RIGHT
		testList = board.getAdjList(1, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 13)));

	}

	// Test adjacency at entrance to rooms
	// These tests are YELLOW in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(18, 3);
		assertTrue(testList.contains(board.getCellAt(18, 2)));
		assertTrue(testList.contains(board.getCellAt(17, 3)));
		assertTrue(testList.contains(board.getCellAt(19, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 1);
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(10, 14);
		assertTrue(testList.contains(board.getCellAt(11, 14)));
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertTrue(testList.contains(board.getCellAt(10, 13)));
		assertTrue(testList.contains(board.getCellAt(10, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(5, 16);
		assertTrue(testList.contains(board.getCellAt(5, 17)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertEquals(2, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test at the bottom edge of the board, one pathway piece
		Set<BoardCell> testList = board.getAdjList(22, 14);
		assertTrue(testList.contains(board.getCellAt(21, 14)));
		assertEquals(1, testList.size());

		// Test on right edge of board, above room,  two pathway pieces
		testList = board.getAdjList(20, 21);
		assertTrue(testList.contains(board.getCellAt(20, 20)));
		assertTrue(testList.contains(board.getCellAt(19, 21)));
		assertEquals(2, testList.size());

		// Test right below room entrance, three pathway pieces
		testList = board.getAdjList(2, 13);
		assertTrue(testList.contains(board.getCellAt(1, 13)));
		assertTrue(testList.contains(board.getCellAt(3, 13)));
		assertTrue(testList.contains(board.getCellAt(2, 14)));
		assertEquals(3, testList.size());

		// Test surrounded by four pathway pieces
		testList = board.getAdjList(10,4);
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(9, 4)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 3)));
		assertEquals(4, testList.size());

		// Test on bottom edge of board, two pathway pieces
		testList = board.getAdjList(22, 10);
		assertTrue(testList.contains(board.getCellAt(22, 11)));
		assertTrue(testList.contains(board.getCellAt(21, 10)));
		assertEquals(2, testList.size());

		// Test on right edge of board, three pathway pieces
		testList = board.getAdjList(8, 21);
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertTrue(testList.contains(board.getCellAt(9, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertEquals(3, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(9, 15);
		assertTrue(testList.contains(board.getCellAt(9, 16)));
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertEquals(3, testList.size());

		// Test on top edge of the board,
		testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(1, testList.size());

		// Test on left edge of the board,
		testList = board.getAdjList(12, 0);
		assertTrue(testList.contains(board.getCellAt(13, 0)));
		assertEquals(1, testList.size());


	}


	// Tests of just pathways, 1 step, includes on edge of board
	// and beside room
	// These are MAGENTA on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(14, 1, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		assertTrue(targets.contains(board.getCellAt(14, 0)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));	

		board.calcTargets(6, 10, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 10)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));	
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(6, 11)));
	}

	// Tests of just pathways, 2 steps
	// These are MAGENTA on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(19, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 14)));
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		assertTrue(targets.contains(board.getCellAt(18, 13)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));

		board.calcTargets(6, 20, 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 21)));
		assertTrue(targets.contains(board.getCellAt(8, 20)));	
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
	}

	// Tests of just pathways, three steps
	// These are MAGENTA on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(6, 20, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 17)));
		assertTrue(targets.contains(board.getCellAt(6, 19)));
		assertTrue(targets.contains(board.getCellAt(7, 18)));
		assertTrue(targets.contains(board.getCellAt(7, 20)));	
		assertTrue(targets.contains(board.getCellAt(8, 19)));
		assertTrue(targets.contains(board.getCellAt(9, 20)));
		assertTrue(targets.contains(board.getCellAt(8, 21)));


	}	


	//Test getting into the room with max steps needed
	// This is RUST on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly one step away
		board.calcTargets(20, 19, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		// step to enter the room
		assertTrue(targets.contains(board.getCellAt(20, 18)));
		// Other steps
		assertTrue(targets.contains(board.getCellAt(20, 20)));
		assertTrue(targets.contains(board.getCellAt(19, 19)));
	}

	// Test getting into room, doesn't require all steps
	// This is CYAN on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(5, 4, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(2, 4)));
		assertTrue(targets.contains(board.getCellAt(8, 4)));
		//close proximity but not yet doors
		assertTrue(targets.contains(board.getCellAt(4, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		// maximum stretch but not doors
		assertTrue(targets.contains(board.getCellAt(6, 2)));
		assertTrue(targets.contains(board.getCellAt(7, 3)));
		assertTrue(targets.contains(board.getCellAt(7, 5)));
		// First door only taking 2 steps
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		// Second door taking max steps
		assertTrue(targets.contains(board.getCellAt(4, 6)));


	}

	// Test getting out of a room
	// This is DARK GREEN on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(17, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		// Take two steps out of the doorway
		board.calcTargets(17, 7, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		assertTrue(targets.contains(board.getCellAt(16, 8)));
	}

}
