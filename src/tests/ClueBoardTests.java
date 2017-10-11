package tests;


import static org.junit.Assert.*;


import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

import clueGame.*;
//Authors: Amelia Atiles and Connor Price
//  Original Code Methods credited to Mark Baldwin
public class ClueBoardTests {

	// boundaries of the grid and legend
	private static Board board;
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 22;

	// create board and configure files.
	@BeforeClass
	public static void setUpBoard()
	{
		board = board.getInstance();
		board.setConfigFiles("ClueMap.csv", "ClueLegend.txt");
		board.initialize();
	}
	@Test
	public  void testLegend()
	{	// test legend values as well as number of characters
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());

		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Entertainment room", legend.get('E'));
		assertEquals("Sauna", legend.get('S'));
		assertEquals("Bedroom", legend.get('D'));
		assertEquals("Closet", legend.get('C'));

	}
	@Test
	public void testBoardDimensions()
	{
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS,board.getNumColumns());
	}
	@Test
	public void testDoorDirections() {
		
		// Tests a selection of doors from the ClueMap, including a doubledoor.
		BoardCell room = board.getCellAt(5, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(1, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(22, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(18, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(19, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// Test a room that definitely isn't a door
		room = board.getCellAt(2, 16);
		assertFalse(room.isDoorway());	
		// Test that the pathways are not doors
		BoardCell cell = board.getCellAt(0, 4);
		assertFalse(cell.isDoorway());		

	}
	@Test
	public void testNumDoorways()
	{ 
		int numDoors = 0;
		for (int row=0; row< board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		//I swear that my completely unique layout ACTUALLY has 16 doors just like yours 
		// :D
		Assert.assertEquals(16, numDoors);
	}

	// Test room cells and make sure they correspond correctly
	@Test
	public void testRoomInitials() {
		// Test spots for all eleven characters.
		assertEquals('E', board.getCellAt(22, 0).getInitial());
		assertEquals('F', board.getCellAt(17, 8).getInitial());
		assertEquals('D', board.getCellAt(20,11).getInitial());
		assertEquals('G', board.getCellAt(22, 21).getInitial());
		assertEquals('S', board.getCellAt(16, 21).getInitial());
		assertEquals('B', board.getCellAt(7, 16).getInitial());
		assertEquals('L', board.getCellAt(1, 8).getInitial());
		assertEquals('K', board.getCellAt(0, 0).getInitial());
		assertEquals('O', board.getCellAt(9, 1).getInitial());
		// Test a walkway
		assertEquals('P', board.getCellAt(17, 14).getInitial());
		// Test the closet
		assertEquals('C', board.getCellAt(12,9).getInitial());
	}

}
