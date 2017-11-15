package clueGame;
import java.awt.Color;
import java.awt.Graphics2D;

//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int cellRow;
	private int cellColumn;
	private char cellInitial;
	private char doorChar;
	private DoorDirection direction;
	private int index;
	private boolean showRoomName;
	private int x;
	private int y;
	public static int PIECE_SIZE = 25;
	private final int DOOR_SLIVER = 5;
	/*
	 * Easier to just use a string type as the 3rd parameter
	 * because some cells will have more than one character for doors
	 * like BU for example.
	 */
	//primary constructor
	public BoardCell(int row, int column, String characters)
	{
		this.cellRow = row;
		this.cellColumn = column;
		this.showRoomName = false;
		x = cellRow * PIECE_SIZE;
		y = cellColumn * PIECE_SIZE;
		// BoardCell is guaranteed to have at least one character
		cellInitial = characters.charAt(0);
		direction = DoorDirection.NONE;
		// if this BoardCell has two characters, check for a door.
		if(characters.length() == 2)
		{
			// accounting for Baldwin's room placements
			if(characters.charAt(1) != 'N')
			{
			doorChar = characters.charAt(1);
			setDoor();
			}
			else
			{
				showRoomName = true;
			}
		}
		// method to set door 
		
	}
	// secondary constructor
	public BoardCell(int row, int column)
	{
		this.cellRow = row;
	}
	// setter methods
	public void setRow(int row) 
	{
		this.cellRow = row;
	}
	public void setColumn(int column) 
	{
		this.cellColumn = column;
	}
	public void setInitial(char initial) 
	{
		this.cellInitial = initial;
	}
	public void setDoorChar(char doorChar) 
	{
		this.doorChar = doorChar;
	}
	public void setDirection(DoorDirection direction) 
	{
		this.direction = direction;
	}
	// getter methods as well as booleans for doorways,pathways,and rooms
	public int getRow() 
	{
		return cellRow;
	}
	public int getColumn() 
	{
		return cellColumn;
	}
	
	public char getInitial()
	{
		return cellInitial;
	}
	public DoorDirection getDoorDirection()
	{
		return direction;
	}
	
	//check if this cell is a pathway
	public boolean isPathway()
	{
		if(cellInitial == Board.WALKWAY_CHARACTER)
		{
			return true;
		}
		return false;
	}
	//check if this cell is a room
	public boolean isRoom()
	{
		if (cellInitial != Board.WALKWAY_CHARACTER && cellInitial!= Board.CLOSET_CHARACTER && !isDoorway())
		{
			return true;
		}
		return false;
	}
	// check if this cell is a doorway
	public boolean isDoorway()
	{
		if(direction != DoorDirection.NONE)
		{
			return true;
		}
		return false;
	}
	// depending on the secondary character, set the door direction for this cell
	public void setDoor()
	{
		switch(doorChar)
		{
		case 'U':
			direction = DoorDirection.UP;
			break;
		case 'D':
			direction = DoorDirection.DOWN;
			break;
		case 'R':
			direction = DoorDirection.RIGHT;
			break;
		case 'L':
			direction = DoorDirection.LEFT;
			break;
		default:
			direction = DoorDirection.NONE;
			break;	
		}
	}
	public void draw(Graphics2D g)
	{
		// Brown tiles for pathways
		if(this.isPathway())
		{
			Color brown = new Color(165,42,42);
			g.setColor(brown);
		}
		// solid gray tiles for the rooms!
		else
		{
			g.setColor(Color.darkGray);
		}
		g.fillRect(x, y, PIECE_SIZE, PIECE_SIZE);
		
		// Draw black outline of the square
		if(this.isPathway())
		{
			drawPathway(g);
		}
		// draw green doorway squares!!
		if(this.isDoorway())
		{
			drawDoorway(g);
		}
		// Make Orange titled rooms cause I can!
		if(this.isRoom())
		{
			drawRoomName(g);
		}
		
	}
	public void drawPathway(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(x,y,PIECE_SIZE, PIECE_SIZE );
	}
	
	public void drawDoorway(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		
		if(this.getDoorDirection() ==  DoorDirection.RIGHT)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
			
		}
		if(this.getDoorDirection() == DoorDirection.LEFT)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
		}
		if(this.getDoorDirection() == DoorDirection.DOWN)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
		}
		if(this.getDoorDirection() == DoorDirection.UP)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
		}
		
	}
	public void drawRoomName(Graphics2D g)
	{
		if(showRoomName == true)
		{
			g.setColor(Color.ORANGE);
			g.drawString(Board.getInstance().getSpecificRoom(getInitial()).toUpperCase(), x , y);
		}
	}
	
}
