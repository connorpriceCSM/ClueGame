package tests;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import experiment.BoardCell;
import experiment.IntBoard;


//Authors: Amelia Atiles and Connor Price
public class IntBoardTests {
	private IntBoard testBoard;
	//Failing Tests
	//4x4 matrix; Test [0][0],[3][3],[1][3],[3][0],[1][1],[2][2]
	/*   0 1 2 3 X
	 * 0 t x x t
	 * 1 x t x x
	 * 2 x x t x
	 * 3 t t x t
	 * Y
	 */
	@Before
	public void setUp() {
		testBoard = new IntBoard(4,4);
		System.out.println("In @Before");
	}
	//Test Adjacentcy
	//Failed tests
	@Test
	public void testFAdjecentcy0() {
		BoardCell cell = testBoard.getCell(0,0);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(1, 0)));
		assertTrue(testList.contains(testBoard.getCell(0, 1)));
		assertTrue(testList.contains(testBoard.getCell(1, 1)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testFAdjecentcy3() {
		BoardCell cell = testBoard.getCell(3,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(3, 2)));
		assertTrue(testList.contains(testBoard.getCell(2, 3)));
		assertTrue(testList.contains(testBoard.getCell(2, 2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testFAdjecentcy3_0() {
		BoardCell cell = testBoard.getCell(3,0);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(2, 0)));
		assertTrue(testList.contains(testBoard.getCell(3, 1)));
		assertTrue(testList.contains(testBoard.getCell(1, 2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testFAdjecentcy1() {
		BoardCell cell = testBoard.getCell(1,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(0, 3)));
		assertTrue(testList.contains(testBoard.getCell(1, 2)));
		assertTrue(testList.contains(testBoard.getCell(2, 3)));
		assertTrue(testList.contains(testBoard.getCell(0, 2)));
		assertTrue(testList.contains(testBoard.getCell(2, 2)));
		assertEquals(5, testList.size());
	}
	
	@Test
	public void testFAdjecentcy11() {
		BoardCell cell = testBoard.getCell(1,1);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(1, 0)));
		assertTrue(testList.contains(testBoard.getCell(1, 2)));
		assertTrue(testList.contains(testBoard.getCell(0, 1)));
		assertTrue(testList.contains(testBoard.getCell(2, 1)));
		assertTrue(testList.contains(testBoard.getCell(0, 0)));
		assertTrue(testList.contains(testBoard.getCell(0, 2)));
		assertTrue(testList.contains(testBoard.getCell(2, 2)));
		assertTrue(testList.contains(testBoard.getCell(0, 2)));
		assertEquals(8, testList.size());
	}
	
	@Test
	public void testFAdjecentcy22() {
		BoardCell cell = testBoard.getCell(2,2);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(1, 2)));
		assertTrue(testList.contains(testBoard.getCell(3, 2)));
		assertTrue(testList.contains(testBoard.getCell(2, 3)));
		assertTrue(testList.contains(testBoard.getCell(2, 1)));
		assertTrue(testList.contains(testBoard.getCell(3, 3)));
		assertTrue(testList.contains(testBoard.getCell(3, 1)));
		assertTrue(testList.contains(testBoard.getCell(1, 1)));
		assertTrue(testList.contains(testBoard.getCell(1, 3)));
		assertEquals(8, testList.size());
	}
	//Tests for the Target Methods
	@Test
	public void testTargets0() {
		BoardCell cell = testBoard.getCell(2, 2);
		testBoard.calcTargets(cell, 3);
		Set targets = testBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(testBoard.getCell(3, 0)));
		assertTrue(targets.contains(testBoard.getCell(2, 0)));
		assertTrue(targets.contains(testBoard.getCell(0, 1)));
		assertTrue(targets.contains(testBoard.getCell(1, 2))); //not there
		assertTrue(targets.contains(testBoard.getCell(0, 3)));
		assertTrue(targets.contains(testBoard.getCell(0, 2)));
	}
	
	@Test
	public void testTargets1() {
		BoardCell cell = testBoard.getCell(0, 0);
		testBoard.calcTargets(cell, 1);
		Set targets = testBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(testBoard.getCell(3, 0))); 
		assertTrue(targets.contains(testBoard.getCell(2, 1)));
		assertTrue(targets.contains(testBoard.getCell(0, 1)));
		assertTrue(targets.contains(testBoard.getCell(1, 2)));
		assertTrue(targets.contains(testBoard.getCell(0, 3)));
		assertTrue(targets.contains(testBoard.getCell(1, 0)));
	}
	
	
	@Test
	public void testTargets3() {
		BoardCell cell = testBoard.getCell(0, 0);
		testBoard.calcTargets(cell, 2);
		Set targets = testBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(testBoard.getCell(3, 0)));
		assertTrue(targets.contains(testBoard.getCell(2, 1)));
		assertTrue(targets.contains(testBoard.getCell(0, 1)));
		assertTrue(targets.contains(testBoard.getCell(1, 2)));
		assertTrue(targets.contains(testBoard.getCell(0, 3)));
		assertTrue(targets.contains(testBoard.getCell(1, 0)));
	}
	
	
	//Passing Tests
	//Test Adjecentcy Methods
		@Test
		public void testAdjecentcy0() {
			BoardCell cell = testBoard.getCell(0,0);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(1, 0)));
			assertTrue(testList.contains(testBoard.getCell(0, 1)));
			assertEquals(2, testList.size());
		}
		
		@Test
		public void testAdjecentcy3() {
			BoardCell cell = testBoard.getCell(3,3);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(3, 2)));
			assertTrue(testList.contains(testBoard.getCell(2, 3)));
			assertEquals(2, testList.size());
		}
		
		@Test
		public void testAdjecentcy3_0() {
			BoardCell cell = testBoard.getCell(3,0);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(2, 0)));
			assertTrue(testList.contains(testBoard.getCell(3, 1)));
			assertEquals(2, testList.size());
		}
		
		@Test
		public void testAdjecentcy1() {
			BoardCell cell = testBoard.getCell(1,3);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(0, 3)));
			assertTrue(testList.contains(testBoard.getCell(1, 2)));
			assertTrue(testList.contains(testBoard.getCell(2, 3)));
			assertEquals(3, testList.size());
		}
		
		@Test
		public void testAdjecentcy11() {
			BoardCell cell = testBoard.getCell(1,1);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(1, 0)));
			assertTrue(testList.contains(testBoard.getCell(1, 2)));
			assertTrue(testList.contains(testBoard.getCell(0, 1)));
			assertTrue(testList.contains(testBoard.getCell(2, 1)));
			assertEquals(4, testList.size());
		}
		
		@Test
		public void testAdjecentcy22() {
			BoardCell cell = testBoard.getCell(2,2);
			Set<BoardCell> testList = testBoard.getAdjList(cell);
			assertTrue(testList.contains(testBoard.getCell(1, 2)));
			assertTrue(testList.contains(testBoard.getCell(3, 2)));
			assertTrue(testList.contains(testBoard.getCell(2, 3)));
			assertTrue(testList.contains(testBoard.getCell(2, 1)));
			assertEquals(4, testList.size());
		}
		//TestTarget
		@Test
		public void testTarget0_1() {
			BoardCell cell = testBoard.getCell(0, 0);
			testBoard.calcTargets(cell, 1);
			Set targets = testBoard.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(testBoard.getCell(1, 0)));
			assertTrue(targets.contains(testBoard.getCell(0, 1)));
			
		}
		
		@Test
		public void testTarget0_2() {
			BoardCell cell = testBoard.getCell(0, 0);
			testBoard.calcTargets(cell, 2);
			Set targets = testBoard.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(testBoard.getCell(2, 0)));
			assertTrue(targets.contains(testBoard.getCell(0, 2)));
			assertTrue(targets.contains(testBoard.getCell(1, 1)));
			
		}
		
		@Test
		public void testTargets0_3() {
			BoardCell cell = testBoard.getCell(0, 0);
			testBoard.calcTargets(cell, 3);
			Set targets = testBoard.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(testBoard.getCell(3, 0)));
			assertTrue(targets.contains(testBoard.getCell(2, 1)));
			assertTrue(targets.contains(testBoard.getCell(0, 1)));
			assertTrue(targets.contains(testBoard.getCell(1, 2)));
			assertTrue(targets.contains(testBoard.getCell(0, 3)));
			assertTrue(targets.contains(testBoard.getCell(1, 0)));
		}
		
		@Test
		public void testTargets2_2() {
			BoardCell cell = testBoard.getCell(2, 2);
			testBoard.calcTargets(cell, 2);
			Set targets = testBoard.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(testBoard.getCell(2, 0)));
			assertTrue(targets.contains(testBoard.getCell(1, 1)));
			assertTrue(targets.contains(testBoard.getCell(3, 1)));
			assertTrue(targets.contains(testBoard.getCell(0, 2)));
			assertTrue(targets.contains(testBoard.getCell(1, 3)));
			assertTrue(targets.contains(testBoard.getCell(3, 3)));
		}
}
