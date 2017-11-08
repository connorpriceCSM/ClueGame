package clueGame;

public class Suggestion {

	public String room;
	public String weapon;
	public String person;

	public Suggestion( String room, String weapon, String person)
	{
		this.room = room;
		this.weapon = weapon;
		this.person = person;
	}


	public Suggestion()
	{}

	//getters and setters 
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}


}
