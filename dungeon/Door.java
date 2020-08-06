package unsw.dungeon;

public class Door extends Entity{
	private int id;
	private String status;
	
	public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;
        this.status = "close";
    }
	
	public int getID() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
