package orderCock_Server_DTO;

public class PopulList {
	private int number;
	private int newmenu;
	private int popul;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNewmenu() {
		return newmenu;
	}
	public void setNewmenu(int newmenu) {
		this.newmenu = newmenu;
	}
	public int getPopul() {
		return popul;
	}
	public void setPopul(int popul) {
		this.popul = popul;
	}
	@Override
	public String toString() {
		return "PopulList [number=" + number + ", newmenu=" + newmenu + ", popul=" + popul + "]";
	}
	
}
