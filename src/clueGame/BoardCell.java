package clueGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int cellRow;
	private int cellColumn;
	private char cellInitial;
	private char doorChar;
	private DoorDirection direction;
	private int index;
	private int x;
	private int y;
	protected boolean highlight;
	public static int PIECE_SIZE = 40;
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
		x = (cellColumn * PIECE_SIZE);
		y = (cellRow * PIECE_SIZE);
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
	public void setHighlight(boolean status)
	{
		highlight = status;
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
		//highlight appropriate squares
		if(this.highlight == true)
		{
			g.setColor(Color.yellow);
		}
		// Brown tiles for pathways
		if(this.isPathway())
		{
			g.setColor(Color.white);
		}
		// solid gray tiles for the rooms!
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(x, y, PIECE_SIZE, PIECE_SIZE);

		// Draw black outline of the square
		if(this.isPathway() || highlight == true)
		{
			drawPathway(g);
		}
		// draw green doorway squares!!
		if(this.isDoorway())
		{
			drawDoorway(g);
		}
		// Make Magenta titled rooms cause I can!
		drawRoomTitles(g);
			
		
	}

	
	//Create grid squares to mmore easily identify spots
	public void drawPathway(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(x,y,PIECE_SIZE, PIECE_SIZE );
	}

	// set the doorways! The direction of the doorway is indicated by its Red initial.
	// Doorways are in Green
	// Proportions are set.
	public void drawDoorway(Graphics2D g)
	{
		g.setColor(Color.GREEN);

		if(this.getDoorDirection() ==  DoorDirection.RIGHT)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
			g.setColor(Color.RED);
			int size = PIECE_SIZE;
			g.setFont( new Font("TimesRoman", Font.PLAIN, (PIECE_SIZE / 2 )));
			g.drawString(String.valueOf('R'), x  + (float)(PIECE_SIZE / 2 ), y + (float)(PIECE_SIZE / 2));
			
			
		}
		if(this.getDoorDirection() == DoorDirection.LEFT)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
			
			g.setColor(Color.RED);
			g.drawString(String.valueOf('L'), x  +  (float)(PIECE_SIZE / 3 ), y + (float)(PIECE_SIZE / 2));
			
		}
		if(this.getDoorDirection() == DoorDirection.DOWN)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
			g.setColor(Color.RED);
			g.drawString(String.valueOf('D'), x  +   (float)(PIECE_SIZE / 3 ), y + (float)(PIECE_SIZE / 2));
			
		}
		if(this.getDoorDirection() == DoorDirection.UP)
		{
			g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
			drawPathway(g);
			g.setColor(Color.RED);
			g.drawString(String.valueOf('U'), x  +  (float)(PIECE_SIZE / 3 ), y + (float)(PIECE_SIZE / 2));
			
		}

	}
	// Room Names built on proportions according to the constant PIECE_SIZE
	public void drawRoomTitles(Graphics2D g)
	{
		g.setColor(Color.MAGENTA);
		
		g.drawString("OBSERVATORY",(float)(PIECE_SIZE * .33) , ((float)PIECE_SIZE * (float) 10.625));
		g.drawString("KITCHEN", (float)( PIECE_SIZE * 1.25) , (float)(PIECE_SIZE *3));
		g.drawString("LIBRARY", (float)(PIECE_SIZE * 8) , (float)(PIECE_SIZE * 3));
		g.drawString("BATHROOM", (float)(PIECE_SIZE *18),  (float) (PIECE_SIZE *3));
		g.drawString("SAUNA", (float) (PIECE_SIZE * 18), (float) (PIECE_SIZE *13 ));
		g.drawString("GARDEN", (float) (PIECE_SIZE * 18), (float) (PIECE_SIZE * 22 ));
		g.drawString("BEDROOM",(float) (PIECE_SIZE * 11.33), (float)(PIECE_SIZE *20.5));
		g.drawString("FOYER",(float) (PIECE_SIZE * 6.5), (float)(PIECE_SIZE *20.5));
		// Entertainment Room is paried due to the narrowness of the room.
		g.drawString("ENTERTAINMENT", (float) (PIECE_SIZE *.33), (float) (PIECE_SIZE *16.5));
		g.drawString("ROOM", (float) (PIECE_SIZE *.70), (float) (PIECE_SIZE *17.2));
		
		
	}
	
}
